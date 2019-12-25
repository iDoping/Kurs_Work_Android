package com.example.kursrpmos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {
    private DialogListenerCosts listener;

    /**
     * Задаёт начальную установку параметров при инициализации активности
     *
     * @param savedInstanceState Сохраненное состояние
     * @return Возвращает диалоговое окно
     */
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Удаление категории")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("При удалении категории удалятся все данные,связанные с ней. Продолжить?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    /**
                     * Нажатие на кнопку "ОК"
                     * @param dialog Интерфейс, позволяющий создателю диалога запускать некоторый код при нажатии на элемент в диалоговом окне
                     * @param which Аргумент, содержащий позицию индекса
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYesClicked();
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
     * Запуск метода onYesClicked
     */
    public interface DialogListenerCosts {
        void onYesClicked();
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
            listener = (DialogListenerCosts) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must implement DialogListenerCosts");
        }
    }
}