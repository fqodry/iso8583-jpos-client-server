package id.mbingweb.jposiso8583;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JposIso8583ClientServerApplication extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JposIso8583ClientServerApplication.class);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(JposIso8583ClientServerApplication.class, args);
    }

}
