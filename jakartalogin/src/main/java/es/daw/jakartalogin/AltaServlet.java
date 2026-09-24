package es.daw.jakartalogin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet("/alta")
public class AltaServlet extends HttpServlet {

private static final Logger LOGGER = Logger.getLogger(AltaServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<String> tecnologias = leerFichero("/WEB-INF/datos/tecnologias.txt");
            LOGGER.info(tecnologias.toString());

            // String opcional = request.getParameter("opcional").trim();


            request.setAttribute("tecnologias", tecnologias);
            request.getRequestDispatcher("/formulario.jsp").forward(request, response);



        }catch ( IOException e){
            // PENDIENTE: enviar a una pagina jsp de error el mensaje de error...
            LOGGER.severe(e.getMessage());


            //Añadir como atributo mensaje de error
            request.setAttribute("mensajeError", e.getMessage());

            request.getRequestDispatcher("/error.jsp").forward(request, response);

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // Leer todos los parámetros del formulario
        String nombre = trim(request.getParameter("nombre"));
        String email = trim(request.getParameter("email"));
        String tecnologia = trim(request.getParameter("tecnologia"));
        String nivel = trim(request.getParameter("nivel"));

        LOGGER.info("nombre: "+nombre);
        LOGGER.info(String.format("email: %s",email));
        LOGGER.info(String.format("tecnologia: %s", tecnologia));
        LOGGER.info(String.format("nivel: %s",nivel));

        request.setAttribute("nombre", nombre);
        request.setAttribute("email", email);
        request.setAttribute("tecnologia", tecnologia);
        request.setAttribute("nivel", nivel);

        String mensaje = validar(nombre, email, tecnologia, nivel);
        if (mensaje != null) {
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("tecnologias", leerFichero("/WEB-INF/datos/tecnologias.txt"));
            request.getRequestDispatcher("/formulario.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/confirmacion.jsp").forward(request, response);

    }

    private List<String> leerFichero(String pathFile) throws IOException{
        List<String> lista = new ArrayList<>();

        InputStream is = getServletContext().getResourceAsStream(pathFile);




        if ( is ==null)
            throw new IOException("No se encuentra el fichero de texto: "+ pathFile);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8))){
            String linea;
            while((linea = br.readLine()) != null){
                if (!linea.isBlank())
                    lista.add(linea.trim());
            }
        }
        return lista;

    }

    private String trim(String valor) {
        return valor == null ? null : valor.trim();
    }

    private String validar(String nombre, String email, String tecnologia, String nivel) {
        if (nombre == null || nombre.isBlank()) {
            return "El nombre no puede estar vacío.";
        }
        if (email == null || email.isBlank()) {
            return "El email es obligatorio.";
        }
        if (tecnologia == null || tecnologia.isBlank()) {
            return "Debes seleccionar una tecnología.";
        }
        if (nivel == null || nivel.isBlank()) {
            return "Debes seleccionar tu nivel actual.";
        }
        return null;
    }


}
