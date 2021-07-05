package cu.daxyel.amiscore.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import cu.daxyel.amiscore.db.DatabaseContract.*;

public class DbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DiagnosisTable.TABLE_NAME + " (" +
                    DiagnosisTable._ID + " INTEGER PRIMARY KEY NOT NULL," +
                    DiagnosisTable.COLUMN_FULL_NAME + " TEXT NOT NULL," +
                    DiagnosisTable.COLUMN_CI + " TEXT NOT NULL," +
                    DiagnosisTable.COLUMN_DISEASES + " TEXT NOT NULL," +
                    DiagnosisTable.COLUMN_CONSULT_DATE + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DiagnosisTable.TABLE_NAME;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "amiscoredata.db";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

    }
}
