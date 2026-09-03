package util;

import com.jcraft.jsch.*;
import static util.EncryptionUtil.*;

public class ConnSsh {
    public static void main(String[] args) {
    	ConnSsh cn= new ConnSsh();    
    	cn.openParkingGate();
    }
    
    public void openParkingGate() {
        
    	String host = sship;
    	String user = ssha;
        String password = sshpassworda;
        int port = 22;

            try {
                JSch jsch = new JSch();
                Session session = jsch.getSession(user, host, port);
                session.setPassword(password);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
                ChannelExec channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand("/share/Public/jdk-11.0.30/bin/java -cp /share/Public/ocp/CallBeep CallBeepX 1"); //設定執行指令,使用絕對路徑
                //hal_app --se_sys_set_buzzer mode=1
                channel.setInputStream(null);
                channel.setErrStream(System.err);
                java.io.InputStream in = channel.getInputStream();
                channel.connect();

                // 讀取回傳結果
                byte[] tmp = new byte[1024];
                while (true) {
                    while (in.available() > 0) {
                        int i = in.read(tmp, 0, 1024);
                        if (i < 0) break;
                        System.out.print(new String(tmp, 0, i));
                    }
                    if (channel.isClosed()) {
                        if (in.available() > 0) continue;
                        System.out.println("結束代碼: " + channel.getExitStatus());
                        break;
                    }
                }
                channel.disconnect();
                session.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
}
