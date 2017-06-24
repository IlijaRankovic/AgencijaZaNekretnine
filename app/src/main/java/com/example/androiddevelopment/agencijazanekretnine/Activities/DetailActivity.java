package com.example.androiddevelopment.agencijazanekretnine.Activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androiddevelopment.agencijazanekretnine.Model.Nekretnina;
import com.example.androiddevelopment.agencijazanekretnine.Model.ORMLightHelper;
import com.example.androiddevelopment.agencijazanekretnine.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import static com.example.androiddevelopment.agencijazanekretnine.Activities.MainActivity.NOTIF_STATUS;
import static com.example.androiddevelopment.agencijazanekretnine.Activities.MainActivity.NOTIF_TOAST;

/**
 * Created by androiddevelopment on 24.6.17..
 */

public class DetailActivity extends AppCompatActivity {

    private ORMLightHelper databaseHelper;
    private SharedPreferences prefs;
    private Nekretnina n;

    private EditText naziv;
    private EditText opis;
    private EditText adresa;
    private EditText brojTelefona;
    private EditText kvadratura;
    private EditText brojSoba;
    private EditText cena;


    public DetailActivity() {
    }

    public ORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMLightHelper.class);
        }
        return databaseHelper;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_notif_icon);
        mBuilder.setContentTitle("Agencija za nekretnine");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_add);

        mBuilder.setLargeIcon(bm);
        mNotificationManager.notify(1, mBuilder.build());
    }


    private void showMessage(String message){

        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

        if (toast){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (status){
            showStatusMesage(message);
        }

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int key = getIntent().getExtras().getInt(MainActivity.NEKRETNINA_KEY);

        try {
            n = getDatabaseHelper().getKontaktDao().queryForId(key);

            naziv= (EditText) findViewById(R.id.nekretnina_naziv);
            opis = (EditText) findViewById(R.id.nekretnina_opis);
            adresa = (EditText) findViewById(R.id.nekretnina_adresa);
            brojTelefona = (EditText) findViewById(R.id.nekretnina_broj_telefona);
            kvadratura = (EditText) findViewById(R.id.nekretnina_kvadratura);
            brojSoba = (EditText) findViewById(R.id.nekretnina_broj_soba);
            cena = (EditText) findViewById(R.id.nekretnina_cena);


            naziv.setText(n.getNaziv());
            opis.setText(n.getOpis());
            adresa.setText(n.getAdresa());
            brojTelefona.setText(n.getBrojTelefona());
            kvadratura.setText(n.getKvadratura());
            brojSoba.setText(n.getBrojSoba());
            cena.setText(n.getCena());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.contact_edit:

                n.setNaziv(naziv.getText().toString());
                n.setOpis(opis.getText().toString());
                n.setAdresa(adresa.getText().toString());
                n.setBrojTelefona(brojTelefona.getText().toString());
                n.setKvadratura(kvadratura.getText().toString());
                n.setBrojSoba(brojSoba.getText().toString());
                n.setCena(cena.getText().toString());


                try {
                    getDatabaseHelper().getKontaktDao().update(n);

                    showMessage("Podaci o nekretnini azurirani");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.contact_delete:
                try {
                    getDatabaseHelper().getKontaktDao().delete(n);

                    showMessage("Nekretnina izbrisana!");

                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
