package it.androidefettuccine.codicefiscaleapp.exceptions;

public class SessoNonInseritoException extends Exception {
    public SessoNonInseritoException(String sesso) {
        super(sesso);
    }
}
