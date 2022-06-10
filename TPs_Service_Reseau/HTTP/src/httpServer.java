import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;

public class httpServer {

    static String CHEMIN;
    /**
     * Etape 1 : lire le numéro de port (args)
     * Etape 2 : créer un serverSocket
     *          while true jusqu'a la fin
     *
     * Etape 3 : attendre une demande de connexion methode accept -->retourne une socket
     * Etape 4 : péparer les flux
     *             créer un inputStreamReader + BufferedRedaer
     *             créer un DataOutputStream
     * Etape 5 : lire 1 ligne --> stocker (variable string) --> readLine()
     * Etape 6 : While true:
     *             lire une ligne puis arreter à la ligne vide -->readLine() et break
     * Etape 7 : récupérer le nom de la ressource dpuis la ligne stocker (étape 5) --> split
     * Etape 8 : Si le fichier existe(fichier docroot/le nom de ressource):
     *              lire le fichier             --->creer methode static bytes[] lireFichier(String cheminComplet)
     *                                                  -->ouvre le fichier
     *                                                      si le fichier existe
     *                                                          récupérer sa taille
     *                                                          créer un tableau de bytes[taille]
     *                                                          lire dans le tableau
     *                                                          retourner le tableau
     *                                                      sinon
     *                                                          retourne null
     *              ecrire dans la socket : "HTTP1.1 200 OK\r\n".getBytes() --> write() * 3
     *                                      "\r\n".getBytes()
     *                                      le contenue du fichier
     *           Sinon:
     *              ecrire : "HTTP1.1 404 NotFound\r\n".getBytes()  -->write()
     *                       "\r\n".getBytes()
     * Etape 9 : fermer la socket et les flux --> close()
     *
     */

    static byte[] lireFichier(String cheminComplet) throws IOException {
        File file = new File(cheminComplet);
        if(file.exists()){
            FileInputStream fileInputStream = new FileInputStream(file);
            int taille = fileInputStream.available();
            byte[] tab = new byte[(int) taille];
            int f = fileInputStream.read(tab);
            return tab;
        }else{
            return null;
        }
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        xmlReader config = new xmlReader();
        config = config.configurerReseau();
        CHEMIN = config.getRacine();


        int numPort = 0;
        try{
            numPort = Integer.parseInt(config.getPort());
        }catch (NumberFormatException nfe){
            System.out.println("numero de port incorrect");
            System.exit(1);
        }

        ServerSocket sSocket = null;
        try{
            sSocket = new ServerSocket(numPort);
            System.out.println("Attente de connexion");
        }catch (IOException ioe){
            System.out.println("création de socket impossible, argument incorrect");
            System.exit(1);
        }


        while (true){
            Socket s = sSocket.accept();

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

            DataOutputStream dataOutputStream = null;
            try{
                dataOutputStream = new DataOutputStream(s.getOutputStream());
            }catch (IOException ioe){
                System.out.println("erreur InOut");
                System.exit(1);
            }

            String ligne1 = bf.readLine();
            System.out.println(ligne1);
            while (true){
                String l = bf.readLine();
                System.out.println(l);
                if(l.equals("")){
                    break;
                }
            }

            String[] ligneStockee = ligne1.split(" ");
            String Ressource = ligneStockee[1];

            byte[] fichier = lireFichier(CHEMIN+Ressource);

            if(fichier != null){
                dataOutputStream.write("HTTP1.1 200 OK\r\n".getBytes());
                dataOutputStream.write("\r\n".getBytes());
                dataOutputStream.write(fichier);
            }else{
                dataOutputStream.write("HTTP1.1 404 NotFound\r\n".getBytes());
                dataOutputStream.write("\r\n".getBytes());
            }

            s.close();
            bf.close();
            dataOutputStream.close();

        }
    }
}
