package fun.dooit.wifi_mac.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import fun.dooit.wifi_mac.R;

/**
 * Created by Eric on 2018/2/23.
 */

public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkReceiver";

    private OnNetworkConnectListener mOnNetworkConnectListener;


    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conn = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
        NetworkInfo wifi = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = conn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        //關閉
        if (networkInfo == null) {
            if (!wifi.isConnected()) {
                if (mOnNetworkConnectListener != null) {
                    mOnNetworkConnectListener.onWifi(false);
                    Toast.makeText(context, R.string.wifi_disconnected, Toast.LENGTH_SHORT).show();
                }
            }

            if (!mobile.isConnected()) {
                if (mOnNetworkConnectListener != null) {
                    Toast.makeText(context, R.string.mobile_disconnected, Toast.LENGTH_SHORT).show();
                    mOnNetworkConnectListener.onMobile(false);
                }
            }

            return;
        }

        switch (networkInfo.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                if (mOnNetworkConnectListener != null) {
                    mOnNetworkConnectListener.onWifi(true);
                    Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();
                }
                break;
            case ConnectivityManager.TYPE_MOBILE:
                if (mOnNetworkConnectListener != null) {
                    Toast.makeText(context, R.string.mobile_connected, Toast.LENGTH_SHORT).show();
                    mOnNetworkConnectListener.onMobile(true);
                }
                break;

        }

    }

    public NetworkReceiver setOnNetworkConnected(OnNetworkConnectListener listener) {
        this.mOnNetworkConnectListener = listener;
        return this;
    }

    public interface OnNetworkConnectListener {
        void onWifi(boolean isConnected);

        void onMobile(boolean isConnected);
    }
}
