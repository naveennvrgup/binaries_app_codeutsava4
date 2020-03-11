package binaries.app.codeutsava.restapi.utils;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import binaries.app.codeutsava.R;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private AlertDialog alertDialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE"))
            if (isNetworkAvailable(context))
                online(true, context);
            else
                online(false, context);
    }

    void online(boolean isOnline, Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        if (!isOnline) {
            if (context != null) {
                View view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_network_error_layout, null);
                view.findViewById(R.id.alert_dialog_layout_button).setOnClickListener(v -> alertDialog.dismiss());

                dialog.setView(view);
                dialog.setCancelable(false);

                alertDialog = dialog.create();

                if (alertDialog.getWindow() != null)
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                alertDialog.show();
            }

        } else {
            if (alertDialog != null)
                alertDialog.dismiss();
        }
    }

    private static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();

                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }

            } else {
                final Network n = cm.getActiveNetwork();

                if (n != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);

                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }

        return false;
    }
}
