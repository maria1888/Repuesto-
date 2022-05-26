import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ManejadorCanciones {
    public boolean cancionExiste(String nombre){
        JSONParser traductorJSON = new JSONParser();
        try {     
            JSONArray listaCancionesJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/canciones.json"));
            for (Object iteradorCancion : listaCancionesJSON){
                JSONObject cancionJSON = (JSONObject) iteradorCancion;
                String nombreCancionEncontrada = (String) cancionJSON.get("nombre");
                if (nombre.equals(nombreCancionEncontrada)){
                    return true;
                }
            }
        } catch (Exception e) {}
        return false;
    }

    public void actualizarBaseDatosCanciones(JSONArray listaCancionesJSON){
        try (FileWriter archivoBaseDatos = new FileWriter("baseDatos/canciones.json")) {
            archivoBaseDatos.write(listaCancionesJSON.toJSONString()); 
            archivoBaseDatos.flush();
        } catch (IOException e) {}
    }

    public int encontrarIndiceCancion(String nombre){
        JSONParser traductorJSON = new JSONParser();
        int indiceCancion = 0;
        try {     
            JSONArray listaCancionesJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/canciones.json"));
            for (Object iteradorCancion : listaCancionesJSON){
                JSONObject cancionJSON = (JSONObject) iteradorCancion;
                String nombreCancionEncontrada = (String) cancionJSON.get("nombre");
                if (nombre.equals(nombreCancionEncontrada)){
                    return indiceCancion;
                } else {
                    indiceCancion = indiceCancion + 1;
                }
            }
        } catch (Exception e) {}
        return -1;
    }

    // Falta el audio de la cancion;
    public void guardarCancion(String nombre, String genero, String artista, String album, String letra, int anio){
        if (!cancionExiste(nombre)){
            JSONParser traductorJSON = new JSONParser();
            try {
                JSONArray listaCancionesJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/canciones.json"));
                JSONObject nuevaCancionJSON = new JSONObject();
                nuevaCancionJSON.put("nombre", nombre);
                nuevaCancionJSON.put("genero", genero);
                nuevaCancionJSON.put("artista", artista);
                nuevaCancionJSON.put("album", album);
                nuevaCancionJSON.put("letra", letra);
                nuevaCancionJSON.put("anio", anio);
                listaCancionesJSON.add(nuevaCancionJSON);
                actualizarBaseDatosCanciones(listaCancionesJSON);
            } catch (Exception e) {}
        } else {
            // Enviar error, la cancion ya existe en la base de datos;
        }
    } 
    
    public void modificarCancion(String nombre, String datoModificar, String nuevoDatoString, int nuevoDatoInt){
        int indiceCancion = encontrarIndiceCancion(nombre);
        if (indiceCancion == -1){
            // Enviar error, la cancion no existe en la base de datos;
        } else {
            JSONParser traductorJSON = new JSONParser();
            try {
                JSONArray listaCancionesJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/canciones.json")); 
                JSONObject nuevaCancionJSON = (JSONObject) listaCancionesJSON.get(indiceCancion);
                if (datoModificar.equals("anio")){
                    nuevaCancionJSON.put(datoModificar, nuevoDatoInt);
                } else {
                    nuevaCancionJSON.put(datoModificar, nuevoDatoString);
                }
                listaCancionesJSON.remove(indiceCancion);
                listaCancionesJSON.add(nuevaCancionJSON);
                actualizarBaseDatosCanciones(listaCancionesJSON);
            } catch (Exception e) {}
        }
    }

    public void eliminarCancion(String nombre){
        int indiceCancion = encontrarIndiceCancion(nombre);
        if (indiceCancion == -1){
            // Enviar error, la cancion no existe en la base de datos;
        } else {
            JSONParser traductorJSON = new JSONParser();
            try {
                JSONArray listaCancionesJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/canciones.json")); 
                listaCancionesJSON.remove(indiceCancion);
                actualizarBaseDatosCanciones(listaCancionesJSON);
            } catch (Exception e) {}
            // Eliminar cancion de la base de datos de audios;
        }
    }

    public void calificarCancion(String nombreUsuario, String nombreCancion, float calificacion){
        int indiceCancion = encontrarIndiceCancion(nombreCancion);
        if (indiceCancion == -1){
            // Enviar error, la cancion no existe en la base de datos;
        } else {
            JSONParser traductorJSON = new JSONParser();
            try {
                JSONArray listaCancionesJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/canciones.json")); 
                JSONObject cancionCalificadaJSON = (JSONObject) listaCancionesJSON.get(indiceCancion);
                cancionCalificadaJSON.put("calificacion" + nombreUsuario, calificacion);
                listaCancionesJSON.remove(indiceCancion);
                listaCancionesJSON.add(cancionCalificadaJSON);
                actualizarBaseDatosCanciones(listaCancionesJSON);
            } catch (Exception e) {}
        }
    }

    public float encontrarCalificacionCancion(String nombreUsuario, String nombreCancion){
        int indiceCancion = encontrarIndiceCancion(nombreCancion);
        if (indiceCancion == -1){
            // Enviar error, la cancion no existe en la base de datos;
            return 0;
        } else {
            JSONParser traductorJSON = new JSONParser();
            try {
                JSONArray listaCancionesJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/canciones.json")); 
                JSONObject cancionSolicitadaJSON = (JSONObject) listaCancionesJSON.get(indiceCancion);
                return (float) cancionSolicitadaJSON.get("calificacion" + nombreUsuario);
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public String encontrarMetadataCancion(String nombreCancion){
        int indiceCancion = encontrarIndiceCancion(nombreCancion);
        if (indiceCancion == -1){
            // Enviar error, la cancion no existe en la base de datos;
            return "";
        } else {
            JSONParser traductorJSON = new JSONParser();
            try {
                JSONArray listaCancionesJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/canciones.json")); 
                JSONObject cancionSolicitadaJSON = (JSONObject) listaCancionesJSON.get(indiceCancion);
                return cancionSolicitadaJSON.toJSONString();
            } catch (Exception e) {
                return "";
            }
        }
    }

    public JSONArray ordenarCancionesQuickSort(){
        JSONParser traductorJSON = new JSONParser();
        try {
            JSONArray listaCancionesJSON = (JSONArray) traductorJSON.parse(new FileReader("baseDatos/canciones.json")); 
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public JSONArray ordenarCancionesInsertionSort(){
        return null;
    }

    public JSONArray ordenarCancionesBubbleSort(){
        return null;
    }

    public JSONArray enviarCancionesOrdenadas(String tipoOrdenamiento){
        if (tipoOrdenamiento.equals("quickSort")){
            return ordenarCancionesQuickSort();
        } else if (tipoOrdenamiento.equals("insertionSort")){
            return ordenarCancionesInsertionSort();
        } else {
            return ordenarCancionesBubbleSort();
        }
    }

}
