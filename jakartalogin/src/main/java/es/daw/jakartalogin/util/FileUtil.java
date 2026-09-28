package es.daw.jakartalogin.util;

import es.daw.jakartalogin.exception.FicheroTxtParaLasListasNoEncontradoException;
import jakarta.servlet.ServletContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {

    private FileUtil() {
        // Clase de utilidades: no se instancia.
    }

    /**
     * Lee un fichero de texto de los recursos de la aplicación y devuelve sus líneas no vacías.
     */
    public static List<String> leerFichero(ServletContext servletContext, String pathFile)
            throws FicheroTxtParaLasListasNoEncontradoException, IOException {
        List<String> lista = new ArrayList<>();
        InputStream is = servletContext.getResourceAsStream(pathFile);

        if (is == null) {
            throw new FicheroTxtParaLasListasNoEncontradoException(
                    "No se encuentra el fichero de texto: " + pathFile);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank()) {
                    lista.add(linea.strip());
                }
            }
        }

        return lista;
    }


    public static List<String> leerFicheroApiStream(ServletContext servletContext, String pathFile)
            throws FicheroTxtParaLasListasNoEncontradoException, IOException {
        InputStream is = servletContext.getResourceAsStream(pathFile);

        if (is == null) {
            throw new FicheroTxtParaLasListasNoEncontradoException(
                    "No se encuentra el fichero de texto: " + pathFile);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return br.lines()
                    .filter(linea -> !linea.isBlank())
                    .map(String::strip)
                    .toList();
        }
    }

    /**
     * Lee un fichero de texto usando la API NIO y devuelve sus líneas no vacías.
     */
    public static List<String> leerFicheroNIO(ServletContext servletContext, String pathFile)
            throws FicheroTxtParaLasListasNoEncontradoException, IOException {
        List<String> lista = new ArrayList<>();
        String rutaReal = servletContext.getRealPath(pathFile);

        if (rutaReal == null) {
            throw new FicheroTxtParaLasListasNoEncontradoException(
                    "No se encuentra el fichero de texto: " + pathFile);
        }

        try (Stream<String> lineas = Files.lines(Paths.get(rutaReal), StandardCharsets.UTF_8)) {
            lista = lineas
                    .filter(linea -> !linea.isBlank())
                    .map(String::strip)
                    .toList();
        }

        return lista;
    }
}
