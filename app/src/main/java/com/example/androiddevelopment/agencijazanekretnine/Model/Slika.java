package com.example.androiddevelopment.agencijazanekretnine.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by androiddevelopment on 24.6.17..
 */
@DatabaseTable(tableName = Slika.TABLE_NAME_PHOTO)
public class Slika {

    public static final String FIELD_NAME_ID = "id";
    public static final String TABLE_NAME_PHOTO = "slika";
    public static final String FIELD_NAME_REAL_ESTATE = "nekretnina";

    @DatabaseField(columnName = FIELD_NAME_ID)
    private int sId;
    @DatabaseField(columnName = TABLE_NAME_PHOTO)
    private String slika;
    @DatabaseField (columnName = FIELD_NAME_REAL_ESTATE, foreign = true, foreignAutoRefresh = true)
    private Nekretnina nekretnina;

    public Slika(){

    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public Nekretnina getNekretnina() {
        return nekretnina;
    }

    public void setNekretnina(Nekretnina nekretnina) {
        this.nekretnina = nekretnina;
    }

    @Override
    public String toString() { return  "Slika" + slika; }
}
