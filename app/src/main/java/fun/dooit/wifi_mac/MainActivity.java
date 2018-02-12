package fun.dooit.wifi_mac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fun.dooit.wifi_mac.util.net.NetworkInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMac1, mTextMac2;
    private Button mBtnGetMac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGetMac = (Button) findViewById(R.id.btn_getMac);
        mTextMac1 = (TextView) findViewById(R.id.text_mac_1);
        mTextMac2 = (TextView) findViewById(R.id.text_mac_2);
        mBtnGetMac.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mTextMac1.setText("");
        mTextMac2.setText("");
        mTextMac1.setText(NetworkInfo.getMacAddressForSdk23(this));
        mTextMac2.setText(NetworkInfo.getMacAddressForSdk21(this));
    }
}
