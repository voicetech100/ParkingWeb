
package util;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthenticationFilter implements ContainerRequestFilter {
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	//包含大量「前端送過來的資料」，同時也包含「後端伺服器自己產生的環境資料」。
    	//大推車裡面裝載的具體資料可以分為三大類：
    	//1. 前端動送過來的資料：Headers / Authorization（帶有 JWT Token）/ Content-Type（告知後端這是 JSON 格式）/ Request Body（請求內文）：例如前端登入表單送過來的帳號、密碼JSON字串。URL網址與參數：前端想請求的後端網址（如 /api/products），以及網址後面的參數（如 ?page=1&limit=10）。HTTP方法：前端是用GET（拿資料）、POST（送資料）、PUT（修改）還是DELETE（刪除）。
    	//2. 前端瀏覽器自動附加的隱性資料,瀏覽器發出請求時，會自動塞入一些底層資訊：瀏覽器快取中儲存的該網域 Cookie。User-Agent：前端使用者的瀏覽器版本（如 Chrome/Safari）與作業系統。IP 地址：發送請求的用戶端 IP 地址。
    	//3. 後端伺服器為這趟請求準備的工具除了接收前端的資料，還裝了後端準備控制這趟請求的「功能按鈕」：SecurityContext：也就是「安全識別證欄位」，讓在驗證 Token 成功後把使用者名稱塞進去。Properties 暫存區：讓在 Filter 裡解析出資料後，可以用 .setProperty("key", value) 存起來，帶給後面的 API 資源使用。
        String path = requestContext.getUriInfo().getPath();
       
        //排除不進行攔截的路徑 //if (path.equals("hello")) {  //要拿掉"api/"，因為JAX-RS拿到的path是從api/後開始算
        if (path.equals("hello/login")||path.equals("hello/getresidentforcombobox")||path.equals("hello/getparkingnumberforcombobox") || path.equals("entry")  || path.equals("exit")){
            return;
        }
        //http://localhost:8080/parkingweb/api/hello/exit'
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION); // 從Header取得Authorization
        String token = authHeader.substring(7).trim();//取得Token字串,從索引值7（是第8個字元）開始切字串
        System.out.println("前端送過來驗證的Token: \t"+token);
        String tokenUserName=util.jwtUtility.extractUsername(token);
        System.out.println("前端Token送來驗證分析出的名字: \t"+tokenUserName);
        Boolean tokenBoolean=util.jwtUtility.validateToken(token); //驗證模擬的 Token
        System.out.println("前端Token送來驗證分析出的布林: \t"+tokenBoolean);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abortWithUnauthorized(requestContext);
            return;
        }

        if (!tokenBoolean) {
            abortWithUnauthorized(requestContext);
            return;
        }
    
    }
    
    private void abortWithUnauthorized(ContainerRequestContext context) {
        Response response = Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"憑證無效或已過期，請重新登入。\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
        context.abortWith(response);
    }
    
    
    
}






      
        /*
        // 驗證成功，設定 SecurityContext
        requestContext.setSecurityContext(new SecurityContext() {  //requestContext是JAX-RS系統（例如 Jersey、RESTEasy、Cxf 等後端框架）內建提供的。
            @Override
            public Principal getUserPrincipal() {
                return () -> tokenUserName;  // 用戶名
            }
            @Override
            public boolean isUserInRole(String role) {
                return "ADMIN".equals(role); // 模擬擁有 ADMIN 角色
            }
            @Override
            public boolean isSecure() { return false; }
            @Override
            public String getAuthenticationScheme() { return "Bearer"; }
        });
    	*/
  