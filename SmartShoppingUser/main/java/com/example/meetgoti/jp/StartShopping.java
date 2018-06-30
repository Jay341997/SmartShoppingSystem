package com.example.meetgoti.jp;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetgoti.jp.Bluetooth.Chat;
import com.example.meetgoti.jp.Bluetooth.Select;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.util.ArrayList;

import me.aflak.bluetooth.Bluetooth;

public class StartShopping extends AppCompatActivity implements Bluetooth.CommunicationCallback {
    private CustomAdapter customAdapter;
    private String name;
    private Bluetooth b;
    private EditText message;
    private Button send;
    private TextView text;
    private ScrollView scrollView;
    private boolean registered=false;
    private Button scan_btn;
    ListView simpleList;
    ArrayList<Product> Products=new ArrayList<Product>();
    ArrayList<Product> ProductsBill=new ArrayList<Product>();
    private Button remove;
    private  Button buy;
    private int total=0;
    private int weight=0;
    private Button pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start_shopping );
        Intent intent=getIntent();
        Products=intent.getParcelableArrayListExtra( "Products" );
        scan_btn=(Button) findViewById( R.id.button5 );
        final Activity activity = this;
        pass=(Button) findViewById( R.id.button6 );
        scan_btn.setEnabled(false);
        buy=(Button) findViewById( R.id.button7 );
        buy.setEnabled(false);
        pass.setEnabled( false );
        simpleList = (ListView)findViewById(R.id.simpleListView);
        remove=(Button)findViewById( R.id.button8 );
        remove.setEnabled( false );
        System.out.println(ProductsBill);
        customAdapter = new CustomAdapter(getApplicationContext(), ProductsBill );
        simpleList.setAdapter(customAdapter);
        b = new Bluetooth(this);
        b.enableBluetooth();
        text=(TextView)findViewById( R.id.textView4 );
        b.setCommunicationCallback(this);
        int pos = getIntent().getExtras().getInt("pos");
        name = b.getPairedDevices().get(pos).getName();
        text.setText("Connecting...");
        b.connectToDevice(b.getPairedDevices().get(pos));
        Toast.makeText( this ,name,Toast.LENGTH_LONG).show();
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        registered=true;
        buy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.send( "0" );
                AlertDialog.Builder mBuilder=new AlertDialog.Builder( StartShopping.this );
                View mView = getLayoutInflater().inflate(R.layout.activity_bill,null);
                final TextView bill_total=(TextView) mView.findViewById( R.id.textView9 );
                final TextView bill_weight=(TextView) mView.findViewById( R.id.textView11 );
                bill_total.setText( Integer.toString( total ) );
                bill_weight.setText( Integer.toString( weight ));
                mBuilder.setView( mView )
                        .setTitle( "Bill" )
                        .setNegativeButton( "cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        } )
                        .setPositiveButton( "submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Products");
                                for(Product it:ProductsBill)
                                {
                                    int temp=Integer.parseInt(it.quantity);
                                    int q=temp;
                                    for(Product it2:Products)
                                    {
                                        if(it.barcode.equals( it2.barcode ))
                                        {
                                            q=Integer.parseInt(it2.quantity);
                                            q=q-temp;
                                        }
                                    }
                                    myRef.child(it.barcode).child( "quantity" ).setValue( Integer.toString(q) );
                                }
                                ProductsBill=new ArrayList<Product>(  );
                                simpleList = (ListView) findViewById( R.id.simpleListView );
                                System.out.println( ProductsBill );
                                customAdapter = new CustomAdapter( getApplicationContext(), ProductsBill );
                                simpleList.setAdapter( customAdapter );
                                total=0;
                                weight=0;
                                TextView Total = (TextView) findViewById( R.id.textView7 );
                                TextView Weight = (TextView) findViewById( R.id.weight );
                                Total.setText( Integer.toString( total ) );
                                Weight.setText( Integer.toString( weight ) );
                            }
                        } );


                AlertDialog dialog=mBuilder.create();
                dialog.show();
            }
        } );
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
        pass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder=new AlertDialog.Builder( StartShopping.this );
                View mView = getLayoutInflater().inflate(R.layout.dialog_password,null);
                final EditText mPassword=(EditText) mView.findViewById( R.id.editText );

                mBuilder.setView( mView )
                        .setTitle( "Password" )
                        .setNegativeButton( "cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        } )
                        .setPositiveButton( "submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (mPassword.getText().toString().equals( "12345678" )) {
                                    b.send( "2" );
                                    remove=(Button)findViewById( R.id.button8 );
                                    remove.setEnabled( true );
                                } else {
                                    Toast.makeText( activity, "Please Enter Correct password!!", Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } );


                AlertDialog dialog=mBuilder.create();
                dialog.show();

            }
        } );
        remove.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Boolean> temp = customAdapter.getCheckArray();
                int count = 0;
                for (Boolean j : temp) {
                    if (j) {
                        weight = weight - Integer.parseInt( ProductsBill.get( count ).weight.toString() );
                        total = total - Integer.parseInt( ProductsBill.get( count ).price.toString() );
                        ProductsBill.remove( ProductsBill.get( count ) );

                    } else
                        count++;
                }
                b.send( "3" );
              //  Toast.makeText( activity, Integer.toString( total ), Toast.LENGTH_SHORT ).show();
              //  Toast.makeText( activity, Integer.toString( weight ), Toast.LENGTH_SHORT ).show();
                simpleList = (ListView) findViewById( R.id.simpleListView );
                System.out.println( ProductsBill );
                customAdapter = new CustomAdapter( getApplicationContext(), ProductsBill );
                simpleList.setAdapter( customAdapter );
                TextView Total = (TextView) findViewById( R.id.textView7 );
                TextView Weight = (TextView) findViewById( R.id.weight );
                 Total.setText( Integer.toString( total ) );
                Weight.setText( Integer.toString( weight ) );
              //  Toast.makeText( activity, "Removed", Toast.LENGTH_SHORT ).show();
                remove.setEnabled( false );

            }
        } );

    }

    @Override
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
                int flag=1;
                for(Product i:ProductsBill)
                {
                    if(i.barcode.equals(result.getContents().toString()  ))
                    {
                        int v=Integer.parseInt(i.quantity);
                        v++;
                        i.quantity=Integer.toString( v );
                        flag=0;
                        break;
                    }
                }
                if(flag==1)
                for(Product i:Products)
                {

                    if(i.barcode.equals( result.getContents().toString() ))
                    {
                        Product temp=new Product();
                        temp.name=i.name;
                        temp.barcode=i.barcode;
                        temp.quantity="1";
                        temp.price=i.price;
                        temp.weight=i.weight;
                        ProductsBill.add(temp);
                        total=total+Integer.parseInt( temp.price );
                        weight=weight+Integer.parseInt( temp.weight );
                        Toast.makeText( this ,temp.name,Toast.LENGTH_LONG).show();
                        //Toast.makeText( this ,Integer.toString( total ),Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                simpleList = (ListView)findViewById(R.id.simpleListView);
                System.out.println(ProductsBill);
                customAdapter = new CustomAdapter(getApplicationContext(), ProductsBill );
                simpleList.setAdapter(customAdapter);
                TextView Total=(TextView)findViewById( R.id.textView7 );
                TextView Weight=(TextView)findViewById( R.id.weight );
                Total.setText(Integer.toString( total ));
                Weight.setText( Integer.toString(weight));
                b.send( "1" );
            }
        }
        else
        {
            super.onActivityResult( requestCode, resultCode, data );
        }


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(registered) {
            unregisterReceiver(mReceiver);
            registered=false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onConnect(BluetoothDevice device) {
        text=(TextView)findViewById( R.id.textView4 );
        Display("Connected");
        scan_btn=(Button) findViewById( R.id.button5 );
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scan_btn.setEnabled(true);
                pass.setEnabled( true );
                buy.setEnabled( true );
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.close:
                b.removeCommunicationCallback();
                b.disconnect();
                Intent intent = new Intent(this, Select.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void Display(final String s){
        text=(TextView)findViewById( R.id.textView4 );
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(s + "\n");

            }
        });
    }
    @Override
    public void onDisconnect(BluetoothDevice device, String message) {
        Display("Disconnected!");
        Display("Connecting again...");
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scan_btn=(Button) findViewById( R.id.button5 );
                pass=(Button) findViewById( R.id.button6 );
                scan_btn.setEnabled(false);
                buy=(Button) findViewById( R.id.button7 );
                buy.setEnabled(false);
                pass.setEnabled( false );
                remove=(Button)findViewById( R.id.button8 );
                remove.setEnabled( false );
            }
        });
        b.connectToDevice(device);
    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onError(String message) {
        text=(TextView)findViewById( R.id.textView4 );
        Display("Error: "+message);
    }

    @Override
    public void onConnectError(final BluetoothDevice device, String message) {
        text=(TextView)findViewById( R.id.textView4 );
        Display("Error: "+message);
        Display("Trying again in 3 sec.");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        b.connectToDevice(device);
                    }
                }, 2000);
            }
        });
    }
    public void onBackPressed() {
        Intent i = new Intent(StartShopping.this,MainActivity.class);
        i.putExtra("Check","Check");
        startActivity(i);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                Intent intent1 = new Intent(StartShopping.this, Select.class);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        if(registered) {
                            unregisterReceiver(mReceiver);
                            registered=false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        if(registered) {
                            unregisterReceiver(mReceiver);
                            registered=false;
                        }
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        }
    };
}
