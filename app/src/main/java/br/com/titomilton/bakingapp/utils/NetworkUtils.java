package br.com.titomilton.bakingapp.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;
import android.widget.Toast;

import br.com.titomilton.bakingapp.R;

public final class NetworkUtils {

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(context, context.getString(R.string.network_is_offline), Toast.LENGTH_SHORT).show();
        }
        return isConnected;

    }

    public static boolean isValidUrl(Context context, String strUri) {
        boolean isValid = Patterns.WEB_URL.matcher(strUri).matches();
        if (!isValid) {
            Toast.makeText(context, context.getString(R.string.url_is_invalid) + " " + strUri, Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

}
