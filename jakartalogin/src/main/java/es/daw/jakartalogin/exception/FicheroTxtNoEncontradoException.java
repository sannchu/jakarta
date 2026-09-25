package es.daw.jakartalogin.exception;

public class FicheroTxtNoEncontradoException extends Exception {


    public FicheroTxtNoEncontradoException(String message) {
        super("MAJETE!!! "+message);
    }

    public FicheroTxtNoEncontradoException(String message, Throwable cause) {
        super("MAJETE!!! " + message, cause);
    }
}
