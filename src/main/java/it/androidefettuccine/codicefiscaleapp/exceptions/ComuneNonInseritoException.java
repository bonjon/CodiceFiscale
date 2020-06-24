package it.androidefettuccine.codicefiscaleapp.exceptions;

public class ComuneNonInseritoException extends Exception {
    public ComuneNonInseritoException(String place) {
        super(place);
    }
}
