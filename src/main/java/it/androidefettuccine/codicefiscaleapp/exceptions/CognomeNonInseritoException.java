package it.androidefettuccine.codicefiscaleapp.exceptions;

public class CognomeNonInseritoException extends Exception {
    public CognomeNonInseritoException(String cognome) {
        super(cognome);
    }
}
