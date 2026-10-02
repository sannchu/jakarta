package es.daw.simpsons.controller;

import es.daw.simpsons.model.Personaje;
import es.daw.simpsons.servicio.PersonajeServicio;
import es.daw.simpsons.util.Utils;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. LEER LOS PARÁMETROS DEL REQUEST
        String lugar = request.getParameter("lugar");
        String ordenarPor = request.getParameter("ordenarPor");
        boolean descendente = request.getParameter("descendente") != null;
        String edadMax = request.getParameter("edadMax");
        String limite = request.getParameter("limite");

        // Si no se eligió un criterio de orden, ordenar por nombre.
        if (ordenarPor == null || ordenarPor.isBlank()) {
            ordenarPor = "nombre";
        }

        // La lista se inicializa vacía para que nunca sea null.
        List<Personaje> personajes = new ArrayList<>();

        // Pasar a la vista los valores necesarios para conservar los filtros.
        request.setAttribute("lugares", servicio.lugares());
        request.setAttribute("lugarSeleccionado", lugar);
        request.setAttribute("ordenarPor", ordenarPor);
        request.setAttribute("descendente", descendente);
        request.setAttribute("edadMax", edadMax);
        request.setAttribute("limite", limite);

        // 2. TRATAR LOS PARÁMETROS. CONVERSIONES Y VALIDACIONES
        try {
            Integer edadMaxInt = Utils.leerEntero("Edad máxima", edadMax);
            Integer limiteInt = Utils.leerEntero(
                    "Número de personajes máximos a mostrar", limite);

            // 3. LÓGICA. Necesito obtener los personajes de los Simpson
            // PENDIENTE: enviar los parámetros de filtrado y ordenación al servicio.
            personajes = servicio.buscar(lugar, edadMaxInt, ordenarPor, descendente, limiteInt);
        } catch (Exception e) {
            // Escribir un mensaje de error en personajes.jsp.
            request.setAttribute("error", e.getMessage());
        }

        // 4. PASAR A LA VISTA TODO LO QUE NECESITE
        request.setAttribute("personajes", personajes);
        request.setAttribute("lugares", servicio.lugaresDisponibles());

        // 5. REENVIAR A LA VISTA (plantilla JSP)
        request.getRequestDispatcher("/personajes.jsp").forward(request, response);
    }
}
