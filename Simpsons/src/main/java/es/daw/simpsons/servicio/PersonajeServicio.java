package es.daw.simpsons.servicio;

import es.daw.simpsons.model.Personaje;
import es.daw.simpsons.repository.PersonajeRepository;

import java.util.List;

public class PersonajeServicio {
    private final PersonajeRepository repositorio = new PersonajeRepository();

    public List<Personaje> buscar() {
        return repositorio.findAll();
    }



}
