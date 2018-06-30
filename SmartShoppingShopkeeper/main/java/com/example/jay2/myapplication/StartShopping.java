package com.example.jay2.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class StartShopping extends AppCompatActivity {
    private Button scan_btn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Products");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start_shopping );
        scan_btn=(Button)findViewById( R.id.button5 );
        final Activity activity = this;
        scan_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator( activity );
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt( "Scan" );
                integrator.setCameraId( 0 );
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled( false );
                integrator.initiateScan( );
            }
        } );

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult( requestCode,resultCode,data );
        if(result!=null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText( this,"You cncelled the scanner",Toast.LENGTH_LONG ).show();

            }
            else
            {
                // Toast.makeText( this ,result.getContents(),Toast.LENGTH_LONG).show();
                EditText barcode_name=(EditText)findViewById( R.id.editText );
                barcode_name.setText( result.getContents() );
            }
        }
        else
        {
            super.onActivityResult( requestCode, resultCode, data );
        }


    }
    public void handlerAddProduct(View view) {
        EditText barcode=(EditText)findViewById( R.id.editText );
        EditText name=(EditText)findViewById( R.id.editText2 );
        EditText price=(EditText)findViewById( R.id.editText3 );
        EditText weight=(EditText)findViewById( R.id.editText4 );
        EditText quantity=(EditText)findViewById( R.id.editText5 );
        if(barcode.getText().toString().equals( "" )||name.getText().toString().equals( "" )||price.getText().toString().equals( "" )||weight.getText().toString().equals( "" )||quantity.getText().toString().equals( "" ))
        {
            Toast.makeText( this, "please filled all input", Toast.LENGTH_SHORT ).show();
        }
        else
        {

            Product temp=new Product(  );
            temp.barcode=barcode.getText().toString();
            temp.name=name.getText().toString();
            temp.price=price.getText().toString();
            temp.quantity=quantity.getText().toString();
            temp.weight=weight.getText().toString();
          //  myRef.child( barcode.getText().toString() ).setValue( temp );
            myRef.child( barcode.getText().toString() ).child("barcode").setValue( barcode.getText().toString() );
           myRef.child( barcode.getText().toString() ).child("name").setValue( name.getText().toString() );
           myRef.child( barcode.getText().toString() ).child("price").setValue( price.getText().toString() );
           myRef.child( barcode.getText().toString() ).child("weight").setValue( weight.getText().toString() );
           myRef.child( barcode.getText().toString() ).child("quantity").setValue( quantity.getText().toString() );
            barcode.setText("");
            name.setText("");
            price.setText("");
            quantity.setText("");
            weight.setText("");
            Toast.makeText( this, "added successfuly", Toast.LENGTH_SHORT ).show();
        }
    }

    public void handlerCanel(View view) {
        EditText barcode=(EditText)findViewById( R.id.editText );
        EditText name=(EditText)findViewById( R.id.editText2 );
        EditText price=(EditText)findViewById( R.id.editText3 );
        EditText weight=(EditText)findViewById( R.id.editText4 );
        EditText quantity=(EditText)findViewById( R.id.editText5 );
        barcode.setText("");
        name.setText("");
        price.setText("");
        quantity.setText("");
        weight.setText("");
    }
}
