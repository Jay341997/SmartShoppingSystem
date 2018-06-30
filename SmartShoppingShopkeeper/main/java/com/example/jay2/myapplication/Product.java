package com.example.jay2.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

class Product implements Parcelable {
    String barcode;
    String name;
    String quantity;
    String price;
    String weight;

    protected Product(Parcel in) {
        name = in.readString();
        quantity = in.readString();
        price = in.readString();
        barcode=in.readString();
        weight=in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product( in );
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public Product() {

    }

    public int describeContents() {
        return this.hashCode();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(quantity);
        dest.writeString(price);
        dest.writeString(barcode);
        dest.writeString(weight);
    }


}
