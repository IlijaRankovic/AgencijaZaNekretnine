package com.example.androiddevelopment.agencijazanekretnine.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by androiddevelopment on 24.6.17..
 */

public class ORMLightHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "DataBase.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Slika, Integer> mSlikaDao = null;
    private Dao<Nekretnina, Integer> mNekretninaDao = null;

    public ORMLightHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Slika.class);
            TableUtils.createTable(connectionSource, Nekretnina.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Slika.class, true);
            TableUtils.dropTable(connectionSource, Nekretnina.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Slika, Integer> getTelefonDao() throws SQLException {
        if (mSlikaDao == null) {
            mSlikaDao = getDao(Slika.class);
        }

        return mSlikaDao;
    }

    public Dao<Nekretnina, Integer> getKontaktDao() throws SQLException {
        if (mNekretninaDao == null) {
            mNekretninaDao = getDao(Nekretnina.class);
        }

        return mNekretninaDao;
    }

    @Override
    public void close() {
        mSlikaDao = null;
        mNekretninaDao = null;

        super.close();
    }
}
