package util;

import javax.crypto.Cipher;

import io.github.cdimascio.dotenv.Dotenv;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionUtil {
	private static final String ALGORITHM = "AES";
    public static String SECRET_KEY; //ForAzure
    public static final String sship="192.168.137.142"; 
    public static String endpoint;
    public static String key1;
    public static String ssha;
    public static String sshpassworda;
    public static String sshu;
    public static String sshpasswordu;
    
    public static String myTokenSecurtKey = "TempPassword"; //ForToken
        
    static  {
    	try {
    		Dotenv dotenv = Dotenv.configure()
			                    .directory("F:\\Public\\TerenceData\\security\\") 
			                    .filename(".env")  //根目錄.env
			                    .load();
    					SECRET_KEY=dotenv.get("SECRET_KEY");  //不能改加密做的配對key,若改需重新加密
						endpoint= decrypt(dotenv.get("endpoint"));
						key1= decrypt(dotenv.get("key1"));
						ssha= decrypt(dotenv.get("ssha"));
					    sshpassworda= decrypt(dotenv.get("sshpassworda"));
					    sshu= decrypt(dotenv.get("sshu"));
					    sshpasswordu= decrypt(dotenv.get("sshpasswordu"));
					    myTokenSecurtKey=dotenv.get("myTokenSecurtKey");  //可改,token加密解密相同即可
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // 加密
    public static String encrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 解密
    public static String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = Base64.getDecoder().decode(encryptedValue);
        return new String(cipher.doFinal(decryptedBytes));
    }


    public static void main(String[] args) {
    /*  ForTestOnly
    	EncryptionUtil util = new EncryptionUtil(); 
        System.out.println(SECRET_KEY);
        System.out.println(EncryptionUtil.endpoint);
        System.out.println(EncryptionUtil.key1);
    	System.out.println(EncryptionUtil.ssha);
    	System.out.println(EncryptionUtil.sshpassworda);
    	System.out.println(EncryptionUtil.sshu);
    	System.out.println(EncryptionUtil.sshpasswordu);
    	System.out.println(myTokenSecurtKey);
    */
    	
    	String password=hashPassword("123456");
    	System.out.println(password);
    	System.out.println(checkPassword("123456",password));
    
    }
  
   
    
    //====ForAccountPasswordWncryption================================
        
    public static String hashPassword(String rawPassword) { //rawPassword送進加密明碼（如 "123456"）
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt()); //自動生成隨機鹽值，強度預設為10, 返回加密的碼
    }

    
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        try {
            // 用內建的checkpw提取密文內的鹽值進行比對
            return BCrypt.checkpw(rawPassword, hashedPassword); //送進的明碼vs加密後的碼,返還值為布林
            } catch (Exception e) {
                return false;
            }
        }
    
    
}



