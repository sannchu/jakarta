package es.daw.simpsons.servicio;

import es.daw.simpsons.model.Personaje;
import es.daw.simpsons.repository.PersonajeRepository;

import java.util.Comparator;
import java.util.List;


public class PersonajeServicio {
    private final PersonajeRepository repositorio = new PersonajeRepository();

    public List<Personaje> buscar(String lugar,
                                  Integer edadMaxima,
                                  String ordenarPor, //pendiente
                                  boolean descendente, //pendiente para otro dia
                                  Integer limite) {

        Comparator<Personaje> comparador = switch (ordenarPor == null ? "nombre" : ordenarPor) {
            case "apellido" -> Comparator.comparing(Personaje::apellido, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Personaje::nombre, String.CASE_INSENSITIVE_ORDER);
            case "edad" -> Comparator.comparingInt(Personaje::edad)
                    .thenComparing(Personaje::nombre, String.CASE_INSENSITIVE_ORDER);
            default -> Comparator.comparing(Personaje::nombre, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Personaje::apellido, String.CASE_INSENSITIVE_ORDER);
        };
        if (descendente) {
            comparador = comparador.reversed();
        }

        var resultados = repositorio.findAll().stream()
                .filter(p -> lugar == null || lugar.isBlank() || p.lugar().equalsIgnoreCase(lugar))
                .filter(p -> edadMaxima == null || p.edad() <= edadMaxima)
                .sorted(comparador);

        return resultados.limit(limite == null ? Long.MAX_VALUE : limite).toList();
    }

    public List<String> lugares() {
        return repositorio.findLugares();
    }


    /**
     *
     */
    public List<String> lugaresDisponibles() {
        return repositorio.findAll().stream()
                .map(Personaje::lugar)
                .distinct()
                .sorted()
                .toList();
                //.collect(Collectors.toList());
    }

}
