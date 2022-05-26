import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class main{
    //public static void main(String[] args){
        //ManejadorCanciones manejadorCanciones = new ManejadorCanciones(); 
        //manejadorCanciones.guardarCancion("Prueba", "genero", "artista", "album", "letra", 20);
    public void iniciarServidor(){
        Servidor server = new Servidor(5566);
        try {
            ServerSocket = new  ServerSocket(puerto);
        
            System.out.println("Servidor Iniciado");
            socket = serverSocket.accept();
            System.out.printf("Cliente conectado");
            os = new ObjectOutputStream(socket.getInputStream());
            is = new ObjectOutputStream(socket.getInputStream());

        }catch (IOException e){
            e.printStackTrace();
        }
    }
public static Server getServer(){
    if (Object.equals(Servidor,null)){
        Servidor = new Server();
    }
    return Servidor;
}
        
    //}
}
