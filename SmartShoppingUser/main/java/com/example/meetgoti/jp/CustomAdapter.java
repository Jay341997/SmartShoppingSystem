package com.example.meetgoti.jp;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> Products=new ArrayList<Product>();
    ArrayList<Boolean> Check=new ArrayList<Boolean>();
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<Product> Products) {
        this.context = context;
        this.Products = Products;
        for(Product i:Products)
            this.Check.add(false);
        inflter = (LayoutInflater.from( applicationContext ));
    }

    @Override
    public int getCount() {
        return Products.size();
    }

    public ArrayList<Boolean> getCheckArray()
    {
       return Check;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View mview = inflter.inflate( R.layout.activity_listview, null );
        TextView name = (TextView) mview.findViewById( R.id.textView );
        name.setText( Products.get(i).name );
        TextView quantity = (TextView) mview.findViewById( R.id.textView2 );
        quantity.setText( Products.get(i).quantity );
        TextView price = (TextView) mview.findViewById( R.id.textView3 );
        CheckBox check=(CheckBox)mview.findViewById( R.id.checkBox );
        check.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check.set( i,!Check.get(i));
                System.out.println(Check);
            }
        } );

        price.setText( Products.get(i).price );
        return mview;
    }
}
