package it.androidefettuccine.codicefiscaleapp.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import it.androidefettuccine.codicefiscaleapp.R;
import it.androidefettuccine.codicefiscaleapp.database.Comune;
import it.androidefettuccine.codicefiscaleapp.database.ComuniDatabase;
import it.androidefettuccine.codicefiscaleapp.exceptions.CognomeNonInseritoException;
import it.androidefettuccine.codicefiscaleapp.exceptions.ComuneNonInseritoException;
import it.androidefettuccine.codicefiscaleapp.exceptions.ComuneNonValidoException;
import it.androidefettuccine.codicefiscaleapp.exceptions.NomeNonInseritoException;
import it.androidefettuccine.codicefiscaleapp.exceptions.SessoNonInseritoException;
import it.androidefettuccine.codicefiscaleapp.fragments.FragmentCode;
import it.androidefettuccine.codicefiscaleapp.utils.CalcolaCF;

public class MainActivity extends AppCompatActivity {

    private ComuniDatabase db;
    ArrayAdapter<String> adapter;//Servirà poi per dare i suggerimenti all'AutoCompleteTextView
    String nome, cognome, sesso, comune, codCat;
    int anno, giorno, mese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = ComuniDatabase.getInstance(MainActivity.this);
        populateDB();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, db.comuneDAO().loadNames());
        new Holder();
    }

    /*Si popola il db di comuni*/
    private void populateDB() {
        List<Comune> oldList = db.comuneDAO().getAll();
        if (oldList.isEmpty()) {
            List<Comune> comuni = readCsvFile();
            db.comuneDAO().insertAll(comuni);
        }
    }

    /*Legge il csv file dei comuni e i dati che mi servono che dovranno essere inseriti nel database*/
    public List<Comune> readCsvFile() {
        List<Comune> comuniList = new ArrayList<>();
        BufferedReader reader;
        try {
            /*Si legge il file nella cartella assets*/
            reader = new BufferedReader(new InputStreamReader(getAssets().open("comuni.csv"), StandardCharsets.UTF_8));
            String name, siglaAut, codCat, nameFull;
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                /*Salvo nome, siglaAutomobilistica e codiceCatastale all'interno di tre stringhe,guardano il file
                csv si vedono le loro posizioni cioè 6,13,18*/
                String[] Line = mLine.split(";");
                name = Line[6];
                siglaAut = Line[13];
                codCat = Line[18];
                nameFull = name + "(" + siglaAut + ")";
                /*Salvo i comuni nella lista di comuni che andremo poi ad inserire nel database successivamente*/
                Comune comune = new Comune(nameFull, codCat);
                comuniList.add(comune);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return comuniList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Si apre il menu e si clicca su about*/
        if (item.getItemId() == R.id.itemAbout) {
            startActivity(new Intent(this, About.class));
            return true;
        }
        /*Si clicca sui preferiti dove saranno salvati i codici fiscali*/
        if (item.getItemId() == R.id.itemPreferiti) {
            startActivity(new Intent(this, Favourites.class));
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            /*Maschio*/
            case R.id.rbM:
                sesso = getString(R.string.m);
                break;
            /*Femmina*/
            case R.id.rbF:
                sesso = getString(R.string.f);
                break;
        }
    }

    class Holder implements View.OnClickListener {
        private AutoCompleteTextView tvComuni;
        private EditText etNome, etCognome;
        private RadioGroup rgSex;
        private DatePicker dpData;

        Holder() {
            /*Si trovano gli Ids*/
            tvComuni = findViewById(R.id.tvComuni);
            tvComuni.setThreshold(2);
            tvComuni.setAdapter(adapter);
            etNome = findViewById(R.id.etName);
            etCognome = findViewById(R.id.etCognome);
            Button btnCalculate = findViewById(R.id.btnCalculate);
            rgSex = findViewById(R.id.rgSex);
            dpData = findViewById(R.id.dpData);
            /*Si setta il bottone che spingeremo per andare a calcolare il codice fiscale*/
            btnCalculate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnCalculate) {
                try {
                    /*Si prendono la data di nascita e tutte le varie informazioni nelle view*/
                    anno = dpData.getYear();
                    mese = dpData.getMonth() + 1;
                    giorno = dpData.getDayOfMonth();
                    comune = tvComuni.getText().toString();
                    codCat = db.comuneDAO().getCode(comune);
                    nome = etNome.getText().toString();
                    cognome = etCognome.getText().toString();
                    /*Si gestisce il caso in cui non si inserisca alcun comune*/
                    if (comune == null || comune.equals("")) {
                        tvComuni.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        tvComuni.setError(getString(R.string.noluogo));
                        throw new ComuneNonInseritoException(getString(R.string.noluogo));
                    }
                    /*Caso in cui il comune non è valido*/
                    if (db.comuneDAO().getName(comune) == null) {
                        tvComuni.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        tvComuni.setError(getString(R.string.luogo_not_valid));
                        throw new ComuneNonValidoException(getString(R.string.luogo_not_valid));
                    }
                    /*Si gestisce il caso in cui non si inserisca alcun nome*/
                    if (nome == null || nome.equals("")) {
                        etNome.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        etNome.setError(getString(R.string.nonome));
                        throw new NomeNonInseritoException(getString(R.string.nonome));
                    }
                    /*Si gestisce il caso in cui non si inserisca alcun cognome*/
                    if (cognome.equals("")) {
                        etCognome.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        etCognome.setError(getString(R.string.nocognome));
                        throw new CognomeNonInseritoException(getString(R.string.nocognome));
                    }
                    RadioButton rb;
                    /*Si gestisce il caso in cui non si inserisce il sesso*/
                    if (rgSex.getCheckedRadioButtonId() == -1) {
                        rgSex.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        throw new SessoNonInseritoException(getString(R.string.nosesso));
                    } else {
                        rb = findViewById(rgSex.getCheckedRadioButtonId());
                    }
                    sesso = rb.getText().toString();
                    /*Calcolo il codice fiscale*/
                    CalcolaCF calcolo = new CalcolaCF(nome, cognome, sesso, codCat, giorno, mese, anno);
                    String value = calcolo.calcola();
                    createFragment(value);
                    clearText();
                } catch (ComuneNonValidoException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, getString(R.string.luogo_not_valid), Toast.LENGTH_LONG).show();
                } catch (ComuneNonInseritoException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, getString(R.string.noluogo), Toast.LENGTH_LONG).show();
                } catch (NomeNonInseritoException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, getString(R.string.nonome), Toast.LENGTH_LONG).show();
                } catch (CognomeNonInseritoException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, getString(R.string.nocognome), Toast.LENGTH_LONG).show();
                } catch (SessoNonInseritoException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, R.string.nosesso, Toast.LENGTH_LONG).show();
                }

            }
        }

        /*Crea il fragment dove si vedrà il codice fiscale calcolato*/
        private void createFragment(String value) {
            Bundle bundle = new Bundle();
            bundle.putString("CF", value);
            bundle.putString("NOME", nome);
            bundle.putString("COGNOME", cognome);
            String data = giorno + "/" + mese + "/" + anno;
            bundle.putString("DATANASCITA", data);
            FragmentCode fragment = new FragmentCode();
            fragment.setArguments(bundle);
            fragment.show(getSupportFragmentManager(), "Fragment");
        }

        /*Azzera le editText*/
        private void clearText() {
            tvComuni.setText("");
            etCognome.setText("");
            etNome.setText("");
        }
    }
}
