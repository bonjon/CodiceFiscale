package it.androidefettuccine.codicefiscaleapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/*Classe dei database delle persone*/
@Database(entities = {Persona.class}, version = 2)
public abstract class PersonaDatabase extends RoomDatabase {
    public abstract PersonaDAO personaDAO();

    /*Si definisce un singleton tramite la funzione getIstance(), per impedire l'apertura
    simultanea di più istanze del database*/
    private static volatile PersonaDatabase INSTANCE;

    public static PersonaDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PersonaDatabase.class, "persone.db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
