package it.androidefettuccine.codicefiscaleapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/*Classe entity che rappresenta la tabella persona da inserire nel database*/
@Entity(tableName = "persona")
public class Persona {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "nome")
    String nome;
    @ColumnInfo(name = "cognome")
    String cognome;
    @ColumnInfo(name = "dataNascita")
    String dataNascita;
    @ColumnInfo(name = "CF")
    String CF;

    Persona(int id, String nome, String cognome, String dataNascita, String CF) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.CF = CF;
    }

    @Ignore
    public Persona(String nome, String cognome, String dataNascita, String CF) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.CF = CF;
    }
}
