package com.luffy.mvp.reponse;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GossipBean implements Parcelable {


    public String qry;
    public String getUserName(){
        return qry;
    }
    private String gprid;
    private List<ResultsBean> results;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.qry);
        dest.writeString(this.gprid);
        dest.writeTypedList(this.results);
    }

    public GossipBean() {
    }

    protected GossipBean(Parcel in) {
        this.qry = in.readString();
        this.gprid = in.readString();
        this.results = in.createTypedArrayList(ResultsBean.CREATOR);
    }

    public static final Parcelable.Creator<GossipBean> CREATOR = new Parcelable.Creator<GossipBean>() {
        @Override
        public GossipBean createFromParcel(Parcel source) {
            return new GossipBean(source);
        }

        @Override
        public GossipBean[] newArray(int size) {
            return new GossipBean[size];
        }
    };
}