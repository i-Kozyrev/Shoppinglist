package ikozyrev.shoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

/**
 * Created in Android Studia
 * User: ikozyrev
 * Date: 19.11.2016.
 */
public class MyCursorLoader extends CursorLoader {

    DB db;

    public MyCursorLoader(Context context, DB db) {
        super(context);
        this.db = db;
    }

    @Override
    public Cursor loadInBackground() {
        if(MainActivity.mInactiveFlag) {
            return db.getData("lists",new int []{1,0});
        }
        else return db.getData("lists", new int[]{1});
    }

}
