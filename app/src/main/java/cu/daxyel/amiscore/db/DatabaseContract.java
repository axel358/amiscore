package cu.daxyel.amiscore.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract() {
    }
    public static class DiagnosisTable implements BaseColumns {
        public static final String TABLE_NAME = "table_diagnosis";
        public static final String COLUMN_FULL_NAME = "full_name";
        public static final String COLUMN_CI = "ci";
        public static final String COLUMN_DISEASES = "diseases";
        public static final String COLUMN_PROBABILITY_INFO = "probability_info";
        public static final String COLUMN_CONSULT_DATE = "consult_date";
    }
}
