package fun.dooit.wifi_mac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fun.dooit.wifi_mac.util.net.NetworkInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button mBtnGetMac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnGetMac = (Button) findViewById(R.id.btn_getMac);
        mTextView = (TextView) findViewById(R.id.textView2);
        mBtnGetMac.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String mac = NetworkInfo.getMacAddress(this);
        mTextView.setText(mac);
    }
}
