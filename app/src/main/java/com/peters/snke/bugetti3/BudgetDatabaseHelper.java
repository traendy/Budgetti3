package com.peters.snke.bugetti3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Sönke on 31.03.2015.
 */
public class BudgetDatabaseHelper  extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME = "budget2.db";
    private static int DATABASE_VERSION = 1;

    private Dao<Budget, Integer> budgetdao=null;


    public BudgetDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Dao<Budget, Integer> getBudgetdao() throws SQLException{
        budgetdao = getDao(Budget.class);
        return budgetdao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)  {
        try {
            TableUtils.createTable(connectionSource, Budget.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, Budget.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(database,connectionSource);
    }

    public void deleteAll() throws SQLException{
        TableUtils.dropTable(connectionSource, Budget.class, true);
        TableUtils.createTable(connectionSource, Budget.class);
    }

    @Override
    public void close(){
        super.close();
        budgetdao=null;
    }
}
