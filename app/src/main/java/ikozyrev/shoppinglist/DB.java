package ikozyrev.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created in Android Studia
 * User: ikozyrev
 * Date: 11.07.2016.
 */
public class DB {


    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "ShoppingList";
    public static final String TABLE_LISTS = "lists";

    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_STATUS = "status";
    public static final String KEY_NAME = "name";
    public static final String KEY_DSC = "dsc";

    private static final String DB_CREATE = "create table " + TABLE_LISTS + "(" + KEY_ID
    + " integer primary key autoincrement," + KEY_DATE + " integer," + KEY_STATUS + " integer, " + KEY_NAME + " text," +
            KEY_DSC + " text" + ")";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }
    public void open() {
        mDBHelper = new DBHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    public Cursor getData(String tableName, int[] statusFlag) {
        String selectableStatus = Arrays.toString(statusFlag).replace("[","").replace("]","");

        return  mDB.rawQuery("SELECT * FROM " + tableName +  " WHERE "+ KEY_STATUS + " IN ("+selectableStatus+")",
                null);

    }

    public Cursor getRawQResult(String query, String[] args){
        return mDB.rawQuery(query,args);
    }



    public void addRec(String tableName, String txt, String dsc) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, txt);
        cv.put(KEY_DSC, dsc);
        cv.put(KEY_STATUS, 1);
        cv.put(KEY_DATE, Calendar.getInstance().getTimeInMillis());
        mDB.insert(tableName, null, cv);
    }

    public void delRec(String tableName,long id) {
        mDB.delete(tableName, KEY_ID + " = " + id, null);
    }
    public void execQuery(String query, String[] args){
        mDB.execSQL(query,args);
    }
    public void updateStatus(String tableName, long id, int status){

        mDB.execSQL("UPDATE " + tableName + " SET "+KEY_STATUS+" = " + status + " WHERE " + KEY_ID + " = " + id);
    }
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            for (int i = 1; i < 5; i++) {
                cv.put(KEY_NAME, "sometext " + i);
                cv.put(KEY_DSC, "sometext " + i);
                sqLiteDatabase.insert(TABLE_LISTS, null, cv);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists " + TABLE_LISTS);
            //lol
            sqLiteDatabase.execSQL(DB_CREATE);
        }
    }


}
