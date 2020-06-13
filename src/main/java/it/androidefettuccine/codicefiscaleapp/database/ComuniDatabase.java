package it.androidefettuccine.codicefiscaleapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/*Classe dei database dei comuni che funge da punto di accesso principale per la
connessione sottostante ai dati relazionali persistenti dell'app.*/
@Database(entities = {Comune.class}, version = 2)
public abstract class ComuniDatabase extends RoomDatabase {
    public abstract ComuneDAO comuneDAO();
}

