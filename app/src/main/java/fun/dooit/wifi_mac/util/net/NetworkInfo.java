package fun.dooit.wifi_mac.util.net;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import fun.dooit.wifi_mac.BuildConfig;

/**
 * Created by Eric on 2018/2/9.
 */

public class NetworkInfo {


    public static String getMacAddress(Context context) {
        StringBuilder res1 = new StringBuilder();

        //Android SDK 23以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            try {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
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
                res1.append("02:00:00:00:00:00");
            }
            return res1.toString().toUpperCase();
        }


        //Anroid SDK 22以下
        WifiManager wifimsg = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifimsg != null) {
            if (wifimsg.getConnectionInfo() != null) {
                if (wifimsg.getConnectionInfo().getMacAddress() != null) {
                    res1.append(wifimsg.getConnectionInfo().getMacAddress());
                }
            }
        }


        return res1.toString().toUpperCase();
    }


    public static String getMacAddressForSdk21(Context context) {
        StringBuilder res1 = new StringBuilder();
        WifiManager wifimsg = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifimsg != null) {
            if (wifimsg.getConnectionInfo() != null) {
                if (wifimsg.getConnectionInfo().getMacAddress() != null) {
                    res1.append(wifimsg.getConnectionInfo().getMacAddress());
                }
            }
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
                    return "";
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
            res1.append("02:00:00:00:00:00");
        }
        return res1.toString().toUpperCase();
    }


}
