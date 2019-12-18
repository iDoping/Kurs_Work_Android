package com.example.kursrpmos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragmentIncomes extends DialogFragment {
    private DialogListenerIncomes listener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Удаление категории")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("При удалении категории удалятся все данные,связанные с ней. Продолжить?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.OnYesClickedIncomes();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }

    public interface DialogListenerIncomes {
        void OnYesClickedIncomes();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListenerIncomes) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "lol");
        }
    }
}