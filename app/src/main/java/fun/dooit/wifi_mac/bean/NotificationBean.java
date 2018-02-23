package fun.dooit.wifi_mac.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 推播訊息的資料物件
 */
public class NotificationBean implements Parcelable {

    public static class Type {

        public static final String MESSAGE = "0";//一般訊息
        public static final String CUST_REMIND = "A00";//提醒頁
        public static final String NEWS_SUBSCRIBE_SALE = "A01";//我的通知-新進物件
        public static final String NEWS_SUBSCRIBE_PRICE = "A02";//我的通知-價格變動
        public static final String NEWS_SUBSCRIBE_SOLD = "A03";//我的通知-成交物件
        public static final String NEWS_SUBSCRIBE_OFF = "A04";//我的通知-下架物件
        public static final String SYSTEM_EMERGENCY = "A05";//系統公告
        public static final String CUST_TASK = "A06";//任務頁
        public static final String CUST_PLAN = "A09";//計畫行程頁

        public static final int FROM_IM_PLAN = 1; //從IM帶入資料到計畫行程-帶看
        public static final int FROM_IM_DONE = 2; //從IM帶入資料到完成行程-庫存回報

    }

    private String mType;
    private String mTitle;
    private String mContent;


    public NotificationBean() {
        this.init();
    }


    private void init() {
        mType = Type.MESSAGE;
        mTitle = "";
        mContent = "";
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String message) {
        this.mContent = message;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("mType:" + this.mType + "\n");
        sb.append("mTitle:" + this.mTitle + "\n");
        sb.append("mContent:" + this.mContent + "\n");
        return sb.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mType);
        dest.writeString(this.mTitle);
        dest.writeString(this.mContent);
    }

    protected NotificationBean(Parcel in) {
        this.mType = in.readString();
        this.mTitle = in.readString();
        this.mContent = in.readString();
    }

    public static final Creator<NotificationBean> CREATOR = new Creator<NotificationBean>() {
        @Override
        public NotificationBean createFromParcel(Parcel source) {
            return new NotificationBean(source);
        }

        @Override
        public NotificationBean[] newArray(int size) {
            return new NotificationBean[size];
        }
    };
}
