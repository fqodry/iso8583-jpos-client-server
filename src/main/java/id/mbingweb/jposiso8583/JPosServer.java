package id.mbingweb.jposiso8583;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ServerChannel;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.iso.packager.ISO93APackager;

/**
 *
 * @author fqodry
 */
public class JPosServer implements ISORequestListener {
    
    public static void run() throws ISOException {
        String hostname = "localhost";
        int portNumber = 12345;
        
        // membuat sebuah packager
        //ISOPackager packager = new GenericPackager("packager/iso93ascii.xml");
        // membuat channel
        ServerChannel channel = new ASCIIChannel(hostname, portNumber, new ISO93APackager());
        // membuat server
        ISOServer server = new ISOServer(portNumber, channel, null);
        server.addISORequestListener(new JPosServer());
        
        new Thread(server).start();
        
        System.out.println("Server siap menerima koneksi pada port ["+ portNumber +"]");
    }

    @Override
    public boolean process(ISOSource isos, ISOMsg isomsg) {
        try {
            System.out.println("Server menerima koneksi dari ["+ ((BaseChannel)isos).getSocket().getInetAddress().getHostAddress() +"]");
            if (isomsg.getMTI().equalsIgnoreCase("1800")) {
                acceptNetworkMsg(isos, isomsg);
            }
        } catch (IOException | ISOException ex) {
            Logger.getLogger(JPosServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    private void acceptNetworkMsg(ISOSource isos, ISOMsg isomsg) throws ISOException, IOException {
        System.out.println("Accepting Network Management Request");
        
        // print each request bit, start from MTI
        System.out.println("MTI='"+ isomsg.getMTI() +"'");
        for(int i = 1; i <= isomsg.getMaxField(); i++) {
            if(isomsg.hasField(i)) {
                System.out.println(i + " = '" + isomsg.getString(i) + "'");
            }
        }
        
        // clone and set reply message
        ISOMsg reply = (ISOMsg) isomsg.clone();
        reply.setMTI("1810");
        reply.set(39, "00");
        reply.set(48, "Hello World! Ini response dari server jPos ya!");
        
        isos.send(reply);
    }
    
}
