

import java.io.IOException;
import java.net.*;

public class UdpClient {

    public static void main(String[] args) throws IOException {

        InetAddress Ipserver = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);
        byte[] msg = args[2].getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(msg,msg.length,Ipserver,port);
        socket.send(packet);
        byte[] msgRecu = new byte[120];
        DatagramPacket reception = new DatagramPacket(msgRecu,msgRecu.length);
        socket.receive(reception);
        String message = new String(reception.getData());
        System.out.println(message);

    }

}
