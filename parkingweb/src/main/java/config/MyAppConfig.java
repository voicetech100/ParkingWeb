package config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class MyAppConfig extends Application {  //名字無所謂,主要需extends Application
}
