package id.mbingweb.jposiso8583;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.filter.DelayFilter;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.ISO93APackager;

/**
 *
 * @author fqodry
 */
public class JPosClient {
    
    public static void run(String hostname, int portNumber) throws ISOException, IOException {
        
        // membuat sebuah packager
        //ISOPackager packager = new GenericPackager("packager/iso93ascii.xml");
        // membuat channel
        BaseChannel channel = new ASCIIChannel(hostname, portNumber, new ISO93APackager());
        // initialize DelayFilter to delay message in 5 second
        DelayFilter delayFilter = new DelayFilter(5000);
        // assign the filter
        channel.addFilter(delayFilter);
        
        try {
            channel.connect();
            channel.setTimeout(30000);
            ISOMsg msg = JPosClient.createMsg();
            channel.send(msg);
            
            // get incoming message
            ISOMsg resp = channel.receive();
            
            // output ISO 8583 message String
            byte[] bIsoMsg = resp.pack();
            String respStr = "";
            for (int i = 0; i < bIsoMsg.length; i++) {
                respStr += (char) bIsoMsg[i];
            }
            System.out.println("Resp. Packed ISO8583 Message = '" + respStr + "'");
            
            // describe each bit, start from MTI
            System.out.println("MTI='"+ resp.getMTI() +"'");
            for(int i = 1; i <= resp.getMaxField(); i++) {
                if(resp.hasField(i)) {
                    System.out.println(i + " = '" + resp.getString(i) + "'");
                }
            }
        } catch(IOException | ISOException ex) {
            Logger.getLogger(JPosServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            channel.disconnect();
        }
    }
    
    private static ISOMsg createMsg() throws ISOException {
        ISOMsg msg = new ISOMsg();
        msg.setMTI("1800");
        msg.set(3, "123456");
        msg.set(7, new SimpleDateFormat("yyyyMMdd").format(new Date()));
        msg.set(11, "000001");
        msg.set(12, new SimpleDateFormat("HHmmss").format(new Date()));
        msg.set(13, new SimpleDateFormat("MMdd").format(new Date()));
        msg.set(48, "ini pesan dari dummy client, terima kasih.");
        msg.set(70, "001");
        
        return msg;
    }
}
