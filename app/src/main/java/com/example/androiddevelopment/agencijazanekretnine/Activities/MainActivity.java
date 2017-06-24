package com.example.androiddevelopment.agencijazanekretnine.Activities;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.agencijazanekretnine.Dialogs.AppDialog;
import com.example.androiddevelopment.agencijazanekretnine.Model.Nekretnina;
import com.example.androiddevelopment.agencijazanekretnine.Model.ORMLightHelper;
import com.example.androiddevelopment.agencijazanekretnine.Preferences.Preferences;
import com.example.androiddevelopment.agencijazanekretnine.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.List;

/**
 * Created by androiddevelopment on 24.6.17..
 */

public class MainActivity extends AppCompatActivity {

    private ORMLightHelper databaseHelper;
    private SharedPreferences SharedPrefs;

    public static String NEKRETNINA_KEY = "ACTOR_KEY";
    public static String NOTIF_TOAST = "notif_toast";
    public static String NOTIF_STATUS = "notif_status";


    public ORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMLightHelper.class);
        }
        return databaseHelper;
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.lista_nekretnina);

        if (listview != null) {
            ArrayAdapter<Nekretnina> adapter = (ArrayAdapter<Nekretnina>) listview.getAdapter();

            if (adapter != null) {
                try {
                    adapter.clear();
                    List<Nekretnina> list = getDatabaseHelper().getKontaktDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void prikaziStatusPoruka(String message) {

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_notif_icon);
        mBuilder.setContentTitle("AgencijaZaNekretnine - obavestenje");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_add);

        mBuilder.setLargeIcon(bm);
        mNotificationManager.notify(1, mBuilder.build());
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.lista_nekretnina);
        try {
            List<Nekretnina> list = getDatabaseHelper().getKontaktDao().queryForAll();

            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Nekretnina p = (Nekretnina) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra(NEKRETNINA_KEY, p.getId());
                    startActivity(intent);
                }
            });

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onResume() {
        super.onResume();

        refresh();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.add_new_contact:

                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.add_dialog_layout);

                Button add = (Button) dialog.findViewById(R.id.dodaj_nekretninu);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText uzetNazivIzDialoga = (EditText) dialog.findViewById(R.id.nekretnina_naziv);
                        EditText uzetOpisIzDialoga = (EditText) dialog.findViewById(R.id.nekretnina_opis);
                        EditText uzetAdresuIzDialog = (EditText) dialog.findViewById(R.id.nekretnina_adresa);
                        EditText uzetBrojTelefonaIzDialoga = (EditText) dialog.findViewById(R.id.nekretnina_broj_telefona);
                        EditText uzetKvadraturaIzDialoga = (EditText) dialog.findViewById(R.id.nekretnina_kvadratura);
                        EditText uzetBrojSobaIzDialoga = (EditText) dialog.findViewById(R.id.nekretnina_broj_soba);
                        EditText uzetCenaIzDialoga = (EditText) dialog.findViewById(R.id.nekretnina_cena);

                        Nekretnina n = new Nekretnina();
                        n.setNaziv(uzetNazivIzDialoga.getText().toString());
                        n.setOpis(uzetOpisIzDialoga.getText().toString());
                        n.setAdresa(uzetAdresuIzDialog.getText().toString());
                        n.setBrojTelefona(uzetBrojTelefonaIzDialoga.getText().toString());
                        n.setKvadratura(uzetKvadraturaIzDialoga.getText().toString());
                        n.setBrojSoba(uzetBrojSobaIzDialoga.getText().toString());
                        n.setCena(uzetCenaIzDialoga.getText().toString());


                        try {

                            getDatabaseHelper().getKontaktDao().create(n);

                            boolean toast = SharedPrefs.getBoolean(NOTIF_TOAST, false);
                            boolean status = SharedPrefs.getBoolean(NOTIF_STATUS, false);

                            if (toast) {
                                Toast.makeText(MainActivity.this, "Dodata nova nekretnina", Toast.LENGTH_SHORT).show();
                            }

                            if (status) {
                                prikaziStatusPoruka("Info - Dodata je nova nekretnina");
                            }

                            refresh();

                        } catch (java.sql.SQLException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();

                    }
                });
                dialog.show();
                break;

            case R.id.about:

                AlertDialog alertDialog = new AppDialog(this).prepareDialog();
                alertDialog.show();
                break;

            case R.id.preferences:
                startActivity(new Intent(MainActivity.this, Preferences.class));
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
