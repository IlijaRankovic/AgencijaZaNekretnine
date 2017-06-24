package com.example.androiddevelopment.agencijazanekretnine.Dialogs;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.androiddevelopment.agencijazanekretnine.R;

/**
 * Created by androiddevelopment on 24.6.17..
 */

public class AppDialog extends AlertDialog.Builder {

    public AppDialog(Context context) {
        super(context);

        setTitle(R.string.dialog_app_naziv);
        setMessage(R.string.dialog_app_tekst);
        setCancelable(false);

        setPositiveButton(R.string.dialog_app_Uredu, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        setNegativeButton(R.string.dialog_app_Ponisti, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
    }


    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

}

