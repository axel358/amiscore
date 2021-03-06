package cu.daxyel.amiscore.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import cu.daxyel.amiscore.models.Diagnosis;
import cu.daxyel.amiscore.db.DatabaseContract.*;
import cu.daxyel.amiscore.models.Item;
import java.util.HashSet;

public class DbDiagnostics extends DbHelper {
    Context context;

    public DbDiagnostics(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long addDiagnostic(String full_name, String ci, String diseases, String probabilityInfo, String consult_date, String observations) {
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DiagnosisTable.COLUMN_FULL_NAME, full_name);
            values.put(DiagnosisTable.COLUMN_CI, ci);
            values.put(DiagnosisTable.COLUMN_DISEASES, diseases);
            values.put(DiagnosisTable.COLUMN_PROBABILITY_INFO, probabilityInfo);
            values.put(DiagnosisTable.COLUMN_CONSULT_DATE, consult_date);
            values.put(DiagnosisTable.COLUMN_OBSERVATIONS, observations);

            id = db.insert(DiagnosisTable.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }

    public ArrayList<Diagnosis> listAllDiagnostics() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Diagnosis> diagnosisList = new ArrayList<>();
        String sortOrder = BaseColumns._ID + " DESC";
        Cursor cursor = db.query(
            DiagnosisTable.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            sortOrder
        );
        while (cursor.moveToNext()) {
            diagnosisList.add(new Diagnosis(cursor.getInt(cursor.getColumnIndex(DiagnosisTable._ID)),
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_FULL_NAME)),
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CI)),
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_DISEASES)),
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_PROBABILITY_INFO)),
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CONSULT_DATE)),
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_OBSERVATIONS))));
        }
        cursor.close();
        return diagnosisList;
    }

    public ArrayList<Diagnosis> listDiagnosticsByDiseases(String disease) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Diagnosis> diagnosisListByDisease = new ArrayList<>();

        String selection = DiagnosisTable.COLUMN_DISEASES + " = ?";
        String[] selectionArgs = {disease};

        String sortOrder = BaseColumns._ID + " DESC";
        Cursor cursor = db.query(
            DiagnosisTable.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        );
        while (cursor.moveToNext()) {
            diagnosisListByDisease.add(new Diagnosis(cursor.getInt(cursor.getColumnIndex(DiagnosisTable._ID)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_FULL_NAME)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CI)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_DISEASES)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_PROBABILITY_INFO)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CONSULT_DATE)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_OBSERVATIONS))));

        }
        cursor.close();
        return diagnosisListByDisease;
    }
    
    public int getDiagnosisCountByIndex(String index){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        String selection = DiagnosisTable.COLUMN_DISEASES + " = ?";
        String[] selectionArgs = {index};

        String sortOrder = BaseColumns._ID + " DESC";
        Cursor cursor = db.query(
            DiagnosisTable.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        );
        int count = cursor.getCount();
        cursor.close();
        
        return count;
    }

    public ArrayList<Diagnosis> listDiagnosticsByPatient(String name) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Diagnosis> diagnosisListByDisease = new ArrayList<>();

        String selection = DiagnosisTable.COLUMN_FULL_NAME + " = ?";
        String[] selectionArgs = {name};

        String sortOrder = BaseColumns._ID + " DESC";
        Cursor cursor = db.query(
            DiagnosisTable.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        );
        while (cursor.moveToNext()) {
            diagnosisListByDisease.add(new Diagnosis(cursor.getInt(cursor.getColumnIndex(DiagnosisTable._ID)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_FULL_NAME)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CI)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_DISEASES)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_PROBABILITY_INFO)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CONSULT_DATE)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_OBSERVATIONS))));

        }
        cursor.close();
        return diagnosisListByDisease;
    }

    public Object[] getPatients() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        HashSet<String> items = new HashSet<String>();
        String sortOrder = BaseColumns._ID + " DESC";
        Cursor cursor = db.query(
            DiagnosisTable.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            sortOrder
        );

        while (cursor.moveToNext()) {
            items.add(cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_FULL_NAME)));
        }
        cursor.close();

        return items.toArray();
    }
    
    public Object[] getDiseases() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        HashSet<String> items = new HashSet<String>();
        String sortOrder = BaseColumns._ID + " DESC";
        Cursor cursor = db.query(
            DiagnosisTable.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            sortOrder
        );

        while (cursor.moveToNext()) {
            items.add(cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_DISEASES)));
        }
        cursor.close();

        return items.toArray();
    }


    public int deleteDiagnostic(int id) {
        int deletedRows = 0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = {Integer.toString(id)};
        try {
            deletedRows = db.delete(DiagnosisTable.TABLE_NAME, selection, selectionArgs);
        } catch (Exception e) {
            e.toString();
        }
        return deletedRows;
    }

    public int editDiagnostic(int id, String full_name, String ci, String diseases, String probabilityInfo, String consult_date, String observations) {
        int count = 0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DiagnosisTable.COLUMN_FULL_NAME, full_name);
        values.put(DiagnosisTable.COLUMN_CI, ci);
        values.put(DiagnosisTable.COLUMN_DISEASES, diseases);
        values.put(DiagnosisTable.COLUMN_PROBABILITY_INFO, probabilityInfo);
        values.put(DiagnosisTable.COLUMN_CONSULT_DATE, consult_date);
        values.put(DiagnosisTable.COLUMN_OBSERVATIONS, observations);

        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = {Integer.toString(id)};
        try {
            count = db.update(
                DiagnosisTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        } catch (Exception e) {
            e.toString();
        }
        return count;
    }
}
