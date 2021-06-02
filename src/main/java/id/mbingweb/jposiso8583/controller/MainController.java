package id.mbingweb.jposiso8583.controller;

import id.mbingweb.jposiso8583.JPosClient;
import id.mbingweb.jposiso8583.JPosServer;
import java.io.IOException;
import org.jpos.iso.ISOException;
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
    
    @GetMapping("/")
    public String welcome() {
        return "index";
    }
    
    @GetMapping("/server-start")
    public void serverStart() throws ISOException {
        JPosServer.run();
    }
    
    @GetMapping("/client-start")
    public void clientStart() throws ISOException, IOException {
        JPosClient.run();
    }
}
