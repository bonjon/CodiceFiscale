package it.androidefettuccine.codicefiscaleapp.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/*Classe entity che rappresenta la tabella Comune da inserire nel database*/
@Entity(tableName = "comune")
public class Comune {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    int id;
    @ColumnInfo(name = "nome")
    public String nome;
    @ColumnInfo(name = "codiceCatastale")
    String codiceCatastale;

    Comune(int id, String nome, String codiceCatastale) {
        this.id = id;
        this.nome = nome;
        this.codiceCatastale = codiceCatastale;
    }

    @Ignore
    public Comune(String nome, String codiceCatastale) {
        this.nome = nome;
        this.codiceCatastale = codiceCatastale;
    }
}
