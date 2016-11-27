package ikozyrev.shoppinglist.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.Calendar;

import ikozyrev.shoppinglist.models.ShoppingList;
import ikozyrev.shoppinglist.models.ShoppingListItem;

/**
 * Created in Android Studia
 * User: ikozyrev
 * Date: 11.07.2016.
 */
public class DB {


    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "ShoppingList.db";


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
        if (mDBHelper != null) mDBHelper.close();
    }

    public Cursor getShoppingListDataWithStatus(int[] statusFlag) {
        String selectableStatus = Arrays.toString(statusFlag).replace("[", "").replace("]", "");

        return mDB.rawQuery("SELECT * FROM " + Tables.MainList.TABLE_NAME + " WHERE " + Tables.MainList.KEY_STATUS + " IN (" + selectableStatus + ")",
                null);

    }

    public Cursor getRawQResult(String query, String[] args) {
        return mDB.rawQuery(query, args);
    }

    public Cursor getShoppingListNameDsc(int id) {
        String query = "SELECT NAME, DSC FROM " + Tables.MainList.TABLE_NAME + " WHERE _id=?";
        return mDB.rawQuery(query, new String[]{String.valueOf(id)});

    }

    public void addShoppingList(ShoppingList shoppingList) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.MainList.KEY_NAME, shoppingList.getName());
        cv.put(Tables.MainList.KEY_DSC, shoppingList.getDsc());
        cv.put(Tables.MainList.KEY_STATUS, 0);
        cv.put(Tables.MainList.KEY_DATE, Calendar.getInstance().getTimeInMillis());
        mDB.insert(Tables.MainList.TABLE_NAME, null, cv);
    }

    public void delShoppingList(long id) {
        String whereClause = Tables.MainList.KEY_ID + " = ?";
        mDB.delete(Tables.MainList.TABLE_NAME, whereClause, new String[]{String.valueOf(id)});
    }

    public void execQuery(String query, String[] args) {
        mDB.execSQL(query, args);
    }

    public void updateShoppingListStatus(long id, int status) {
        ContentValues cv = new ContentValues();
        cv.put(Tables.MainList.KEY_STATUS, status);
        String whereClause = Tables.MainList.KEY_ID + " = ?";
        mDB.update(Tables.MainList.TABLE_NAME, cv, whereClause, new String[]{String.valueOf(id)});

    }

    public void updateShoppingListsRec(ShoppingList shoppingList) {
        ContentValues cv = new ContentValues();
        String whereClause = Tables.MainList.KEY_ID + " = ?";
        cv.put(Tables.MainList.KEY_NAME, shoppingList.getName());
        cv.put(Tables.MainList.KEY_DSC, shoppingList.getDsc());
        mDB.update(Tables.MainList.TABLE_NAME, cv, whereClause, new String[]{String.valueOf(shoppingList.getId())});
    }

    public boolean isDoneStatusShoppingListsRow(long id){
        String selection = Tables.MainList.KEY_ID + " = ?";
        Cursor cursor = mDB.query(Tables.MainList.TABLE_NAME,new String[]{Tables.MainList.KEY_STATUS},selection,new String[]{String.valueOf(id)},null,null,null);
        boolean status = false;
        if (cursor.moveToFirst()) {
            if(cursor.getInt(cursor.getColumnIndex(Tables.MainList.KEY_STATUS)) == 0){
                status = false;
            }
            else if (cursor.getInt(cursor.getColumnIndex(Tables.MainList.KEY_STATUS)) == 1)
                status = true;
        }
        cursor.close();
        return status;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            createTables(sqLiteDatabase);

        }

        public void createTables(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(Tables.MainList.CREATE_TABLE);
            sqLiteDatabase.execSQL(Tables.ItemList.CREATE_TABLE);
            sqLiteDatabase.execSQL(Tables.DimItemType.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists " + Tables.MainList.TABLE_NAME);
            createTables(sqLiteDatabase);
        }
    }


}
