package it.androidefettuccine.codicefiscaleapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Objects;

import it.androidefettuccine.codicefiscaleapp.R;
import it.androidefettuccine.codicefiscaleapp.database.Persona;
import it.androidefettuccine.codicefiscaleapp.database.PersonaDatabase;

/*Fragment dove si andrà a mettere il codice fiscale calcolato*/
public class FragmentCode extends DialogFragment {
    private String CF, nome, cognome, data;
    private PersonaDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        db = PersonaDatabase.getInstance(getContext());
        assert getArguments() != null;
        /*Si prendono tutti i vari argomenti passati dalla main activity*/
        CF = getArguments().getString("CF");
        nome = Objects.requireNonNull(getArguments().getString("NOME")).toUpperCase();
        cognome = Objects.requireNonNull(getArguments().getString("COGNOME")).toUpperCase();
        data = getArguments().getString("DATANASCITA");
        View v = inflater.inflate(R.layout.fragment_code, container, false);
        try {
            new Holder(v);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return v;
    }

    class Holder implements View.OnClickListener {
        private TextView tvCF, tvNome, tvCognome, tvData;

        Holder(View v) throws WriterException {
            /*Si prendono gli id*/
            tvCF = v.findViewById(R.id.tvCF);
            tvNome = v.findViewById(R.id.tvName);
            tvCognome = v.findViewById(R.id.tvSurname);
            tvData = v.findViewById(R.id.tvDate);
            ImageView ivBarcode = v.findViewById(R.id.ivBarcode);
            Button btnSi = v.findViewById(R.id.btnSi);
            Button btnNo = v.findViewById(R.id.btnNo);
            /*Si settano le varie TextView*/
            tvCF.setText(CF);
            tvNome.setText(nome);
            tvCognome.setText(cognome);
            tvData.setText(data);
            /*Si settano i vari bottoni Si se si vuole salvare nel db No viceversa*/
            btnNo.setOnClickListener(this);
            btnSi.setOnClickListener(this);
            generateCode(CF, ivBarcode);
        }

        /*Funzione che genera il barcode in bitmap corrispondente al codice fiscale calcolato
        utilizzando la libreria stand-alone Zxing, tipo di barcode Code39*/
        void generateCode(String value, ImageView iv) throws WriterException {
            com.google.zxing.MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bm = writer.encode(value, BarcodeFormat.CODE_39, 250, 50); //Code39
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bm);
            iv.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View v) {
            /*Caso in cui si vuole salvare il codice fiscale nel database*/
            if (v.getId() == R.id.btnSi) {
                /*Si prendono le informazioni che andranno poi inserite nel db*/
                String nome = tvNome.getText().toString();
                String cognome = tvCognome.getText().toString();
                String dataNascita = tvData.getText().toString();
                String CF = tvCF.getText().toString();
                Persona persona = new Persona(nome, cognome, dataNascita, CF);
                /*Inserisco la persona nel db*/
                db.personaDAO().insert(persona);
                Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_LONG).show();
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().remove(FragmentCode.this).commit();
            }
            /*Viceversa*/
            if (v.getId() == R.id.btnNo) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().remove(FragmentCode.this).commit();
            }
        }
    }
}
