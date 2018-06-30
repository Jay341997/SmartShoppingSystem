package com.example.jay2.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CheckAvaibility extends AppCompatActivity {
    ArrayList<Product> Products=new ArrayList<Product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_avaibility);
        Intent intent=getIntent();
        Product temp=new Product();

        System.out.println(temp.name);
        Products=intent.getParcelableArrayListExtra( "Products" );
        ArrayList<String> ProductsList=new ArrayList<String>();
        for(Product i:Products)
        {
            ProductsList.add(i.name);
        }
        System.out.println(ProductsList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, ProductsList);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.countries_list);
        textView.setAdapter(adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Products");



    }

    public void handlerSearch(View view) {

        final String message = "Book";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Products");
        myRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and agai
                AutoCompleteTextView autoText=findViewById( R.id.countries_list);
                String msg=autoText.getText().toString();
                for(com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren())
                {
                    if(ds.child( "name" ).getValue().equals( msg )) {
                        TextView textView=findViewById( R.id.textView );
                        textView.setText( "Total Available items :" );
                        TextView textView2=findViewById( R.id.textView2 );
                        String s=  ds.child( "quantity" ).getValue().toString();
                        textView2.setText( s );
                        // System.out.println(  ds.child( "quantity").getValue()  );
                    }
                    System.out.println( ds.child( "quantity" ).getValue() );
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Jay", "Failed to read value.", databaseError.toException());
            }
        });

    }
}