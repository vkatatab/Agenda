package br.edu.ifspsaocarlos.agenda.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agenda.db";
    static final String DATABASE_TABLE = "contatos";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "nome";
    static final String KEY_FONE = "fone";
    static final String KEY_FONE_SECONDARY = "fone_secondary";
    static final String KEY_BIRTHDATE = "birthdate";
    static final String KEY_EMAIL = "email";
    static final String KEY_FAVORITE = "favorite";
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_CREATE = "CREATE TABLE "+ DATABASE_TABLE +" (" +
            KEY_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_FONE + " TEXT, "  +
            KEY_EMAIL + " TEXT);";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            this.upgradeToVersion2(database);
        }
        if (oldVersion < 3) {
            this.upgradeToVersion3(database);
        }
        if (oldVersion < 4) {
            this.upgradeToVersion4(database);
        }
    }

    private void upgradeToVersion2(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE "+ DATABASE_TABLE +" ADD COLUMN "+ KEY_FAVORITE +" INTEGER NOT NULL DEFAULT 0");
    }

    private void upgradeToVersion3(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE "+ DATABASE_TABLE +" ADD COLUMN "+ KEY_FONE_SECONDARY +" TEXT");
    }

    private void upgradeToVersion4(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE "+ DATABASE_TABLE +" ADD COLUMN "+ KEY_BIRTHDATE +" TEXT");
    }
}

