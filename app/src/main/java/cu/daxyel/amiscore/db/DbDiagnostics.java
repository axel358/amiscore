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

public class DbDiagnostics extends DbHelper
{
    Context context;
    public DbDiagnostics(@Nullable Context context)
    {
        super(context);
        this.context = context;
    }
    public long addDiagnostic(String full_name, String ci, String diseases, String consult_date)
    {
        long id=0;
        try
        {
            DbHelper dbHelper=new DbHelper(context);
            SQLiteDatabase db= dbHelper.getWritableDatabase();

            ContentValues values=new ContentValues();
            values.put(DiagnosisTable.COLUMN_FULL_NAME, full_name);
            values.put(DiagnosisTable.COLUMN_CI, ci);
            values.put(DiagnosisTable.COLUMN_DISEASES, diseases);
            values.put(DiagnosisTable.COLUMN_CONSULT_DATE, consult_date);

            id = db.insert(DiagnosisTable.TABLE_NAME, null, values);
        }
        catch (Exception e)
        {
            e.toString();
        }
        return id;
    }
    public ArrayList<Diagnosis> listAllDiagnostics()
    {
        DbHelper dbHelper=new DbHelper(context);
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ArrayList<Diagnosis> diagnosisList =new ArrayList<>();
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
        while (cursor.moveToNext())
        {
            diagnosisList.add(new Diagnosis(cursor.getInt(cursor.getColumnIndex(DiagnosisTable._ID)),
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_FULL_NAME)), 
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CI)), 
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_DISEASES)), 
                                            cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CONSULT_DATE))));
        }
        cursor.close();
        return diagnosisList;
    }
    public ArrayList<Diagnosis> listDiagnosticsByDiseases(String disease)
    {
        DbHelper dbHelper=new DbHelper(context);
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ArrayList<Diagnosis> diagnosisListByDisease =new ArrayList<>();

        String selection = DiagnosisTable.COLUMN_DISEASES + " = ?";
        String[] selectionArgs = { disease };

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
        while (cursor.moveToNext())
        {
            diagnosisListByDisease.add(new Diagnosis(cursor.getInt(cursor.getColumnIndex(DiagnosisTable._ID)),
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_FULL_NAME)), 
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CI)), 
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_DISEASES)), 
                                                     cursor.getString(cursor.getColumnIndex(DiagnosisTable.COLUMN_CONSULT_DATE))));

        }
        cursor.close();
        return diagnosisListByDisease;
    }

    public int deleteDiagnostic(int id)
    {
        int deletedRows=0;
        DbHelper dbHelper=new DbHelper(context);
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = {Integer.toString(id)};
        try
        {
            deletedRows = db.delete(DiagnosisTable.TABLE_NAME, selection, selectionArgs);
        }
        catch (Exception e)
        {
            e.toString();
        }
        return deletedRows;
    }
    public int editDiagnostic(int id, String full_name, String ci, String diseases, String consult_date)
    {
        int count=0;
        DbHelper dbHelper=new DbHelper(context);
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DiagnosisTable.COLUMN_FULL_NAME, full_name);
        values.put(DiagnosisTable.COLUMN_CI, ci);
        values.put(DiagnosisTable.COLUMN_DISEASES, diseases);
        values.put(DiagnosisTable.COLUMN_CONSULT_DATE, consult_date);

        String selection = BaseColumns._ID + " LIKE ?";
        String[] selectionArgs = {Integer.toString(id)};
        try
        {
            count = db.update(
                DiagnosisTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        }
        catch (Exception e)
        {
            e.toString();
        }
        return count;
    }
}
