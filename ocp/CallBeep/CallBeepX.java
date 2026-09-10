import java.io.*;

public class CallBeepX {
    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.out.println("Please Input Args！");
            return;
        }

        // 變數名稱modeParam避免與main的args衝突
        String modeParam = "mode="+args[0];
        
        // hal_app --se_sys_set_buzzer mode=1 呼叫指令範例
        // 檢查條件
        if (args.length == 1) {
            try {
                // 將變數傳入 ProcessBuilder
                ProcessBuilder pb = new ProcessBuilder("hal_app", "--se_sys_set_buzzer", modeParam);
                // 合併錯誤輸出
                pb.redirectErrorStream(true); 
                // 執行指令
                Process process = pb.start(); 
                // 讀取外部指令的輸出內容
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "MS950")
                );
                                
                String line;
                
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                
                // 等待指令執行完畢
                int exitCode = process.waitFor();
                System.out.println("Command finished with exit code: " + exitCode);
                
            } catch (IOException e) {
                System.out.println("Failed to start command: " + e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("Process Termination: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
