package ikozyrev.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import ikozyrev.shoppinglist.models.ShoppingList;

/**
 * Created in Android Studia
 * User: ikozyrev
 * Date: 20.11.2016.
 */
public class MainDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {
    View form;
    Long mId;
    EditText mNameBox;
    EditText mDscBox;
    Bundle mBundle;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_main_act, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mBundle = this.getArguments();
        if (this.getTag().equals("edit")) {
            mId = mBundle.getLong("id");
            mNameBox = (EditText) form.findViewById(R.id.dialog_input_name_ET);
            mDscBox = (EditText) form.findViewById(R.id.dialog_input_DSC_ET);
            Cursor res = MainActivity.mDb.getShoppingListNameDsc(mId.intValue());
            if (res.moveToFirst()) {
                mNameBox.setText(res.getString(res.getColumnIndex("name")));
                mDscBox.setText(res.getString(res.getColumnIndex("dsc")));
            }
        }
        return (builder.setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        mNameBox = (EditText) form.findViewById(R.id.dialog_input_name_ET);
        mDscBox = (EditText) form.findViewById(R.id.dialog_input_DSC_ET);
        String name = mNameBox.getText().toString();
        String dsc = mDscBox.getText().toString();
        ShoppingList shoppingList = new ShoppingList(name,dsc);
        if (this.getTag().equals("create")) {
            MainActivity.mDb.addShoppingList(shoppingList);
        }
        else{
            shoppingList.setId(mId.intValue());
            MainActivity.mDb.updateShoppingListsRec(shoppingList);
        }
        //MainActivity.updateListView();
        ((MainActivity) getActivity()).updateListView();

    }


    @Override
    public void onDismiss(DialogInterface unused) {
        super.onDismiss(unused);
    }

    @Override
    public void onCancel(DialogInterface unused) {
        super.onCancel(unused);
    }
}