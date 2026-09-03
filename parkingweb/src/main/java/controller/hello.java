package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.azure.ai.vision.imageanalysis.ImageAnalysisClient;
import com.azure.ai.vision.imageanalysis.ImageAnalysisClientBuilder;
import com.azure.ai.vision.imageanalysis.models.DetectedTextBlock;
import com.azure.ai.vision.imageanalysis.models.DetectedTextLine;
import com.azure.ai.vision.imageanalysis.models.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.models.VisualFeatures;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;

import jakarta.servlet.http.HttpServletRequest; // 🌟 變更為 jakarta 20260813 add
import jakarta.servlet.http.HttpSession;        // 🌟 變更為 jakarta 20260813 add
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.Status;
import model.AccessTable;
import model.AdminRegistrationTable;
import model.CarRegistrationTable;
import model.ResidentTable;
import service.AccessService;
import service.AdminRegistrationService;
import service.CarRegistrationService;
import service.ResidentService;
import service.impl.AccessServiceImpl;
import service.impl.AdminRegistrationServiceImpl;
import service.impl.CarRegistrationServiceImpl;
import service.impl.ResidentServiceImpl;
import util.EncryptionUtil;
import view.ComboResidentParking;
import view.ComboResidentParkingDao;
import view.ComboResidentParkingDaoImpl;


@Path("/hello")   //    http://localhost:8080/parkingweb/api/hello
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class hello {
	@GET
	public String SayHello() {
		return "Hello World!!!";
	}

	
	
	
	@GET
	@Path("/validation")
	@Produces("application/json;charset=UTF-8")
	// 💡 注意：GET 請求通常不用加 @Consumes("application/json")，因為 GET 沒有 Body
	public Response validation() {
	    // (這裡通常會寫驗證 Token 是否為最高管理員的邏輯)
	    // 驗證成功，回傳 JSON 告知前端可以通行
	    Map<String, String> resultMap = new HashMap<>();
	    resultMap.put("status", "success");
	    resultMap.put("message", "驗證成功，允許進入管理後台");
	    return Response.ok(resultMap).build();
	}
	
	
	
	
	
	
	
	
	

	

	//刪除manager
    /*{
        "id":
        "account": "jennie",
        "password": "jennie123",
        "name": "Jennie",
        "phone": "0988168168",
        "date": "",
        "class1": "2"
    }*/
	@DELETE //postman => json =>	http://localhost:8080/parkingweb/api/hello/admcrud/id
	@Path("/admcrud/{id}")   
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response DeleteManager(@PathParam("id")int id,Map<String, String> payload){
		System.out.println(id);	
		System.out.println(payload.get("account"));	
		System.out.println(payload.get("name"));
		AdminRegistrationService admRegService = new AdminRegistrationServiceImpl();
			AdminRegistrationTable admRegTable = new AdminRegistrationTable(); 
			admRegTable.setId(id);
			admRegTable.setAccount(payload.get("account"));
			admRegTable.setPassword(payload.get("password"));
			admRegTable.setName(payload.get("name"));
			admRegTable.setPhone(payload.get("phone"));
			admRegTable.setClass1(payload.get("class1"));
		    admRegTable.setDate(payload.get("date"));
		    int i = admRegService.DeleteAccount(admRegTable);
			if (i == 1){ 
				return Response.ok().entity(Map.of("success", true , "data" , payload , "message" , "刪除成功" )).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("success",false,"data",payload, "message","刪除失敗")).build();
			}
		}
	
	

	
	
	
	
	
	
	
	
	
	

	

	//更新manager
    /*{
        "id":
        "account": "jennie",
        "password": "jennie123",
        "name": "Jennie",
        "phone": "0988168168",
        "date": "",
        "class1": "2"
    }*/
	@PUT //postman => json =>	http://localhost:8080/parkingweb/api/hello/admcrud/id
	@Path("/admcrud/{id}")   
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response UpdateManager(@PathParam("id")int id,Map<String, String> payload){
		//System.out.println(id);	
		//System.out.println(payload.get("account"));	
		//System.out.println(payload.get("name"));
		AdminRegistrationService admRegService = new AdminRegistrationServiceImpl();
			AdminRegistrationTable admRegTable = new AdminRegistrationTable(); 
			admRegTable.setId(id);
			admRegTable.setAccount(payload.get("account"));
			admRegTable.setPassword(payload.get("password"));
			admRegTable.setName(payload.get("name"));
			admRegTable.setPhone(payload.get("phone"));
			admRegTable.setClass1(payload.get("class1"));
		    admRegTable.setDate(payload.get("date"));
		    int i = admRegService.UpdateAccount(admRegTable);
			if (i == 1){ 
				return Response.ok().entity(Map.of("success", true , "data" , payload , "message" , "更新成功" )).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("success",false,"data",payload, "message","更新失敗")).build();
			}
		}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//新增manager
    /*{
        "account": "jennie",
        "password": "jennie123",
        "name": "Jennie",
        "phone": "0988168168",
        "date": "",
        "class1": "2"
    }*/
	@POST //postman => json =>	http://localhost:8080/parkingweb/api/hello/admcrud
	@Path("/admcrud")   
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response addManager(Map<String, String> payload){
			AdminRegistrationService admRegService = new AdminRegistrationServiceImpl();
			AdminRegistrationTable admRegTable = new AdminRegistrationTable(); 
			admRegTable.setAccount(payload.get("account"));
			admRegTable.setPassword(payload.get("password"));
			admRegTable.setName(payload.get("name"));
			admRegTable.setPhone(payload.get("phone"));
			admRegTable.setClass1(payload.get("class1"));
		    admRegTable.setDate(payload.get("date"));
		    String i = admRegService.InsertAccount(admRegTable);
			if (i.equals("新增成功!")){ 
				return Response.ok().entity(Map.of("success", true , "data" , payload , "message" , i )).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("success",false,"data",payload, "message",i)).build();
			}
		}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//查詢所有管理員,會員等級1使用
	@POST //postman => json =>	http://localhost:8080/parkingweb/api/hello/getallmanager
	/*  {
            "id": 23,
            "account": "test111",
            "password": "1111",
            "name": "test111",
            "phone": "09881111",
            "date": "2026-07-03 08:55:36",
            "class1": "2"
        }
	 */@Path("/getallmanager")   
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response getAllManager(Map<String, String> payload){
		String account = payload.get("account");
	    String password = payload.get("password");
	    System.out.println(account);
	    AdminRegistrationService admRegService = new AdminRegistrationServiceImpl();  
		    AdminRegistrationTable admRegTable = new AdminRegistrationTable(); 
		    admRegTable.setAccount(account);
		    admRegTable.setPassword(password);
			List<AdminRegistrationTable> list = admRegService.FindAllManager(admRegTable);
			if (list != null || list.size()>0){ 
				return Response.ok().entity(Map.of("success", true , "data" , list , "message" , "查詢完成" )).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(Map.of("success",false,"data",list, "message","查無資料")).build();
			}	// 💡 密碼錯誤不回傳token欄位，避免null造成Map.of報錯
		}
	
	
	
	
	
	
	//查詢單一管理員,會員等級2使用
	@POST //postman => {"account":"admin","password":"admin123"} =>	http://localhost:8080/parkingweb/api/hello/getmanager
	@Path("/getmanager")   
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response getManager(Map<String, String> payload){
		String account = payload.get("account");
	    String password = payload.get("password");
			AdminRegistrationService admRegService = new AdminRegistrationServiceImpl();  
		    AdminRegistrationTable admRegTable = new AdminRegistrationTable(); 
		    admRegTable.setAccount(account);
		    admRegTable.setPassword(password);
			List<AdminRegistrationTable> list = admRegService.FindAccountList(admRegTable);
			if (list != null || list.size()>0){ 
				return Response.ok().entity(Map.of("success", true , "data" , list , "message" , "查詢完成" )).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(Map.of("success",false,"data",list, "message","查無資料")).build();
			}	// 💡 密碼錯誤不回傳token欄位，避免null造成Map.of報錯
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	//首頁登入
	@POST //postman => {"account":"admin","password":"admin123"} =>	http://localhost:8080/parkingweb/api/hello/login
	@Path("/login")   
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response login(Map<String, String> payload){
		System.out.println("@後端Login=>進入");
		String account = payload.get("account");
	    String password = payload.get("password");
			AdminRegistrationService admRegService = new AdminRegistrationServiceImpl();  
		    AdminRegistrationTable admRegTable = new AdminRegistrationTable(); 
		    admRegTable.setAccount(account);
		    admRegTable.setPassword(password);
		    List<AdminRegistrationTable> admList = admRegService.CheckAccountPasswordWeb(admRegTable);
			// 🌟 定義登入成功要核發的 Token 字串
			//String token="admin-token-SECRET_123456";  //前端可以localStorage.setItem()
		    System.out.println("@後端Login=>返回");
			String token=util.jwtUtility.generateToken(account);
			
			System.out.println(admList);
			System.out.println(admList.size());
			
			if (admList!=null && admList.size()==1){ //後端回傳比對結果true會是1
				return Response.ok().entity(Map.of("success",true,"data",admList,"token",token)).build();
			} else {
				return Response.ok().entity(Map.of("success",false,"data","帳號或密碼錯誤")).build();
			}	// 💡 密碼錯誤不回傳token欄位，避免null造成Map.of報錯
		}
	
	
	
	
	
	
	
	
	

	
	//抓取所有住戶
	@GET  //	http://localhost:8080/parkingweb/api/hello/getallresidentbylot
	@Path("/getallresidentbylot")   
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response getAllResident(){ 
				ResidentService residentService = new ResidentServiceImpl();
				List<ResidentTable> list =  residentService.getAllResidentWeb();
				if (list!=null){
					return Response.ok().entity(Map.of("success",true,"data",list)).build();
				} else {
					return Response.status(Response.Status.NOT_FOUND).entity(Map.of("success",false,"data",null)).build();
				}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//抓取車輛登記
	@GET  //	http://localhost:8080/parkingweb/api/hello/getallcarregistration
	@Path("/getallcarregistration")   
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response getAllCarRegistration(){ 
				CarRegistrationService carRegistration = new CarRegistrationServiceImpl();
				List<CarRegistrationTable> list =  carRegistration.getAllCarRegistrationWeb();
				if (list!=null){
					return Response.ok().entity(Map.of("success",true,"data",list)).build();
				} else {
					return Response.status(Response.Status.NOT_FOUND).entity(Map.of("success",false,"data",null)).build();
				}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//抓取停車資訊
	@GET  //	http://localhost:8080/parkingweb/api/hello/getallresidentparking
	@Path("/getallresidentparking")   
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response getAllResidentParking(){ 
				AccessService acc= new AccessServiceImpl();
				List<ComboResidentParking> list =  acc.getAllComboResidentParkingWeb();
				if (list!=null){
					return Response.ok().entity(Map.of("success",true,"data",list)).build();
				} else {
					return Response.status(Response.Status.NOT_FOUND).entity(Map.of("success",false,"data",null)).build();
				}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	//抓取進出紀錄
	@GET  //	http://localhost:8080/parkingweb/api/hello/getEntryExit/2026-01-10/2026-10-10/one-0002
	@Path("/getEntryExit/{fromDate}/{endDate}/{licensePlateNumber}")    
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response getEntryExit(@PathParam("fromDate")String fromDate,
											 @PathParam("endDate")String endDate,
											 @PathParam("licensePlateNumber")String licensePlateNumber){ 
				AccessService acc= new AccessServiceImpl(); 
				List<AccessTable> list =  acc.getEntryExitWeb(fromDate,  endDate, licensePlateNumber);
				if (list!=null){
					return Response.ok().entity(list).build();
				} else {
					return Response.status(Response.Status.NOT_FOUND).entity(Map.of("success",false,"data",null)).build();
				}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 1. 寫入 Session API
	@POST
	@Path("/setsession")    
	@Consumes("application/json") 
	@Produces("application/json;charset=UTF-8")
	public Response setSessionData(
		Map<String, Object> payload, 
	        @Context jakarta.servlet.http.HttpServletRequest request) {
		System.out.println("【系統收到物件】住戶ID為: " + payload.get("residentId"));
	    
	    HttpSession session = request.getSession(true);
	    // ⭕ 修正：存入的是 Map 物件
	    session.setAttribute("REPORT_DATA", payload); 
	    
	    return Response.ok("{\"success\":true, \"message\":\"Session 資料已成功寫入！\"}").build();
	}

	
	
	
	// 2. 讀取 Session API
		@GET
		@Path("/getsession")
		@Produces("application/json;charset=UTF-8")
		public Response getSessionData(@Context jakarta.servlet.http.HttpServletRequest request) {
		    HttpSession session = request.getSession(false);
		    if (session != null && session.getAttribute("REPORT_DATA") != null) {
		        
		        // ⭕ 修正：這裡必須轉型回 Map<String, Object>，不能轉成 String！
		        Map<String, Object> myDataMap = (Map<String, Object>) session.getAttribute("REPORT_DATA");
		        System.out.println("【系統】成功撈到暫存資料，準備送往前端。");
		        
		        // 一次性銷毀機制（若有需要再打開）
		        // session.removeAttribute("REPORT_DATA"); 
		        
		        // ⭕ 修正：直接回傳 Map 物件，Jax-RS 會自動將其轉為標準 JSON 格式
		        return Response.ok(myDataMap).build();
		    }
		    // 💡 提示：如果找不到，回傳 401 或含有 success:false 的 JSON
		    return Response.ok("{\"success\":false, \"message\":\"無暫存資料或階段已過期\"}").build();
		}
	
		//預設30分鐘的計算方式是從使用者最後一次發送網頁請求
		//修改有效時間
		//傳統Java專案(修改web.xml)：
		// <session-config>
	    //   <session-timeout>15</session-timeout> <!-- 改成 15 分鐘 -->
		// </session-config>
		//如果是Spring Boot專案(修改application.properties)：propertiesserver.servlet.session.timeout=15m
		
		
	
	
	
	

	
	
	
	
		
		
		
	
	
	//=====常用方法===================================
	private Map<String, Object> ok(Object residentData ,Object carData)
	{ 
		//return Map.of("success", true, "resident", residentData , "carregistration", carData); 
	    // 使用 LinkedHashMap保留Put的先後順序
	    Map<String, Object> response = new LinkedHashMap<>();
	    response.put("success", true);
	    response.put("resident", residentData);
	    response.put("carregistration", carData);
	    return response;
	}
	
	private Map<String, Object> fail(String resmsg, String carmsg) 
	{ 
		//return Map.of("success", false, "resident", resmsg, "carregistration", carmsg);
		Map<String, Object> response = new LinkedHashMap<>();
	    response.put("success", false);
	    response.put("resident", resmsg);
	    response.put("carregistration", carmsg);
	    return response;
	}
	
	
	//Map.of()或預設的 HashMap是無序的，轉成 JSON 時欄位會隨機跳動。
	//LinkedHashMap 的作用：它內部使用鏈表（Linked List）記錄了插入的順序，主流的 JSON 序列化工具（如 Jackson、Gson）在轉換時，會依照您put的順序輸出成JSON字串。
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ========== 工具資料列 ==========
    // get resident & carregistration for Combobox
    @GET	//地址    http://localhost:8080/parkingweb/api/hello/getresidentforcombobox
    @Path("/getresidentforcombobox")
    @Produces(MediaType.TEXT_PLAIN + ";charset=UTF-8")  // ⬅️ 子方法覆蓋類別層級，回傳純文字
    public Response getresidentforCB() {
        ResidentService resService = new ResidentServiceImpl();
        String residentCB = resService.FindAddressSimpleForCombobox();
        
        // ✅ 加上 CORS 標頭，解決跨域問題
        return Response.ok()
            .entity(residentCB)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
            .build();
    }

		

	
	@GET   //車位   http://localhost:8080/parkingweb/api/hello/getparkingnumberforcombobox
	@Path("/getparkingnumberforcombobox")
	public Response getcarregistrationforCB() {
		ResidentService resService = new ResidentServiceImpl();
		String carregistrationCB = resService.FindParkingSpaceNumberForCombobox();
		return Response.ok().entity(carregistrationCB).build();
		}
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	//====更新修改住戶資料===============================================================================
//	@GET //先用GET比較好測試,後續再改POSE
//	@Path("/updateresident/{id}/{parkingowner}/{parkingownerphone}/{parkinguser}/{parkinguserphone}")
//	public Response updateResidentByResidentId(
//	    @PathParam("id") String residentid,
//	    @PathParam("parkingowner") String parkingowner,
//	    @PathParam("parkingownerphone") String parkingownerphone,
//	    @PathParam("parkinguser") String parkingspaceuser,
//	    @PathParam("parkinguserphone") String parkinguserphone
//	) { //   http://localhost:8080/parkingweb/api/hello/updateresident/A57H15F02/1111/2222/3333/4444
//		ResidentService resService = new ResidentServiceImpl();
//		resService.UpdateResidentByResidentId(residentid, parkingowner, parkingownerphone, parkingspaceuser, parkinguserphone);
//		return Response.ok(Map.of("success", true)).build();
//	}
//	
	//====更新修改住戶資料 (已改成 POST 接收 JSON) ===============================================================================
	
	@POST
	@Path("/updateresident") // URL 變得非常乾淨： http://localhost:8080/parkingweb/api/hello/updateresident
	@Consumes(MediaType.APPLICATION_JSON) // 聲明接收 JSON 格式
	@Produces(MediaType.APPLICATION_JSON) // 聲明回傳 JSON 格式
	public Response updateResidentByResidentId(Map<String, String> payload) {
		
		// 從前端傳來的 JSON 中取出對應的欄位值
		String residentid = payload.get("residentId");
		String parkingowner = payload.get("parkingSpaceOwner");
		String parkingownerphone = payload.get("parkingSpaceOwnerPhone");
		String parkingspaceuser = payload.get("parkingSpaceUser");
		String parkinguserphone = payload.get("parkingSpaceUserPhone");
		
		// 呼叫您原本的 Service
		ResidentService resService = new ResidentServiceImpl();
		resService.UpdateResidentByResidentId(residentid, parkingowner, parkingownerphone, parkingspaceuser, parkinguserphone);
		
		return Response.ok(Map.of("success", true)).build();
	}

	
	
	
	
	
	
	
	

	// ====新增車輛登記=====
	@POST
	@Path("/insertcarregistration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response insertCarRegistration(Map<String, String> payload) {
		// 💡 關鍵修正：從 request 中取得目前的 session 物件
//	    jakarta.servlet.http.HttpSession session = request.getSession();
		//Object myData = session.getAttribute("REPORT_DATA");
		String residentId = payload.get("residentId");
	    System.out.println(residentId);
	    String addressSimple      = payload.get("addressSimple"); //前端沒傳addressSimple的json
	    String carSerialNumber    = payload.get("carSerialNumber");
	    String licensePlateNumber = payload.get("licensePlateNumber"); // 👈 這裏會正確拿到前端填寫的車牌，不要用 isEmpty 蓋掉它了！
	    String occupiedAvailable  = payload.get("occupiedAvailable");
	    String carUser  = payload.get("parkingSpaceUser");      // 電話
	    System.out.println(carUser);
	    String carUserPhone = payload.get("parkingSpaceUserPhone"); // 名字
	    System.out.println(carUserPhone);
	    String carRegistrationDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
	    System.out.println(licensePlateNumber);	    // 2. 呼叫您原本的 Service
	    CarRegistrationService carService = new CarRegistrationServiceImpl();
	    
	    int i = carService.InsertCarRegistration(
	            residentId, 
	            addressSimple, 
	            carSerialNumber, 
	            licensePlateNumber, 
	            occupiedAvailable, 
	            carUser,        // 電話
	            carUserPhone,   // 名字
	            carRegistrationDate
	    );	
	    
	    // 3. 根據結果回傳對應的 JSON 回應
	    if (i > 0) {
	        return Response.ok(Map.of("success", true, "message", "車輛新增成功", "data", licensePlateNumber)).build();
	    }
	    // 如果因為空車牌或滿了被擋下，會回傳 400 錯誤
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(Map.of("success", false, "message", "新增失敗：車輛登記已滿或車號格式不符","data", licensePlateNumber))
				.build();
	}

	
	
	
	
	
	
	
	//====刪除車輛登記===============================================================================
		@GET //先用GET比較好測試,後續再改POSE
		@Path("/delete/{id}/{licenseplatenumber}")  // http://localhost:8080/parkingweb/api/hello/delete/A57H01F01/ONE-0002
		public Response DeleteCarRegistration(@PathParam("licenseplatenumber")String licenseplatenumber,@PathParam("id")String id) {
			CarRegistrationService carService =new CarRegistrationServiceImpl(); 
			int i=carService.DeleteCarRegistrationByLicensePlateNumberResidentId(licenseplatenumber,id);
			if (i>0) {
				return Response.ok().entity(Map.of("success", true,"result","車輛刪除完成","data",licenseplatenumber)).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity(Map.of("success",false,"result","查無車輛","data",licenseplatenumber)).build();
			}
		}
		
	
	
	
		
		
		
		
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//===查詢=================================================================================
	
		//查詢所有住戶
		@GET
		@Path("/getallresident")  //http://localhost:8080/parkingweb/api/hello/getallresident
		public Response getAllResidentById() {
			ResidentService resService = new ResidentServiceImpl();
			List<ResidentTable> list = resService.FindAllResident();
			if (list !=null && list.size()>0) {
				return Response.ok(list).build();
			}
			else {
				return Response.ok().entity("查無資料").build();
			}
		}
		
		
		
		
		
	
		
		
		
		//用地址找住戶
		@GET   //http://localhost:8080/parkingweb/api/hello/getresidentbyaddresssimple/57號11樓-1
		@Path("/getresidentbyaddresssimple/{addresssimple}")
		
		public Response getResidentByAddressSimple(@PathParam("addresssimple")String addresssimple) { //, @Context jakarta.servlet.http.HttpServletRequest request) {
			ResidentService resService = new ResidentServiceImpl();
			CarRegistrationService carService =new CarRegistrationServiceImpl();
			ResidentTable resident = resService.FindResidentByAddressSimple(addresssimple);
			if (resident !=null) {
				String resId = resident.getResidentId();
				List<CarRegistrationTable> carregistration = carService.GetCarRegistrationByResidentId(resId);
				//Map<String, Object> map = ok(resident,carregistration); //查詢結果包裝成JSON物件（或 Map）
				//HttpSession session = request.getSession(); //取得 Session 並把資料存進去
	            //session.setAttribute("REPORT_DATA", map); //使用 jakarta的session物件將資料存入工作階段
	            //return Response.seeOther(URI.create("/parkingweb/residentbyaddress.html")).build();
	            return Response.ok(ok(resident,carregistration)).build();
			}
			else {
				//return Response.seeOther(URI.create("/parkingweb/error.html")).build();
				return Response.ok().entity(fail("門牌查無資料","查無車輛")).build();
				//return Response.status(Response.Status.NOT_FOUND).entity("查無資料").build();
			}
		}
		
		
		
		
		
		
		
		
		
		//用車號找住戶
		@GET
		@Path("/getresidentbylicenseplatenumber/{number}")  //http://localhost:8080/parkingweb/api/hello/getresidentbylicenseplatenumber/ONE-0001
		public Response getresidentbylicenseplatenumber(@PathParam("number")String number) {
			ResidentService resService = new ResidentServiceImpl();
			CarRegistrationService carService =new CarRegistrationServiceImpl(); 
			ResidentTable resident = resService.FindResidentByLicensePlateNumber(number);
			if (resident !=null) {				
				String resId = resident.getResidentId();
				List<CarRegistrationTable> carregistration = carService.GetCarRegistrationByResidentId(resId);
				return Response.ok(ok(resident,carregistration)).build();
			}
			else {
				return Response.ok().entity(fail("車牌查無住戶","查無車輛")).build();
			}
		}
		
		
		
		
		
	
	
		//用ID找住戶
		@GET
		@Path("/getresidentbyid/{id}")  //http://localhost:8080/parkingweb/api/hello/getresidentbyid/A57H12F01
		public Response getResidentById(@PathParam("id")String id) {
			ResidentService resService = new ResidentServiceImpl();
			CarRegistrationService carService =new CarRegistrationServiceImpl();
			ResidentTable resident = resService.FindResidentByResidentId(id);
			if (resident !=null) {
				String resId = resident.getResidentId();
				List<CarRegistrationTable> carregistration = carService.GetCarRegistrationByResidentId(resId);
				return Response.ok(ok(resident,carregistration)).build();
			}
			else {
				return Response.ok().entity(fail("Id查無住戶","查無車輛")).build();
				//return Response.status(Response.Status.NOT_FOUND).entity("查無資料").build();
			}
		}
		
		

		
		
		//用車位找住戶
		@GET
		@Path("/getresidentbyparkingspacenumber/{number}")  //http://localhost:8080/parkingweb/api/hello/getresidentbyparkingspacenumber/c1502
		public Response getResidentByParkingSpace(@PathParam("number")String number) {
			ResidentService resService = new ResidentServiceImpl();
			CarRegistrationService carService =new CarRegistrationServiceImpl();
			ResidentTable resident = resService.FindResidentByParkingSpaceNumber(number);
			if (resident !=null) {
				String resId = resident.getResidentId();
				List<CarRegistrationTable> carregistration = carService.GetCarRegistrationByResidentId(resId);
				return Response.ok(ok(resident,carregistration)).build();
			}
			else {
				return Response.ok().entity(fail("車位查無住戶","查無車輛")).build();
				//return Response.status(Response.Status.NOT_FOUND).entity("查無資料").build();
			}
		}
		
		
	

		
		
		
	
}
