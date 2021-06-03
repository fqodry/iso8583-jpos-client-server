package id.mbingweb.jposiso8583.controller;

import id.mbingweb.jposiso8583.JPosClient;
import id.mbingweb.jposiso8583.JPosServer;
import java.io.IOException;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author fqodry
 */
@Controller
@RequestMapping("iso8583")
public class MainController {
    
    @Value("${jpos.server.host}") private String host;
    @Value("${jpos.server.port}") private String port;
    
    @GetMapping("/")
    public String welcome() {
        return "index";
    }
    
    @GetMapping("/server-start")
    public String serverStart() throws ISOException {
        JPosServer.run(host, Integer.valueOf(port));
        
        return "server";
    }
    
    @GetMapping("/client-start")
    public String clientStart() throws ISOException, IOException {
        JPosClient.run(host, Integer.valueOf(port));
        
        return "client";
    }
}
