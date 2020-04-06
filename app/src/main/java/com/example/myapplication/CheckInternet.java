package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

/**
 * Designed and Developed by Mohammad suhail ahmed on 27/02/2020
 */
public class CheckInternet {
    public boolean checkInternet(final Context context) {
        boolean res;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.i("internet", "connected to internet");
            res = true;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("No Internet Connection please enable wifi or mobile data.....");
            builder.setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (dialog != null) {
                        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                }
            }).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (dialogInterface != null) {
                        dialogInterface.dismiss();
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            res = false;
        }
        return res;
    }
}
