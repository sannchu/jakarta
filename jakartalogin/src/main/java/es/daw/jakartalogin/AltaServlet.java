package es.daw.jakartalogin;

import es.daw.jakartalogin.exception.FicheroTxtNoEncontradoException;
import es.daw.jakartalogin.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet("/alta")
public class AltaServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AltaServlet.class.getName());

    private List<String> tecnologias = new ArrayList<>();
    private List<String> niveles = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            tecnologias = FileUtil.leerFichero(getServletContext(), "/WEB-INF/datos/tecnologias.txt");
            niveles = FileUtil.leerFichero(getServletContext(), "/WEB-INF/datos/niveles.txt");
        } catch (IOException | FicheroTxtNoEncontradoException e) {
            LOGGER.severe(e.getMessage());
            throw new ServletException("No se han podido cargar las listas del formulario", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tecnologias",tecnologias);
        request.setAttribute("niveles", niveles);
        request.getRequestDispatcher("/formulario.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // 1. LEER todos los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String tecnologia = request.getParameter("tecnologia");
        String nivel = request.getParameter("nivel");

        LOGGER.info("nombre: "+nombre);
        LOGGER.info(String.format("email: %s",email));
        LOGGER.info(String.format("tecnologia: %s",tecnologia));
        LOGGER.info(String.format("nivel: %s",nivel));

        // 2. VALIDACIONES
        // Validar los parámetros!!!
        nombre = nombre.strip();

        // Realmente los campos del formulario si no se rellenan llegan como cadena vacía y no como null
        email = email == null ? null : email.trim();
        tecnologia = tecnologia == null ? null : tecnologia.trim();
        nivel = nivel == null ? null : nivel.trim();

        request.setAttribute("tecnologia", tecnologia);
        request.setAttribute("nivel", nivel);

        // Si el nombre viene vacío que vuelva a la página del formulario indicando que
        // el nombre no puede estar vacío...

        if (nombre.isBlank()){
            request.setAttribute("mensaje","Majete!!! rellena el nombre que es obligatorio!!!!");
            request.setAttribute("tecnologias",tecnologias);
            request.setAttribute("niveles", niveles);
            request.getRequestDispatcher("/formulario.jsp").forward(request,response);
            return;
        }
        //------------------
        // -----------------
        // 3. PERSISTENCIA EN BD
        // EN ESTE PUNTO SE COMPROBARÍA EN BD SI EXISTE UN USUARIO CON ESE NOMBRE... ETC...
        // CONSIDERAMOS QUE TODO OK!!! LA LÓGICA DE NEGOCIO ES MUY SENCILLITA!!!!!
        // ------------------------


        // --------------------------------------------------------
        // 4. PREPARAR LA SALIDA. LO QUE SE VA A DEVOLVER
        // Pendiente enviar a la jsp como atributos los parámetros..
        request.setAttribute("nombre",nombre);
        request.setAttribute("email",email);
        request.setAttribute("tecnologia",tecnologia);
        request.setAttribute("nivel",nivel);

        // Pendiente llamar a la página confirmacion.jsp

        request.getRequestDispatcher("/confirmacion.jsp").forward(request,response);

    }

}
