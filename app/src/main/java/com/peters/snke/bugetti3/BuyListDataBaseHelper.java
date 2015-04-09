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
 * Created by Sönke on 09.04.2015.
 */
public class BuyListDataBaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "buylist.db";
    private static int DATABASE_VERSION = 1;

    private Dao<BuyListItems, Integer> BuyListItemsDao = null;

    public BuyListDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Dao<BuyListItems, Integer> getBuyListItemsDao() throws SQLException {
        try {
            BuyListItemsDao = getDao(BuyListItems.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BuyListItemsDao;
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
    public void deleteAll() throws SQLException {
        TableUtils.dropTable(connectionSource, Charges.class, true);
        TableUtils.createTable(connectionSource, Charges.class);

    }


    @Override
    public void close() {
        super.close();
        BuyListItemsDao = null;
    }

}
