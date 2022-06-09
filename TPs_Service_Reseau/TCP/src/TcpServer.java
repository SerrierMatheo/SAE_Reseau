import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class TcpServer {

    public static void main(String[] args) throws IOException {
        /**
         * Etape 1 : lire la ligne de commande(num port)
         * Etape 2 : créer une socket d'écoute [ServerSocket] sur le port)
         * Etape 3 : Attendre une demande de connexion (accept(): Socket)
         * Etape 4 : Créer les flux puis :
         *           - getInputStream --> InputStreamReader(inputStream) -->BufferedReader(inputStreamReader)
         *           - getOutputStream --> PrintWriter (constructeur avec outputstream)
         *           - Scanner
         * Etape 5 : lire depuis la socket
         *             -methode ReadLine() de BufferedReader
         * Etape 6 : Affiche à l'écran
         * Etape 6.5 : if "stop" --> break
         * Etape 7 : lire le clavier
         * Etape 8 : ecrit dans la socket
         * Etape 8.5 : if "stop" -->break
         * retour à l'étape 5 : while (true)
         * Etape 9 : fermer les flux --> .close()
         */

        int numPort = 0;
        try{
            numPort = Integer.parseInt(args[0]);
        }catch (NumberFormatException nfe){
            System.out.println("numéro de port incorrect");
            System.exit(1);
        }

        ServerSocket sSocket = null;
        try{
            sSocket = new ServerSocket(numPort);
        }catch (IOException ioe){
            System.out.println("création de socket impossible, argument incorrect");
            System.exit(1);
        }

        System.out.println("Attente de connexion");

        Socket s = null;
        try {
            s = sSocket.accept();
        }catch (IOException ioe){
            System.out.println("erreur, demande de connexion");
            System.exit(1);
        }

        InputStream inputStream =null;
        try {
            inputStream = s.getInputStream();
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
            outputStream = s.getOutputStream();
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

        //-----------------------------------Code-sans-exceptions---------------------------------
        /**int numPort = Integer.parseInt(args[0]);
        ServerSocket sSocket = new ServerSocket(numPort);
        System.out.println("Attente de connexion");
        Socket s = sSocket.accept();
        BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);
        while (true){
            String recep = bf.readLine();
            System.out.println(recep);
            if(Objects.equals(recep, "stop")){
                break;
            }
            String msg = sc.nextLine();
            pw.println(msg);
            if(Objects.equals(msg, "stop")){
                break;
            }
        }
        bf.close();
        pw.close();
        sc.close();*/
    }

}
