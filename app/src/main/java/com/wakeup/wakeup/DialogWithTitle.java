package com.wakeup.wakeup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogWithTitle extends AppCompatDialogFragment {
    private EditText etAlarmOrProfileName;
    private dialogListener listener;
    private String title;
    private String validBtnName;
    private String invalidBtnName;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("DialogTitle");
            validBtnName = getArguments().getString("ValidButton");
            invalidBtnName = getArguments().getString("InvalidButton");
        } else {
            title = "New Alarm Name";
            validBtnName = "OK";
            invalidBtnName = "CANCEL";
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.res_layout_dialog_with_title, null);
        builder.setView(view)
                .setTitle(title)
                .setNegativeButton(invalidBtnName,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .setPositiveButton(validBtnName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (title.equalsIgnoreCase("AlarmName")) {
                            //TODO: Update alarm name in database

                        } else {
                            //TODO: Update profile name in database
                            String profileName = etAlarmOrProfileName.getText().toString();
                            listener.applyTexts(profileName);
                        }
                        String profileName = etAlarmOrProfileName.getText().toString();
                        listener.applyTexts(profileName);
                    }
                });

        etAlarmOrProfileName = view.findViewById(R.id.et_profileOrAlarmName);
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
