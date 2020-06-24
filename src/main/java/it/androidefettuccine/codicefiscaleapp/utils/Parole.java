package it.androidefettuccine.codicefiscaleapp.utils;

/*Classe che serve per gestire consonanti vocali e caratteri dispari e pari*/
class Parole {
    private static final String VOCALI = "AEIOU";

    /*Cognomi che hanno bisogno di spazi*/
    static String eliminaSpaziBianchi(String cognome) {
        return cognome.replaceAll("\\s+", ""); //"\\s+" indica sequenze di uno o più spazi bianchi
    }

    /*Prende la stringa di caratteri in posizione dispari*/
    static String getStringaDispari(String result) {
        StringBuilder risultato = new StringBuilder();
        for (int i = 0; i < result.length(); i++) {
            if ((i + 1) % 2 == 1) {
                risultato.append(result.charAt(i));
            }
        }
        return risultato.toString();
    }

    /*Prende la stringa di caratteri in posizione pari*/
    static String getStringaPari(String result) {
        StringBuilder risultato = new StringBuilder();
        for (int i = 0; i < result.length(); i++) {
            if ((i + 1) % 2 == 0) {
                risultato.append(result.charAt(i));
            }
        }
        return risultato.toString();
    }

    /*Controlla se un carattere è una vocale*/
    static boolean isVocale(char character) {
        return VOCALI.contains(Character.toString(character));
    }
}
