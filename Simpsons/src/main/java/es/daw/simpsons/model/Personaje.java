package es.daw.simpsons.model;

/**
 * Representa un personaje de Los Simpson.
 *
 * @param nombre nombre del personaje
 * @param apellido apellido del personaje
 * @param edad edad del personaje
 * @param ocupacion ocupación del personaje
 * @param lugar sitio donde se suele ver al personaje, como su casa o la escuela
 * @param principal indica si pertenece a la familia protagonista
 */
public record Personaje(
        String nombre,
        String apellido,
        int edad,
        String ocupacion,
        String lugar,
        boolean principal
) {

    /** Devuelve el nombre completo del personaje. */
    public String nombreCompleto() {
        return apellido.isBlank() ? nombre : nombre + " " + apellido;
    }

    /** Indica si el personaje es menor de edad. */
    public boolean esMenor() {
        return edad < 18;
    }

    // Getters JavaBean para que las propiedades estén disponibles desde JSP/EL.
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getEdad() { return edad; }
    public String getOcupacion() { return ocupacion; }
    public String getLugar() { return lugar; }
    public boolean isPrincipal() { return principal; }
    public String getNombreCompleto() { return nombreCompleto(); }
    public boolean isMenor() { return esMenor(); }
}
