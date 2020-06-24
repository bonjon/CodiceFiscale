package it.androidefettuccine.codicefiscaleapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/*Interfaccia che contiene i metodi per accedere al database*/
@Dao
public interface ComuneDAO {
    @Query("SELECT * FROM comune")
    List<Comune> getAll();

    @Query("SELECT nome FROM comune")
    String[] loadNames();

    @Query("SELECT codiceCatastale FROM comune WHERE nome == :nomCom")
    String getCode(String nomCom);

    @Query("SELECT nome FROM comune where nome == :nomCom")
    String getName(String nomCom);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Comune> comuni);

}
