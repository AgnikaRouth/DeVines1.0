package devines.com.DeVines_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    Toolbar toolbar1;
    private DrawerLayout drawer;
    private CardView tractor1;
    WifiManager wifiManager;
    Switch wifiSwitch,s1; // s1 is the bluetooth switch
    Button dialog;
   private ImageView ImageOpen;

    private TextView register;

   //private Button btlogout;

    private FirebaseAuth mAuth;

    BluetoothAdapter mBluetoothAdapter;
    private static final String TAG = "Home";

    private static final int REQUEST_ENABLE_BT = 1;
    private final int Intentid =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //mAuth authentication
        mAuth = FirebaseAuth.getInstance();

        toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1); //pass the custom toolbar as actionbar

        //open_close navigation bar
        drawer = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar1,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //image onclick enlarge
        ImageOpen = (ImageView) findViewById(R.id.ivbackground);
        ImageOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,ImageViewMax.class));
            }
        });


        //Cardview popup
        tractor1 = (CardView) findViewById(R.id.cvTractor1);
        tractor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, PopupReceiver.class));
            }
        });

        //defining variables
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch = (Switch) findViewById(R.id.swWifi1);

        //adds listener to toggle button
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);

                    //wifiSwitch.setText("Wifi is ON");
                    Toast.makeText(Home.this, "Wifi may take a moment to turn on", Toast.LENGTH_LONG).show();
                } else {
                    wifiManager.setWifiEnabled(false);
                   // wifiSwitch.setText("Wifi is OFF");
                }
            }
        });



        if(wifiManager.isWifiEnabled())
        {
            wifiSwitch.setChecked(true);
            //wifiSwitch.setText("Wifi is ON");
        }
        else
        {
            wifiSwitch.setChecked(false);
            //wifiSwitch.setText("Wifi is OFF");
        }
        //defining variables
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        s1 = (Switch) findViewById(R.id.swBT1);

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                if (isChecked) {
                    Log.d(TAG,"onClick: enabling/disabling bluetooth.");
                    enableDisableBT();

                }
                else
                {
                    Log.d(TAG, "enableDisableBT: disabling BT.");
                    mBluetoothAdapter.disable();
                    //Toast.makeText(this,"Disable bluetooth", LENGTH_SHORT).show();

                }
            }
        });

        //for registration pages
        register = (TextView) findViewById(R.id.tvregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Register1New.class);
                startActivity(intent);
            }
        });




    }
    //navigation drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) //right side on the screen = START will be replaced by END
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }

    //topmenu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_up,menu);
        return true;
    }

    // top menu bar item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.item_implementation)
        {
            Toast.makeText(this,"Implementation", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Home.this, Implementation.class);
            Home.this.startActivity(intent);
            return true;
        }
        if (id == R.id.notification)
        {
            Intent intent = new Intent(Home.this,Notification.class);
            Home.this.startActivity(intent);
            return true;
        }
        if (id == R.id.logout)
        {
            Logout();
        }
        return super.onOptionsItemSelected(item);
    }

    //wifi status check
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    //wifi state receiver
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiSwitch.setChecked(true);
                    //wifiSwitch.setText("WiFi is ON");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiSwitch.setChecked(false);
                   //wifiSwitch.setText("WiFi is OFF");
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy: called.");
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
    }
    public void enableDisableBT()
    {
        if(mBluetoothAdapter == null)
        {
            Log.d(TAG, "EnableDisableBT: Doesn't have BT capabilities");

            //Device doesn't support Bluetooth
        }
        if(!mBluetoothAdapter.isEnabled())
        {
            //to intercept changes in your bluetooth status
            Log.d(TAG,"EnableDisableBT: Enabling BT");
            // Intent to intercept changes in BT status
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);

            // Intent to intercept changes in BT status
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1,BTIntent);



             // the mBroadcastReceiver1 will catch the state change in bluetooth
        }
        //BT is already enabled, hence to disable BT :
        if (mBluetoothAdapter.isEnabled())
        {
            Log.d(TAG,"EnableDisableBT: Disabling BT");


            // Intent to intercept changes in BT status
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1,BTIntent); // the function will catch the state change in bluetooth
            mBluetoothAdapter.disable();
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mBroadcastReceiver1  = new BroadcastReceiver    () {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            //when discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {

                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch ((state))
                {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive:STATE OFF");
                        break;

                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1:STATE TURNING OFF");
                        break;

                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1:STATE ON");
                        break;

                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1:STATE TURNING On");
                        break;


                }
            }
        }
    };

    //Logout Activity
    private void Logout()
    {
        mAuth.signOut();
        finish();
        startActivity(new Intent(Home.this, Reg_Login.class));
    }


    //user deny permission for bluetooth - function

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check if the request code is same as what is passed here it is IntentId

        if (resultCode == RESULT_CANCELED) {
            s1.setChecked(false);

        }
    }

}
