package ikozyrev.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

/**
 * Created in Android Studia
 * User: ikozyrev
 * Date: 20.11.2016.
 */
public class MainDialogFragment extends DialogFragment implements
        DialogInterface.OnClickListener {
    private View form = null;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        form = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_main_act, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return (builder.setTitle("Создание списка").setView(form)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, null).create());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        EditText nameBox = (EditText) form.findViewById(R.id.dialog_input_name_ET);
        EditText dscBox = (EditText) form.findViewById(R.id.dialog_input_DSC_ET);
        String name = nameBox.getText().toString();
        String dsc = dscBox.getText().toString();
        MainActivity.mDb.addRec("lists", name, dsc);
        //MainActivity.updateListView();
        ((MainActivity)getActivity()).updateListView();
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