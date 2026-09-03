package controller;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import service.AccessService;
import service.impl.AccessServiceImpl;
import util.EncryptionUtil;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

// 💡 導入Azure A 視覺 1.0.0 正式版官方規格套件
import com.azure.ai.vision.imageanalysis.ImageAnalysisClient;
import com.azure.ai.vision.imageanalysis.ImageAnalysisClientBuilder;
import com.azure.ai.vision.imageanalysis.models.DetectedTextBlock;
import com.azure.ai.vision.imageanalysis.models.DetectedTextLine;
import com.azure.ai.vision.imageanalysis.models.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.models.VisualFeatures;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/entry")
public class EntryPhoto {
    // 儲存照片的本機目錄
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads";
    // 💡 Azure入口
    String AZURE_ENDPOINT = EncryptionUtil.endpoint;  //"https://<RESOURCE_NAME>://";
    String AZURE_KEY = EncryptionUtil.key1;  //"AZURE_VISION_KEY";
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPhoto(
    		@FormDataParam("photo") InputStream uploadedInputStream,
            @FormDataParam("photo") FormDataContentDisposition fileDetail,
            @Context UriInfo uriInfo) {
        Map<String, Object> responseMap = new HashMap<>();
        if (uploadedInputStream == null) {
            responseMap.put("error", "Invalid_Payload");
            responseMap.put("message", "Request must contain a valid file.");
            return Response.status(Response.Status.BAD_REQUEST).entity(responseMap).build();
        }
        
        try {
            // 1. 確保儲存目錄存在
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // 2. 產生識別碼 (ID) 與取得副檔名
            String photoId = UUID.randomUUID().toString();
            String originalFilename = fileDetail.getFileName();
            String extension = originalFilename != null && originalFilename.contains(".") 
                    ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";

            // 3. 前端的資料流複製儲存到硬碟
            java.nio.file.Path targetPath = java.nio.file.Paths.get(UPLOAD_DIR, photoId + extension);
            Files.copy(uploadedInputStream, targetPath);

            // ===Azure車牌辨識===
            String licensePlate = "未偵測到車牌";
        	String authStr=null;
        	//	return Response.ok(Map.of("id", photoId,"status",authStr,"license_plate", licensePlate)).build();
            try {
                byte[] imageBytes = Files.readAllBytes(targetPath);
                licensePlate = recognizeLicensePlateWithAzure(imageBytes);//Azure算出來車牌植入到licensePlate變數
                if (!licensePlate.equalsIgnoreCase("未偵測到車牌")){
                	AccessService acc= new AccessServiceImpl();
                	authStr=acc.AuthorizationEntry(licensePlate);
                }
            } catch (Exception ex) {
                System.err.println("Azure 雲端辨識錯誤: " + ex.getMessage());
                licensePlate = "Azure 服務連線失敗：" + ex.getMessage();
            }
            // ==============================================================================

            // 4. 動態產生照片REST資源存取網址
            URI location = uriInfo.getAbsolutePathBuilder().path(photoId).build();

            // 5. 回傳給前端JSON訊息
            responseMap.put("id", photoId);
            responseMap.put("status", "success");
            responseMap.put("license_plate", licensePlate); // 回傳精準辨識的車牌給網頁  //// 💡 就在這裡！把剛剛算好的車牌，以 "license_plate" 為標籤欄位名稱，植入回傳物件中
            responseMap.put("resource_url", location.toString());
            responseMap.put("EntryMsg", authStr);
            return Response.created(location).entity(responseMap).build();

        } catch (IOException e) {
            responseMap.put("error", "Internal_Server_Error");
            responseMap.put("message", "儲存照片失敗：" + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseMap).build();
        }
    }

    //照片二進位資料傳送至 Azure AI進行文字辨識 (OCR)
    
    private String recognizeLicensePlateWithAzure(byte[] imageBytes) throws Exception {
        // 1. 初始化 Azure 影像分析客戶端
        ImageAnalysisClient client = new ImageAnalysisClientBuilder()
                .endpoint(AZURE_ENDPOINT)
                .credential(new AzureKeyCredential(AZURE_KEY))
                .buildClient();

        // 2. 封裝圖片數據並指定只擷取 READ (OCR 文字辨識) 功能
        BinaryData imageData = BinaryData.fromBytes(imageBytes);
        ImageAnalysisResult result = client.analyze(
                imageData, 
                Arrays.asList(VisualFeatures.READ), 
                null
        );

        // 3. 確保結構存在且防呆過濾
        if (result == null || result.getRead() == null || result.getRead().getBlocks() == null || result.getRead().getBlocks().isEmpty()) {
            return "無法讀取圖片中的文字";
        }

        StringBuilder allDetectedText = new StringBuilder();

        // 4. 透過正式版雙層迴圈走訪：先找區塊 (Blocks)，再讀文字行 (Lines)
        for (DetectedTextBlock block : result.getRead().getBlocks()) {
            if (block.getLines() == null) continue;
            for (DetectedTextLine line : block.getLines()) {
                String rawLine = line.getText();
                // 去除空格並轉大寫,台灣車牌不含小寫
                String cleanLine = rawLine != null ? rawLine.replaceAll("\\s+", "").toUpperCase() : "";
                allDetectedText.append(cleanLine).append(" ");
                // 一：嘗試直接對當前文字行進行高精準度比對
                String plateCheck = matchTaiwanPlate(cleanLine);
                if (!plateCheck.startsWith("辨識未完全") && !plateCheck.contains("未偵測")) {
                    return plateCheck; 
                }
            }
        }

        // 二：若單行過濾失敗，將整張圖的所有文字無縫串接，防範英數遭到切行分開
        return matchTaiwanPlate(allDetectedText.toString().replaceAll("\\s+", ""));
    }

    //輔助工具方法：精準篩選與拼湊台灣車牌格式 (沿用原邏輯)
    private String matchTaiwanPlate(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "未偵測到有效文字";
        }

        // 1. 精準比對帶有橫槓的標準格式 (例如 ABC-5678, 9276-MG)
        Pattern standardPattern = Pattern.compile("([A-Z0-9]{2,4}-[A-Z0-9]{2,4})");
        Matcher standardMatcher = standardPattern.matcher(text);
        if (standardMatcher.find()) {
            return standardMatcher.group(1);
        }

        // 2. 防呆機制：分開抓 3-4 碼數字與 2-3 碼英文並自動拼湊
        Pattern numPattern = Pattern.compile("\\d{3,4}");
        Pattern letterPattern = Pattern.compile("[A-Z]{2,3}");
        Matcher numMatcher = numPattern.matcher(text);
        Matcher letterMatcher = letterPattern.matcher(text);

        if (numMatcher.find() && letterMatcher.find()) {
            if (text.indexOf(letterMatcher.group()) < text.indexOf(numMatcher.group())) {
                return letterMatcher.group() + "-" + numMatcher.group();
            } else {
                return numMatcher.group() + "-" + letterMatcher.group();
            }
        }

        if (text.length() < 3) {
            return "未偵測到有效車牌 (字體結構殘缺)";
        }

        return "辨識未完全精準，原始字串: " + text;
    }


}

