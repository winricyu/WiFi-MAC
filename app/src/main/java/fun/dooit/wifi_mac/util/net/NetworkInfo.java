package fun.dooit.wifi_mac.util.net;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;

import fun.dooit.wifi_mac.BuildConfig;

/**
 * Created by Eric on 2018/2/9.
 */

public class NetworkInfo {


    public static String getMacAddress(Context context) {
        String mac = "";
        WifiManager wifimsg = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (wifimsg != null) {
                if (wifimsg.getConnectionInfo() != null) {
                    if (wifimsg.getConnectionInfo().getMacAddress() != null) {
                        mac = wifimsg.getConnectionInfo().getMacAddress();
                    }
                }
            }


            return mac;

        }


        return mac;
    }

}
