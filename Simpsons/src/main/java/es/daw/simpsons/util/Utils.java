package es.daw.simpsons.util;


public class Utils {
    /**
     * Convierte un String a Integer, devolviendo null si el String es nulo o vacío.
     * Lanza una excepción si el String no es un número entero válido.
     *
     * @param nombreCampo el nombre del campo que se está validando (para el mensaje de error)
     * @param valor el valor del campo a convertir
     * @return el Integer convertido, o null si el valor es nulo o vacío
     * @throws Exception si el valor no es un número entero válido
     */
    public static Integer leerEntero(String nombreCampo, String valor) throws Exception {
        // Comprobamos si el valor es nulo o vacío
        if (valor == null || valor.isEmpty())   {
            return null;

        }

        Integer num;
        try {
            // Comprobamos que el texto se puede convertir a entero
            num = Integer.valueOf(valor); //lanza un numberFormatException si no es un número válido


        } catch (NumberFormatException e) {
            throw new Exception ("El campo '" + nombreCampo + "' debe ser un número entero válido.");
        }

        if (num < 0) {
            throw new Exception ("El campo '" + nombreCampo + "' debe ser un número entero no negativo.");
        }
        return num;
    }

}
