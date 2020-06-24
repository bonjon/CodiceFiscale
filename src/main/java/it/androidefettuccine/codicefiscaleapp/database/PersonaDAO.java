package it.androidefettuccine.codicefiscaleapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonaDAO {
    @Query("SELECT * from persona")
    List<Persona> getAll();

    @Query("SELECT CF FROM persona")
    String[] loadNames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Persona persona);

    @Delete
    void deleteUsers(List<Persona> persone);
}
