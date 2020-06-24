package it.androidefettuccine.codicefiscaleapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import it.androidefettuccine.codicefiscaleapp.R;
import it.androidefettuccine.codicefiscaleapp.database.PersonaDatabase;

public class Favourites extends AppCompatActivity {
    private PersonaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);
        setTitle(getString(R.string.preferiti));
        db = PersonaDatabase.getInstance(getApplicationContext());
        String[] boh = db.personaDAO().loadNames();
        TextView tv = findViewById(R.id.tv);
        tv.setText(boh[0]);
    }
}
