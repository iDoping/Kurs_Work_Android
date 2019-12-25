package com.example.kursrpmos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragmentPlans extends DialogFragment {

    private DialogListenerPlans listener;

    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     * @return Возвращает диалоговое окно
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Удаление лимита")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Лимит выбранной категории будет удален и установлен в значение 0, продолжить?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    /**
                     * Нажатие на кнопку "ОК"
                     * @param dialog Интерфейс, позволяющий создателю диалога запускать некоторый код при нажатии на элемент в диалоговом окне
                     * @param which Аргумент, содержащий позицию индекса
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYesClickedPlans();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

                    /**
                     * Нажатие на кнопку "Отмена"
                     * @param dialog Интерфейс, позволяющий создателю диалога запускать некоторый код при нажатии на элемент в диалоговом окне
                     * @param which Аргумент, содержащий позицию индекса
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }

    /**
     * Запуск метода onYesClickedPlans
     */
    public interface DialogListenerPlans {
        void onYesClickedPlans();
    }

    /**
     * Связка диалогового окна с Activity
     *
     * @param context контекст Activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {

            listener = (DialogListenerPlans) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement DialogListenerPlans");
        }
    }
}