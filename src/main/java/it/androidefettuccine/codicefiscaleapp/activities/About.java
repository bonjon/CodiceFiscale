package it.androidefettuccine.codicefiscaleapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import it.androidefettuccine.codicefiscaleapp.R;

/*Classe about dove andremo a mettere tutte le nostre informazioni*/
public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setTitle(getString(R.string.about));
    }
}
