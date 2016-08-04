package com.example.chaitanya.afapp;

import android.os.Parcel;
import android.os.Parcelable;

// Parcelabler class where all the data is parceled from the json
public class Promotions implements Parcelable {

    String title;
    String businessURL;
    String description;
    String footer;
    String image;

    public Promotions(String title, String businessURL, String description, String footer, String image) {
        this.title = title;
        this.businessURL = businessURL;
        this.description = description;
        this.footer = footer;
        this.image = image;
    }

    protected Promotions(Parcel in) {
        title = in.readString();
        businessURL = in.readString();
        description = in.readString();
        footer = in.readString();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(businessURL);
        dest.writeString(description);
        dest.writeString(footer);
        dest.writeString(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Promotions> CREATOR = new Parcelable.Creator<Promotions>() {
        @Override
        public Promotions createFromParcel(Parcel in) {
            return new Promotions(in);
        }

        @Override
        public Promotions[] newArray(int size) {
            return new Promotions[size];
        }
    };
}
