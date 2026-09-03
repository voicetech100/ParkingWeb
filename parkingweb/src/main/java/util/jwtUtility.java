package util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class jwtUtility {

	
	public static void main(String[] args) {
		String token=generateToken("Roy"); //Roy just a test name ForTestOnly...
		System.out.println(token);
		System.out.println(extractUsername(token));
	}

	
	
	
	// 生成 Token
    public static String generateToken(String username) {  // 傳進來的參數 username
        // 💡 密鑰字串必須大於等於 32 個字元，否則執行時會報錯安全強度不夠
        String SECRET_STRING = util.EncryptionUtil.myTokenSecurtKey;
        // 💡 新版規定：必須將字串轉為 SecretKey 物件
        SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)           
                .issuedAt(new Date())       
                .expiration(new Date(System.currentTimeMillis() + 600000)) //當前時間 + 10分鐘
                .signWith(key)              
                .compact();                 
    }
    
    
    

    // 驗證 Token
    public static boolean validateToken(String token) {
        try {
            String SECRET_STRING = util.EncryptionUtil.myTokenSecurtKey;
            SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

            String name = Jwts.parser()
                    .verifyWith(key)             
                    .build()                     
                    .parseSignedClaims(token)    
                    .getPayload()                
                    .getSubject();               
            return name != null;                 // 有使用者名稱表示有效
        
        } catch (Exception e) {
            System.out.println("validateToken error " + e.getMessage());
            return false;                        
        }
    }
    
    
    
    
 
    // 從 Token 提取使用者名稱
    public static String extractUsername(String token) {
        String SECRET_STRING = util.EncryptionUtil.myTokenSecurtKey;
        SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)             
                .build()                     
                .parseSignedClaims(token)    
                .getPayload()                
                .getSubject();               
    }
    



}
