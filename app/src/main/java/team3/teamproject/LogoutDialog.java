package team3.teamproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import com.facebook.login.LoginManager;


/**
 * Created by Petr Makarov on 21.04.2018.
 */



public class LogoutDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.alert_lo_message)
                .setTitle(R.string.alert_lo_title)
                .setPositiveButton(R.string.alert_lo_positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginManager.getInstance().logOut();
                        Intent logOutScreen = new Intent(getActivity(), LoginActivity.class);
                        startActivity(logOutScreen);

                    }
                })
                .setNegativeButton(R.string.alert_lo_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LogoutDialog.super.onCancel(dialog);
                    }
                });

        return builder.create();
    }


}