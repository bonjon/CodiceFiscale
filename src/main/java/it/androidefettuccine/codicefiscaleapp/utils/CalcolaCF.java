package it.androidefettuccine.codicefiscaleapp.utils;

/*Classe che permette di calcolare il codice fiscale*/
public class CalcolaCF {
    private String nome, cognome, sesso, codCat;
    private String consonanti_Cognome = "";
    private String vocali_Cognome = "";
    private String consonanti_Nome = "";
    private String vocali_Nome = "";
    private int giorno, mese, anno;

    public CalcolaCF(String nome, String cognome, String sesso, String codCat, int giorno, int mese, int anno) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.codCat = codCat;
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
    }

    /*La funzione vera e propria che calcolerà il codice fiscale*/
    public String calcola() {
        cognome = Parole.eliminaSpaziBianchi(cognome).toUpperCase();
        nome = Parole.eliminaSpaziBianchi(nome).toUpperCase();
        popolazioneStringheConsVoc();
        String codCognome = this.calcolaCodCognome();
        String codNome = this.calcolaCodNome();
        String codDataNascitaSesso = this.calcolaCodDataNascitaSesso(this.anno, this.mese, this.giorno, this.sesso);
        String result = codCognome + codNome + codDataNascitaSesso + codCat;
        String charControl = this.calcolaCharControl(result);
        result += charControl;
        return result;
    }

    /*Popola le stirnghe consonanti e vocali*/
    private void popolazioneStringheConsVoc() {
        /*Cognome*/
        for (int i = 0; i < cognome.length(); i++) {
            if (Parole.isVocale(cognome.charAt(i))) {
                vocali_Cognome = vocali_Cognome + cognome.charAt(i);
            } else if (cognome.charAt(i) != ' ')
                consonanti_Cognome = consonanti_Cognome + cognome.charAt(i);
        }
        /*Nome*/
        for (int i = 0; i < nome.length(); i++) {
            if (Parole.isVocale(nome.charAt(i)))
                vocali_Nome = vocali_Nome + nome.charAt(i);
            else if (nome.charAt(i) != ' ')
                consonanti_Nome = consonanti_Nome + nome.charAt(i);
        }
    }

    /*Si calcola il codice del cognome, prima seconda e terza consonante*/
    private String calcolaCodCognome() {
        String codCognome = "";
        if (consonanti_Cognome.length() >= 3) {
            for (int i = 0; i < 3; i++)
                codCognome = codCognome + consonanti_Cognome.charAt(i);
            return codCognome;
        }
        if (consonanti_Cognome.length() == 2) {
            codCognome = codCognome + consonanti_Cognome.charAt(0) + consonanti_Cognome.charAt(1) + vocali_Cognome.charAt(0);
            return codCognome;
        }
        if (consonanti_Cognome.length() == 1) {
            if (vocali_Cognome.length() >= 2) {
                codCognome = codCognome + consonanti_Cognome.charAt(0) + vocali_Cognome.charAt(0) + vocali_Cognome.charAt(1);
                return codCognome;
            }
            /*Si gestiscono i casi in cui si hanno meno di 2 vocali, dunque si ricorre all'aggiunta di X a seconda delle
            lettere rimanenti*/
            if (vocali_Cognome.length() == 1) {
                codCognome = codCognome + consonanti_Cognome.charAt(0) + vocali_Cognome.charAt(0) + "X";
                return codCognome;
            } else {
                codCognome = codCognome + consonanti_Cognome.charAt(0) + "XX";
                return codCognome;
            }
        } else {
            /*Caso in cui ho solo voccali*/
            if (vocali_Cognome.length() >= 3) {
                codCognome = codCognome + vocali_Cognome.charAt(0) + vocali_Cognome.charAt(1) + vocali_Cognome.charAt(2);
                return codCognome;
            }
            /*Si gestiscono i casi in cui si hanno meno di 2 vocali, dunque si ricorre all'aggiunta di X a seconda delle
            lettere rimanenti*/
            if (vocali_Cognome.length() == 2) {
                codCognome = codCognome + vocali_Cognome.charAt(0) + vocali_Cognome.charAt(1) + "X";
                return codCognome;
            } else {
                codCognome = codCognome + vocali_Cognome.charAt(0) + "XX";
                return codCognome;
            }
        }
    }

    /*Si calcola il codice del nome, prima terza e quarta consonante*/
    private String calcolaCodNome() {
        String codNome = "";
        if (consonanti_Nome.length() > 3) {
            codNome = codNome + consonanti_Nome.charAt(0) + consonanti_Nome.charAt(2) + consonanti_Nome.charAt(3);
            return codNome;
        }
        if (consonanti_Nome.length() == 3) {
            for (int i = 0; i < 3; i++)
                codNome = codNome + consonanti_Nome.charAt(i);
            return codNome;
        }
        if (consonanti_Nome.length() == 2) {
            codNome = codNome + consonanti_Nome.charAt(0) + consonanti_Nome.charAt(1) + vocali_Nome.charAt(0);
            return codNome;
        }
        if (consonanti_Nome.length() == 1) {
            if (vocali_Nome.length() >= 2) {
                codNome = codNome + consonanti_Nome.charAt(0) + vocali_Nome.charAt(0) + vocali_Nome.charAt(1);
                return codNome;
            }
            /*Si gestiscono i casi in cui si hanno meno di 2 vocali, dunque si ricorre all'aggiunta di X a seconda delle
            lettere rimanenti*/
            if (vocali_Nome.length() == 1) {
                codNome = codNome + consonanti_Nome.charAt(0) + vocali_Nome.charAt(0) + "X";
                return codNome;
            } else {
                codNome = codNome + consonanti_Nome.charAt(0) + "XX";
                return codNome;
            }
        } else {
            /*Caso in cui ho solo vocali*/
            if (vocali_Nome.length() >= 3) {
                codNome = codNome + vocali_Nome.charAt(0) + vocali_Nome.charAt(1) + vocali_Nome.charAt(2);
                return codNome;
            }
            /*Si gestiscono i casi in cui si hanno meno di 2 vocali, dunque si ricorre all'aggiunta di X a seconda delle
            lettere rimanenti*/
            if (vocali_Nome.length() == 2) {
                codNome = codNome + vocali_Nome.charAt(0) + +vocali_Nome.charAt(1) + "X";
                return codNome;
            } else {
                codNome = codNome + vocali_Nome.charAt(0) + "XX";
                return codNome;
            }
        }
    }

    /*Si calcola il codice di data di nacita e di sesso, ultimr due cifre dell'anno, una lettera per il mese
    il giorno di nascita: in caso di sesso femminile si aggiunge 40*/
    private String calcolaCodDataNascitaSesso(int anno, int mese, int giorno, String sesso) {
        String codiceDataNascitaESesso;
        String codiceAnno;
        String codiceMese;
        String codiceGiornoESesso;
        codiceAnno = calcolaCodAnno(anno);
        codiceMese = calcolaCodMese(mese);
        codiceGiornoESesso = calcolaCodGiornoESesso(giorno, sesso);
        codiceDataNascitaESesso = codiceAnno + codiceMese + codiceGiornoESesso;
        return codiceDataNascitaESesso;
    }

    /*Una lettera a seconda del mese*/
    private String calcolaCodMese(int mese) {
        String risultato;
        switch (mese) {
            case 1:
                risultato = "A";
                break;
            case 2:
                risultato = "B";
                break;
            case 3:
                risultato = "C";
                break;
            case 4:
                risultato = "D";
                break;
            case 5:
                risultato = "E";
                break;
            case 6:
                risultato = "H";
                break;
            case 7:
                risultato = "L";
                break;
            case 8:
                risultato = "M";
                break;
            case 9:
                risultato = "P";
                break;
            case 10:
                risultato = "R";
                break;
            case 11:
                risultato = "S";
                break;
            case 12:
                risultato = "T";
                break;
            default:
                risultato = "";
                break;
        }
        return risultato;
    }

    /*Giorno e sesso*/
    private String calcolaCodGiornoESesso(int giorno, String sesso) {
        String s;
        if (sesso.equals("F")) {
            int codiceGiorno = giorno + 40;
            s = Integer.toString(codiceGiorno);
        } else {
            if (giorno < 10)
                s = "0" + giorno;
            else
                s = Integer.toString(giorno);
        }
        return s;
    }

    /*Prendo le ultime due cifre dell'anno*/
    private String calcolaCodAnno(int anno) {
        return Integer.toString(anno).substring(2);
    }

    /*Si calcola il carattere di controllo, è composto da 1 carattere e serve a verificare
    la correttezza dei precedenti caratteri in fase di digitazione. Le modalità di calcolo sono spiegate
    nel D.M. n° 345 del 23/12/1976 (Articolo 7)*/
    private String calcolaCharControl(String result) {
        String pari = Parole.getStringaPari(result);
        String dispari = Parole.getStringaDispari(result);
        int sommaPari = conversionePari(pari);
        int sommaDispari = conversioneDispari(dispari);
        int somma = sommaPari + sommaDispari;
        int resto = somma % 26;
        char restoConvertito = conversioneResto(resto);
        return Character.toString(restoConvertito);
    }

    /*Conversione del resto in un carattere per il terzo passaggio*/
    private char conversioneResto(int resto) {
        return (char) (resto + 65);
    }

    /*Conversione dei caratteri dispari per il secondo passaggio di creazione del carattere di controllo*/
    private int conversioneDispari(String dispari) {
        int risultato = 0;
        for (int i = 0; i < dispari.length(); i++) {
            char carattere = dispari.charAt(i);
            switch (carattere) {
                case '0':
                case 'A':
                    risultato += 1;
                    break;
                case '1':
                case 'B':
                    risultato += 0;
                    break;
                case '2':
                case 'C':
                    risultato += 5;
                    break;
                case '3':
                case 'D':
                    risultato += 7;
                    break;
                case '4':
                case 'E':
                    risultato += 9;
                    break;
                case '5':
                case 'F':
                    risultato += 13;
                    break;
                case '6':
                case 'G':
                    risultato += 15;
                    break;
                case '7':
                case 'H':
                    risultato += 17;
                    break;
                case '8':
                case 'I':
                    risultato += 19;
                    break;
                case '9':
                case 'J':
                    risultato += 21;
                    break;
                case 'K':
                    risultato += 2;
                    break;
                case 'L':
                    risultato += 4;
                    break;
                case 'M':
                    risultato += 18;
                    break;
                case 'N':
                    risultato += 20;
                    break;
                case 'O':
                    risultato += 11;
                    break;
                case 'P':
                    risultato += 3;
                    break;
                case 'Q':
                    risultato += 6;
                    break;
                case 'R':
                    risultato += 8;
                    break;
                case 'S':
                    risultato += 12;
                    break;
                case 'T':
                    risultato += 14;
                    break;
                case 'U':
                    risultato += 16;
                    break;
                case 'V':
                    risultato += 10;
                    break;
                case 'W':
                    risultato += 22;
                    break;
                case 'X':
                    risultato += 25;
                    break;
                case 'Y':
                    risultato += 24;
                    break;
                case 'Z':
                    risultato += 23;
                    break;
            }
        }
        return risultato;
    }

    /*Conversione dei caratteri pari per il secondo passaggio di creazione del carattere di controllo*/
    private int conversionePari(String pari) {
        int risultato = 0;
        for (int i = 0; i < pari.length(); i++) {
            char carattere = pari.charAt(i);
            int numero = Character.getNumericValue(carattere);

            if (Character.isLetter(carattere)) {
                //Se è una lettera
                numero = carattere - 65;
                risultato += numero;
            } else {
                //Se è un numero
                risultato += numero;
            }
        }
        return risultato;
    }
}
