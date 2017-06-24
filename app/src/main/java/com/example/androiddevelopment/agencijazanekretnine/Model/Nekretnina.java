package com.example.androiddevelopment.agencijazanekretnine.Model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by androiddevelopment on 24.6.17..
 */
@DatabaseTable(tableName = Nekretnina.TABLE_NAME_NAME)
public class Nekretnina {

    public static final String FIELD_NAME_ID = "id";
    public static final String TABLE_NAME_NAME = "naziv";
    public static final String TABLE_NAME_DESC = "opis";
    public static final String TABLE_NAME_ADDRESS = "adresa";
    public static final String TABLE_NAME_NUMBER_PHONE = "broj telefona";
    public static final String TABLE_NAME_SQUARE = "kvadratura";
    public static final String TABLE_NAME_NUMBER_ROOMS = "broj soba";
    public static final String TABLE_NAME_PRICE = "cena";
    public static final String TABLE_NAME_PHOTOS = "slike";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = TABLE_NAME_NAME)
    private String naziv;
    @DatabaseField(columnName = TABLE_NAME_DESC)
    private String opis;
    @DatabaseField(columnName = TABLE_NAME_ADDRESS)
    private String adresa;
    @DatabaseField(columnName = TABLE_NAME_NUMBER_PHONE)
    private String brojTelefona;
    @DatabaseField(columnName = TABLE_NAME_SQUARE)
    private String kvadratura;
    @DatabaseField(columnName = TABLE_NAME_NUMBER_ROOMS)
    private String brojSoba;
    @DatabaseField(columnName = TABLE_NAME_PRICE)
    private String cena;
    @ForeignCollectionField(columnName = Nekretnina.TABLE_NAME_PHOTOS, eager = true)
    private ForeignCollection<Slika> slike;

    public Nekretnina (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(String kvadratura) {
        this.kvadratura = kvadratura;
    }

    public String getBrojSoba() {
        return brojSoba;
    }

    public void setBrojSoba(String brojSoba) {
        this.brojSoba = brojSoba;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    @Override
    public String toString() { return  "naziv" + naziv; }
}
