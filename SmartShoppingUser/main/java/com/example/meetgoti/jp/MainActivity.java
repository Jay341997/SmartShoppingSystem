package com.example.meetgoti.jp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;


import com.example.meetgoti.jp.Bluetooth.Scan;
import com.example.meetgoti.jp.Bluetooth.Select;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import me.aflak.bluetooth.Bluetooth;

public class MainActivity extends AppCompatActivity {

    private TextView mValueView;

    ArrayList<Product> Products=new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start_shopping=(Button)findViewById( R.id.button2 );
        start_shopping.setEnabled( false );
        Button check_avaibility=(Button)findViewById( R.id.button3 );
        check_avaibility.setEnabled( false );
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Products");
        myRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and agai
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Jay", "Failed to read value.", databaseError.toException());
            }
        });
    }
    private void showData(com.google.firebase.database.DataSnapshot dataSnapshot) {

        int count=0;
        Products=new ArrayList<Product>(  );
        for(com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren())
        {
            Product temp=new Product();
            temp.name=(String)ds.child("name").getValue().toString();
            temp.quantity=(String)ds.child("quantity").getValue().toString();
            temp.price=(String)ds.child("price").getValue().toString();
            temp.barcode=(String)ds.child( "barcode" ).getValue().toString();
            temp.weight=(String)ds.child( "weight" ).getValue().toString();
            Products.add(temp);
            System.out.println(ds.child("name").getValue());
            count++;
        }
        Button start_shopping=(Button)findViewById( R.id.button2 );
        start_shopping.setEnabled( true );
        Button check_avaibility=(Button)findViewById( R.id.button3 );
        check_avaibility.setEnabled( true );
    }

    public void handlerCheckAvailability(View view) {
        Intent intent=new Intent(this,CheckAvaibility.class);
        intent.putExtra( "Products",Products );
        startActivity(intent);
    }

    public void handlerStartShopping(View view) {
        Intent intent=new Intent(this,Select.class);
        intent.putParcelableArrayListExtra( "Products", Products );
        startActivity(intent);
        //Intent intent=new Intent(this,Select.class);
        //startActivity(intent);
    }

}

