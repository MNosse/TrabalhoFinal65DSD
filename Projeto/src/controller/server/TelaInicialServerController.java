package controller.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TelaInicialServerController {
    
    public String getHostAdress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch(UnknownHostException e) {
            return "";
        }
    }
}
