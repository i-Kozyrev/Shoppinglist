package ikozyrev.shoppinglist.DB;

/**
 * Created in Android Studia
 * User: ikozyrev
 * Date: 27.11.2016.
 */

import android.provider.BaseColumns;

public final class Tables {
    public Tables() {
    }

    public static abstract class MainList implements BaseColumns {

        public static final String TABLE_NAME = "shopping_lists";
        public static final String KEY_ID = "_id";
        public static final String KEY_DATE = "date";
        public static final String KEY_STATUS = "status";
        public static final String KEY_NAME = "name";
        public static final String KEY_DSC = "dsc";
        public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + KEY_ID
                + " integer primary key autoincrement," + KEY_DATE + " integer," + KEY_STATUS + " integer, " + KEY_NAME + " text," +
                KEY_DSC + " text" + ")";

    }

    public static abstract class ItemList implements BaseColumns {
        public static final String TABLE_NAME = "item_lists";
        public static final String KEY_ID = "_id";
        public static final String KEY_DATE = "date";
        public static final String KEY_STATUS = "status";
        public static final String KEY_NAME = "name";
        public static final String KEY_DSC = "dsc";
        public static final String KEY_COUNT = "count";
        public static final String KEY_TYPE = "type";
        public static final String KEY_SHOPPING_LIST = "shopping_list";
        public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + KEY_ID
                + " integer primary key autoincrement, " + KEY_DATE + " integer, " + KEY_STATUS + " integer, " + KEY_NAME + " text, " +
                KEY_DSC + " text, " + KEY_COUNT + " integer, " + KEY_TYPE + " integer, " + KEY_SHOPPING_LIST + " integer " + ")";
    }

    public static abstract class DimItemType implements BaseColumns {
        public static final String TABLE_NAME = "dim_item_type";
        public static final String KEY_ID = "_id";
        public static final String KEY_NAME = "name";

        public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAME + " text UNIQUE " + ")";
    }

}
