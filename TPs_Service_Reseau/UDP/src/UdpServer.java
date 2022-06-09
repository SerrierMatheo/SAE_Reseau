

import java.io.IOException;
import java.io.IOException;
import java.net.*;

public class UdpServer {

    public static void main(String[] args) throws IOException {

        int numPort = 0;
        try{
            numPort = Integer.parseInt(args[0]);
        }catch (NumberFormatException nfe){
            System.out.println("numéro de port incorrect");
            System.exit(1);
        }

        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(numPort);
        } catch (SocketException se){
            System.out.println("impossible d'ouvrir le socket, le socket ne peut lier le numéro de port ");
            System.exit(1);
        }

        byte[] tab = new byte[120];
        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(tab, tab.length);
        }catch ( IllegalArgumentException iae){
            System.out.println("argument incorrect");
            System.exit(1);
        }

        try {
            socket.receive(packet);
        }catch (IOException ioe){
            System.out.println("In/Out error");
            System.exit(1);
        }

        String msg = new String(packet.getData());

        int port = 0;
        try {
            port = packet.getPort();
        }catch (NumberFormatException nfe){
            System.out.println("erreur numero de port");
            System.exit(1);
        }

        InetAddress adresse = packet.getAddress();

        byte[] tab1 = ("recu "+msg).getBytes();

        DatagramPacket packetRetour = null;
        try {
            packetRetour = new DatagramPacket(tab1, tab1.length,adresse,port);
        }catch (IllegalArgumentException iae){
            System.out.println("erreur argument");
            System.exit(1);
        }

        try {
            socket.send(packetRetour);
        }catch (IOException ioe){
            System.out.println("erreur In/Out");
            System.exit(1);
        }


    }
}
