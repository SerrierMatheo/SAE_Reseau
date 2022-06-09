import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class TcpClient {

    public static void main(String[] args) throws IOException {
        /**
         * Etape 1 : lire la ligne de commande ( IP, numPort)
         * Etape 2 : créer un objet Socket [Socket] sur l'ip, numPort
         * Etape 3 : créer les flux
         *             - getInputStream --> InputStreamReader(inputStream) -->BufferedReader(inputStreamReader)
         *             - getOutputStream --> PrintWriter (constructeur avec outputstream)
         *             - Scanner
         * Etape 4 : lire le clavier
         * Etape 5 : écrire dans la socket
         * Etape 5.5 : if "stop" --> break
         * Etape 6 : lire depuis la socket
         * Etape 7 : afficher à l'écran
         * Etape 7.5 : if "stop" --> break
         * retour à l'étape 4
         */


        InetAddress ip = null;
        try{
            ip = InetAddress.getByName(args[0]);
        }catch (UnknownHostException uhe){
            System.out.println("adresse inconnue");
            System.exit(1);
        }

        int numPort = 0;
        try{
            numPort = Integer.parseInt(args[1]);
        }catch (NumberFormatException nfe){
            System.out.println("numéro de port incorrect");
            System.exit(1);
        }

        Socket socket = null;
        try{
            socket = new Socket(ip,numPort);
        }catch (IOException ioe){
            System.out.println("création de socket impossible, argument incorrect");
            System.exit(1);
        }

        InputStream inputStream =null;
        try {
            inputStream = socket.getInputStream();
        }catch (IOException ioe){
            System.out.println("aucun flux entrant pour cette socket");
            System.exit(1);
        }

        InputStreamReader inputStreamReader = null;
        inputStreamReader = new InputStreamReader(inputStream);

        BufferedReader bf = null;
        bf = new BufferedReader(inputStreamReader);

        OutputStream outputStream = null;
        try{
            outputStream = socket.getOutputStream();
        }catch (IOException ioe){
            System.out.println("erreur outputStream");
            System.exit(1);
        }

        PrintWriter pw = new PrintWriter(outputStream, true);

        Scanner sc = new Scanner(System.in);

        while(true){
            String msg = sc.nextLine();
            pw.println(msg);
            if(Objects.equals(msg, "stop")){
                break;
            }
            String recep = "";
            try {
                recep = bf.readLine();
            }catch (IOException ioe){
                System.out.println("Erreur InOut");
                System.exit(1);
            }
            System.out.println(recep);
            if(Objects.equals(recep, "stop")){
                break;
            }
        }
        bf.close();
        pw.close();
        sc.close();

//-----------------------------------Code-sans-exceptions-----------------------------------------
        /**InetAddress ip = InetAddress.getByName(args[0]);
        int numPort = Integer.parseInt(args[1]);
        Socket socket = new Socket(ip,numPort);
        BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);
        while(true){
            String msg = sc.nextLine();
            pw.println(msg);
            if(Objects.equals(msg, "stop")){
                break;
            }
            String recep = bf.readLine();
            System.out.println(recep);
            if(Objects.equals(recep, "stop")){
                break;
            }
        }
        bf.close();
        pw.close();
        sc.close();*/
    }
}
