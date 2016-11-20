package ikozyrev.shoppinglist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;



public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final int CM_DELETE_ID = 1;
    private static final int CM_CHANGE_STATUS_ID = 2;
    private static final int CM_EDIT_ROW = 3;
    ListView mLvData;
    public static DB mDb;
    SimpleCursorAdapter mScAdapter;
    Toolbar mToolbar;
    public static boolean mInactiveFlag = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        setSupportActionBar(mToolbar);
        // открываем подключение к БД
        openDbConn();

        // создаем адаптер и настраиваем список
        mScAdapter = new SimpleCursorAdapter(this, R.layout.item, null, new String[]{DB.KEY_ID, DB.KEY_DSC}
                , new int[]{R.id.tvText, R.id.tvDsc}, 0);
        mLvData.setAdapter(mScAdapter);

        // добавляем контекстное меню к списку
        registerForContextMenu(mLvData);

        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);
        updateListView();
    }

    private void closeDbConn() {
        // закрываем подключение при выходе
        mDb.close();
    }

    public void updateListView(){
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    private void openDbConn() {
        mDb = new DB(this);
        mDb.open();
    }


    private void findViewsById() {
        mLvData = (ListView) findViewById(R.id.mainActivityListView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mInactiveFlag) {
            getMenuInflater().inflate(R.menu.menu_main_a, menu);
        }
        else getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_show && !mInactiveFlag) {
            mInactiveFlag = true;
            getSupportLoaderManager().getLoader(0).forceLoad();

        }
        else if (id == R.id.action_show){
            mInactiveFlag = false;
            getSupportLoaderManager().getLoader(0).forceLoad();
        }

        return super.onOptionsItemSelected(item);
    }

    // обработка нажатия кнопки
    public void onButtonClick(View view) {
        // добавляем запись
        // mDb.addRec("lists", String.valueOf(mScAdapter.getCount() + 1), "test");
        // получаем новый курсор с данными

        new MainDialogFragment().show(getSupportFragmentManager(), "create");

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_record);
        menu.add(0, CM_CHANGE_STATUS_ID, 0, R.string.change_status_to_inactive);
        menu.add(0, CM_EDIT_ROW, 0, R.string.edit_record);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case CM_DELETE_ID: {
                // получаем из пункта контекстного меню данные по пункту списка

                // извлекаем id записи и удаляем соответствующую запись в БД
                mDb.delRec("lists", acmi.id);
                // получаем новый курсор с данными
                updateListView();
                return true;
            }
            case CM_CHANGE_STATUS_ID:
                mDb.updateStatus("lists", acmi.id, 0);
                updateListView();
                return true;
            case CM_EDIT_ROW:
                //mDb.updateStatus("lists", acmi.id, 0);
                Bundle b = new Bundle();
                b.putLong("id", acmi.id);
                MainDialogFragment mdf = new MainDialogFragment();
                mdf.setArguments(b);
                mdf.show(getSupportFragmentManager(), "create");
                updateListView();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        closeDbConn();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, mDb);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mScAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


}