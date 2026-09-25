package es.daw.jakartalogin.util;

import es.daw.jakartalogin.exception.FicheroTxtNoEncontradoException;
import jakarta.servlet.ServletContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private FileUtil() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Lee un fichero de texto de los recursos de la aplicación y devuelve sus líneas no vacías.
     */
    public static List<String> leerFichero(ServletContext servletContext, String pathFile)
            throws FicheroTxtNoEncontradoException, IOException {
        List<String> lista = new ArrayList<>();
        InputStream is = servletContext.getResourceAsStream(pathFile);

        if (is == null) {
            throw new FicheroTxtNoEncontradoException(
                    "No se encuentra el fichero de texto: " + pathFile);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank()) {
                    lista.add(linea.strip());
                }
            }
        } catch (IOException e) {
            throw new FicheroTxtNoEncontradoException(
                    "Error al leer el fichero de texto: " + pathFile, e);
        }

        return lista;
    }
}
