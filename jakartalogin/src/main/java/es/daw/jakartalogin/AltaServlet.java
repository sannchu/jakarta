package es.daw.jakartalogin;

import es.daw.jakartalogin.exception.TxtNoEncontradoException;

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
    private List<String> tecnologias;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            tecnologias = leerFichero("/WEB-INF/datos/tecnologias.txt");
        } catch (IOException | TxtNoEncontradoException e) {
            LOGGER.severe(e.getMessage());
            throw new ServletException("No se han podido cargar las tecnologías", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tecnologias", tecnologias);
        request.getRequestDispatcher("/formulario.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // 1. LEER todos los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String tecnologia = request.getParameter("tecnologia");
        String nivel = request.getParameter("nivel");

        // 2. VALIDACIONES
        // Validar los parámetros!!!
        // Realmente los campos del formulario si no se rellenan llegan como caden vacía y no como null
        // Limpiar los campos sin provocar un NullPointerException si falta alguno.
        nombre = nombre == null ? "" : nombre.trim();
        email = email == null ? null : email.trim();
        tecnologia = tecnologia == null ? null : tecnologia.trim();
        nivel = nivel == null ? null : nivel.trim();


        LOGGER.info("nombre: "+nombre);
        LOGGER.info(String.format("email: %s",email));
        LOGGER.info(String.format("tecnologia: %s", tecnologia));
        LOGGER.info(String.format("nivel: %s",nivel));




        // -----------------
        // 3. PERSISTENCIA EN BD
        // EN ESTE PUNTO SE COMPROBARÍA EN BD SI EXISTE UN USUARIO CON ESE NOMBRE... ETC...
        // CONSIDERAMOS QUE TODO OK!!! LA LÓGICA DE NEGOCIO ES MUY SENCILLITA!!!!!
        // ------------------------

        // --------------------------------------------------------
        // 4. PREPARAR LA SALIDA. LO QUE SE VA A DEVOLVER
        // Enviar a la jsp como atributos los parámetros..
        request.setAttribute("nombre", nombre);
        request.setAttribute("email", email);
        request.setAttribute("tecnologia", tecnologia);
        request.setAttribute("nivel", nivel);

        // Si el nombre viene vacío, volver a la página del formulario indicando que
        // el nombre no puede estar vacío...
        if (nombre.isBlank()) {
            request.setAttribute("mensaje", "¡El nombre es obligatorio!");
            request.setAttribute("tecnologias", tecnologias);
            request.getRequestDispatcher("/formulario.jsp").forward(request, response);
            return;
        }

        // Llamar a la página confirmacion.jsp
        request.getRequestDispatcher("/confirmacion.jsp").forward(request, response);

    }

    /**
     * Lee un fichero de texto
     * @param pathFile ruta al fichero. Debe ser absoluta y encontrarse protegida en WEB-INF
     * @return List de cadena de texto de cada linea
     * @throws IOException si se produce un error al leer el fichero
     * @throws TxtNoEncontradoException si no existe el fichero
     */
    private List<String> leerFichero(String pathFile)
            throws IOException, TxtNoEncontradoException {
        List<String> lista = new ArrayList<>();

        InputStream is = getServletContext().getResourceAsStream(pathFile);




        // getResourceAsStream abre un flujo de bytes (InputStream)
        if (is == null) {
            throw new TxtNoEncontradoException(
                    "No se encuentra el fichero de texto: " + pathFile);
        }

        // try con recursos: todo lo que se declara dentro del paréntesis se cierra automáticamente (close())
        // InputStream -> bytes en crudo
        // InputStreamReader -> convierte esos bytes en caracteres según el charset
        // BufferedReader -> añade un buffer para leer línea a línea
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8))){
            String linea;
            while((linea = br.readLine()) != null){
                if (!linea.isBlank())
                    //lista.add(linea.trim());
                    lista.add(linea.trim());
            }
        }
        return lista;

    }

}
