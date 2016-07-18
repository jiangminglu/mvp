
package com.luffy.mvp.reponse;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultsBean implements Parcelable {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
    }

    public ResultsBean() {
    }

    protected ResultsBean(Parcel in) {
        this.key = in.readString();
    }

    public static final Parcelable.Creator<ResultsBean> CREATOR = new Parcelable.Creator<ResultsBean>() {
        @Override
        public ResultsBean createFromParcel(Parcel source) {
            return new ResultsBean(source);
        }

        @Override
        public ResultsBean[] newArray(int size) {
            return new ResultsBean[size];
        }
    };
}