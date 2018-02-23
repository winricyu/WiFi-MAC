package fun.dooit.wifi_mac.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FCMFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static final String TAG = "FCMInstanceIDService";
    private String mToken;

    @Override
    public void onTokenRefresh() {
        mToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onTokenRefresh: " + mToken);

    }


}
