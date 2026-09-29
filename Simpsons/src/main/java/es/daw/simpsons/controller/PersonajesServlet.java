package es.daw.simpsons.controller;

import es.daw.simpsons.model.Personaje;
import es.daw.simpsons.servicio.PersonajeServicio;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "personajes", urlPatterns = "/personajes")
public class PersonajesServlet extends HttpServlet {

    private final PersonajeServicio servicio = new PersonajeServicio();




    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. LEER PARÁMETROS DEL REQUEST
        String lugar = request.getParameter("lugar");
        String ordenarPor = request.getParameter("ordenarPor");
        boolean descendente = request.getParameter("descendente") != null
                ? Boolean.parseBoolean(request.getParameter("descendente")) : false;
        String edadMax = request.getParameter("edadMax");
        String limite = request.getParameter("limite");

        // 2. TRATAR LOS PARÁMETROS. CONVERSIONES Y VALIDACIONES



        // 3. LÓGICA. Necesito obtener los personajes de los Simpson
        List<Personaje> personajes = servicio.buscar();


        // 4. PASAR A LA VISTA TODO LO QUE NECESITE
        request.setAttribute("personajes", personajes);

        // 5. REENVIAR A LA VISTA (plantilla JSP)

        request.getRequestDispatcher("/personajes.jsp").forward(request, response);
    }
}
