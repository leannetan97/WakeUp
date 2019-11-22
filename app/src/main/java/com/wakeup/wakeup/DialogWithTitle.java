package com.wakeup.wakeup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class DialogWithTitle extends AppCompatDialogFragment  {
    private EditText etAlarmOrProfileName;
    private DialogListener listener;
    private String title, hint, validBtnName, invalidBtnName;

    public interface DialogListener {
        void applyTexts(String newName);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("DialogTitle");
            hint = getArguments().getString("Hint");
            validBtnName = getArguments().getString("ValidButton");
            invalidBtnName = getArguments().getString("InvalidButton");
        } else {
            title = "Alarm Name";
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
                        String name = etAlarmOrProfileName.getText().toString().trim();
                        listener.applyTexts(name);
                    }
                });

        etAlarmOrProfileName = view.findViewById(R.id.et_profileOrAlarmName);
        etAlarmOrProfileName.setHint(hint);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(getTargetRequestCode() == 1 || getTargetRequestCode() == 2){
                listener = (DialogListener) getTargetFragment();
            }else {
                listener = (DialogListener) context;
            }
        } catch (Exception e) {
            throw new ClassCastException(e + "must implement DialogListener.");
        }
    }

}
