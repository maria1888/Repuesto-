import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ManejadorUsuarios {

    public boolean usuarioExiste(String nombreUsuario){
        JSONParser traductorJSON = new JSONParser();
        try {     
            JSONArray listaUsuariosJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/usuarios.json"));
            for (Object iteradorUsuario : listaUsuariosJSON){
                JSONObject usuarioJSON = (JSONObject) iteradorUsuario;
                String nombreUsuarioEncontrado = (String) usuarioJSON.get("nombreUsuario");
                if (nombreUsuario.equals(nombreUsuarioEncontrado)){
                    return true;
                }
            }
        } catch (Exception e) {}
        return false;
    }

    public int encontrarIndiceUsuario(String nombreUsuario){
        JSONParser traductorJSON = new JSONParser();
        int indiceUsuario = 0;
        try {     
            JSONArray listaUsuariosJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/usuarios.json"));
            for (Object iteradorUsuario : listaUsuariosJSON){
                JSONObject usuarioJSON = (JSONObject) iteradorUsuario;
                String nombreUsuarioEncontrado = (String) usuarioJSON.get("nombreUsuario");
                if (nombreUsuario.equals(nombreUsuarioEncontrado)){
                    return indiceUsuario;
                } else {
                    indiceUsuario = indiceUsuario + 1;
                }
            }
        } catch (Exception e) {}
        return -1;
    }

    // Falta agregarle la lista de amigos y la lista de generos favoritos;
    public void registrarUsuario(String nombreUsuario, String nombre, String primerApellido, String segundoApellido, String contrasena, int edad){
        if (!usuarioExiste(nombreUsuario)){
            JSONParser traductorJSON = new JSONParser();
            try {
                JSONArray listaUsuariosJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/usuarios.json"));
                JSONObject nuevoUsuarioJSON = new JSONObject();
                nuevoUsuarioJSON.put("nombreUsuario", nombreUsuario);
                nuevoUsuarioJSON.put("nombre", nombre);
                nuevoUsuarioJSON.put("primerApellido", primerApellido);
                nuevoUsuarioJSON.put("segundoApellido", segundoApellido);
                nuevoUsuarioJSON.put("contrasena", contrasena);
                nuevoUsuarioJSON.put("edad", edad);
                listaUsuariosJSON.add(nuevoUsuarioJSON);
                try (FileWriter archivoBaseDatos = new FileWriter("baseDatos/usuarios.json")) {
                    archivoBaseDatos.write(listaUsuariosJSON.toJSONString()); 
                    archivoBaseDatos.flush();
                } catch (IOException e) {}
            } catch (Exception e) {}
        } else {
            // Enviar error, el usuario ya existe en la base de datos;
        }
    }

    public boolean loginCorrecto(String nombreUsuarioColocado, String contrasenaColocada){
        int indiceUsuario = encontrarIndiceUsuario(nombreUsuarioColocado);
        if (indiceUsuario == -1){
            // Enviar error, el usuario no existe en la base de datos;
            return false;
        } else {
            JSONParser traductorJSON = new JSONParser();
            try {
                JSONArray listaUsuariosJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/usuarios.json"));
                JSONObject usuarioJSON = (JSONObject) listaUsuariosJSON.get(indiceUsuario);
                if (((String) usuarioJSON.get("contrasena")).equals(contrasenaColocada)){
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
    }

    public String encontrarAmigosUsuario(String nombreUsuario){
        int indiceUsuario = encontrarIndiceUsuario(nombreUsuario);
        if (indiceUsuario == -1){
            // Enviar error, el usuario no existe en la base de datos;
            return "";
        } else {
            JSONParser traductorJSON = new JSONParser();
            try {
                JSONArray listaUsuariosJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/usuarios.json"));
                JSONObject usuarioJSON = (JSONObject) listaUsuariosJSON.get(indiceUsuario);
                return "";
            } catch (Exception e) {
                return "";
            }
        }
    }
}
