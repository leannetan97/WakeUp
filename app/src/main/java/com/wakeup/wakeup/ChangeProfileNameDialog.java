package com.wakeup.wakeup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ChangeProfileNameDialog extends AppCompatDialogFragment {
    private EditText etProfileName;
    private dialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_dialog_change_profile_name, null);
        builder.setView(view)
                .setTitle("Change Profile Name")
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String profileName = etProfileName.getText().toString();
                        listener.applyTexts(profileName);
                    }
                });

        etProfileName = view.findViewById(R.id.et_profileName);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (dialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener.");
        }
    }

    interface dialogListener {
        void applyTexts(String profileName);
    }
}
