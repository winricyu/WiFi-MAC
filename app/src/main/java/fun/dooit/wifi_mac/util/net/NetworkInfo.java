package fun.dooit.wifi_mac.util.net;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eric on 2018/2/9.
 */

public class NetworkInfo {

    private static final String TAG = "NetworkInfo";
    private static final String DEFAULT_MAC = "02:00:00:00:00:00";


    public static String getMacAddress(Context context) {

        String result = getMacAddressForSdk21(context).trim();

        if (!DEFAULT_MAC.equals(result)) {
            return result;
        }

        //Android SDK 23以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result = getMacAddressForSdk23(context).trim();
        }
        return result;
    }


    public static String getMacAddressForSdk21(Context context) {
        StringBuilder res1 = new StringBuilder();
        try {
            WifiManager wifimsg = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            if (wifimsg == null || wifimsg.getConnectionInfo() == null) {
                return res1.toString();
            }

            if (!TextUtils.isEmpty(wifimsg.getConnectionInfo().getMacAddress())) {
                res1.append(wifimsg.getConnectionInfo().getMacAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res1.toString().toUpperCase();
    }

    public static String getMacAddressForSdk23(Context context) {
        StringBuilder res1 = new StringBuilder();
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return res1.toString();
                }

                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            res1.append(DEFAULT_MAC);
        }
        return res1.toString().toUpperCase();
    }


}
