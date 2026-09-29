package es.daw.simpsons.controller;

import es.daw.simpsons.model.Personaje;
import es.daw.simpsons.repository.PersonajeRepositorio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

@WebServlet(name = "personajes", urlPatterns = "/personajes")
public class PersonajesServlet extends HttpServlet {
    private final PersonajeRepositorio repositorio = new PersonajeRepositorio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String lugar = request.getParameter("lugar");
        Integer edadMax = parseNonNegativeInteger(request.getParameter("edadMax"));
        Integer limite = parseNonNegativeInteger(request.getParameter("limite"));
        String ordenarPor = request.getParameter("ordenarPor");
        boolean descendente = request.getParameter("descendente") != null;

        List<Personaje> personajes = repositorio.findAll().stream()
                .filter(p -> lugar == null || lugar.isBlank() || p.lugar().equals(lugar))
                .filter(p -> edadMax == null || p.edad() <= edadMax)
                .sorted(comparador(ordenarPor, descendente))
                .limit(limite == null ? Long.MAX_VALUE : limite.longValue())
                .toList();

        request.setAttribute("personajes", personajes);
        request.setAttribute("lugares", new TreeSet<>(repositorio.findAll().stream()
                .map(Personaje::lugar)
                .toList()));
        request.setAttribute("total", personajes.size());
        request.setAttribute("edadMax", edadMax);
        request.setAttribute("limite", limite);
        request.setAttribute("ordenarPor", ordenarPor == null ? "nombre" : ordenarPor);
        request.setAttribute("descendente", descendente);

        request.getRequestDispatcher("/WEB-INF/views/personajes.jsp").forward(request, response);
    }

    private static Integer parseNonNegativeInteger(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            int parsed = Integer.parseInt(value);
            return parsed >= 0 ? parsed : null;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static Comparator<Personaje> comparador(String campo, boolean descendente) {
        Comparator<Personaje> comparator = switch (campo == null ? "nombre" : campo) {
            case "apellido" -> Comparator.comparing(Personaje::apellido, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Personaje::nombre, String.CASE_INSENSITIVE_ORDER);
            case "edad" -> Comparator.comparingInt(Personaje::edad)
                    .thenComparing(Personaje::nombre, String.CASE_INSENSITIVE_ORDER);
            default -> Comparator.comparing(Personaje::nombre, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Personaje::apellido, String.CASE_INSENSITIVE_ORDER);
        };
        return descendente ? comparator.reversed() : comparator;
    }
}
