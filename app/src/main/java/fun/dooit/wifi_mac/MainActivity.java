package fun.dooit.wifi_mac;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fun.dooit.wifi_mac.service.NetworkReceiver;
import fun.dooit.wifi_mac.util.net.NetworkInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView mTextMac1, mTextMac2, mTextLog;
    private Button mBtnGetMac;
    private NetworkReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGetMac = (Button) findViewById(R.id.btn_getMac);
        mTextMac1 = (TextView) findViewById(R.id.text_mac_1);
        mTextMac2 = (TextView) findViewById(R.id.text_mac_2);
        mTextLog = (TextView) findViewById(R.id.text_log);
        mTextLog.setMovementMethod(new ScrollingMovementMethod());
        mBtnGetMac.setOnClickListener(this);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new NetworkReceiver();
        mReceiver.setOnNetworkConnected(new NetworkReceiver.OnNetworkConnectListener() {
            @Override
            public void onWifi(boolean isConnected) {
                Log.d(TAG, "onWifi() called with: isConnected = [" + isConnected + "]");
                insertLog("WiFi is Connected: " + isConnected);
            }

            @Override
            public void onMobile(boolean isConnected) {
                Log.d(TAG, "onMobile() called with: isConnected = [" + isConnected + "]");
                insertLog("Mobile is Connected: " + isConnected);

            }
        });
        this.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            this.unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        checkNetworkConnection();
        mTextMac1.setText("");
        mTextMac2.setText("");
        mTextMac1.setText(NetworkInfo.getMacAddressForSdk23(this));
        mTextMac2.setText(NetworkInfo.getMacAddressForSdk21(this));
        insertLog(NetworkInfo.getMacAddress(this));
    }


    private void checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        Log.d(TAG, "Wifi connected: " + isWifiConn);
        Log.d(TAG, "Mobile connected: " + isMobileConn);
        String wifi = isWifiConn ? "ON" : "OFF";
        String mobile = isMobileConn ? "ON" : "OFF";
        StringBuilder sb = new StringBuilder("------\r\n");
        sb.append("Wifi: " + wifi + " , Mobile: " + mobile);
        insertLog(sb.toString());
    }

    private void insertLog(String text) {
        Log.d(TAG, "insertLog() called with: text = [" + text + "]");
        if (TextUtils.isEmpty(mTextLog.getText())) {
            mTextLog.setText(text + "\n\r");
        } else {
            mTextLog.append(text + "\n\r");
        }

        if (mTextLog.getLayout()==null){
            return;
        }
        final int scrollAmount = mTextLog.getLayout().getLineTop(mTextLog.getLineCount()) - mTextLog.getHeight();
        if (scrollAmount > 0) {
            mTextLog.scrollTo(0, scrollAmount);
        } else {
            mTextLog.scrollTo(0, 0);
        }

    }
}
