import java.net.*;
import java.io.*;

import java.nio.charset.StandardCharsets;


public class Servidor {
    private Socket socket = null;
    private ServerSocket servidor = null;
    private DataInputStream mensajeCliente = null;

    public Servidor(int puerto){
        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Waiting for a client ...");
            socket = servidor.accept();
            System.out.println("Cliente conectado");
            
            mensajeCliente = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String lineaMensajeCliente = "";
            
            while (!lineaMensajeCliente.equals("FIN")) {
                try {
                    lineaMensajeCliente = mensajeCliente.readUTF();
                    System.out.println("asd");
                }
                catch(IOException i) {}
            } 

            socket.close();
            mensajeCliente.close();
        }
        catch(IOException i) {}
    }
}
