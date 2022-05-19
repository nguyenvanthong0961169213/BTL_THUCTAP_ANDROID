package com.example.bt_android_thuctap.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class internet extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
        {
            if(isNetworkAvailable(context))
            {
                Toast.makeText(context,"INTERNET CONNECTED",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context,"INTERNET DISCONNECTED!!!",Toast.LENGTH_LONG).show();
            }

        }
    }

    private boolean isNetworkAvailable(Context context) {
      ConnectivityManager connectivityManager=(ConnectivityManager)
              context.getSystemService(context.CONNECTIVITY_SERVICE);
      if(connectivityManager==null)
      {
          return false;
      }
      if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M)
      {
          Network network=connectivityManager.getActiveNetwork();
          if(network==null)
          {
              return false;
          }
          NetworkCapabilities capabilities= connectivityManager.getNetworkCapabilities(network);
          return capabilities!=null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
      }
      else
      {
          NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
          return networkInfo!=null && networkInfo.isConnected();
      }
    }
}