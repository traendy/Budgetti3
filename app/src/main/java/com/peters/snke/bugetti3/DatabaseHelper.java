package com.peters.snke.bugetti3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Sönke on 18.03.2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "charges4.db";
    private static int DATABASE_VERSION = 1;

    private Dao<Charges, Integer> chargeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public Dao<Charges, Integer> getChargeDao() throws SQLException {
        try {
            chargeDao = getDao(Charges.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chargeDao;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {


        try {
            TableUtils.createTable(connectionSource, Charges.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int i, int i2) {
        Log.i(DatabaseHelper.class.getName(), "onUpgrade");

        try {

            TableUtils.dropTable(connectionSource, Charges.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        onCreate(database, connectionSource);
    }


    // method to delete all rows
    public void deleteAll() throws SQLException {
        TableUtils.dropTable(connectionSource, Charges.class, true);
        TableUtils.createTable(connectionSource, Charges.class);

    }


    @Override
    public void close() {
        super.close();
        chargeDao = null;
    }


}
