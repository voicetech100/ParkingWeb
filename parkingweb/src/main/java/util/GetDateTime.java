package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetDateTime {

	public static void main(String[] args) {
		System.out.println(new GetDateTime().nowDataTime());
	}
	
	
	public String nowDataTime() {
		LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formatDT = now.format(formatter);
	    //System.out.println(formatDT);
	    return formatDT;
	}
	
}
