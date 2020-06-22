package it.androidefettuccine.codicefiscaleapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


/*Classe dei database dei comuni che funge da punto di accesso principale per la
connessione sottostante ai dati relazionali persistenti dell'app.*/
@Database(entities = {Comune.class}, version = 2)
public abstract class ComuniDatabase extends RoomDatabase {
    public abstract ComuneDAO comuneDAO();
    /*Si definisce un singleton tramite la funzione getIstance(), per impedire l'apertura
    simultanea di più istanze del database*/
    private static volatile ComuniDatabase INSTANCE;
    public static ComuniDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ComuniDatabase.class,"comuni.db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}

