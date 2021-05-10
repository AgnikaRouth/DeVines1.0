package devines.com.DeVines_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage2 extends AppCompatActivity {
    Button before,after;
    EditText ucountry,upin, ucity, uaddress,udob;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String PersonalDetails;
    Spinner sp;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page2);

        before = (Button)findViewById(R.id.btregister2before);
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterPage2.this,Register1.class);
                startActivity(i);
            }
        });

        after = (Button)findViewById(R.id.btregister2after);
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registersave();
            }
        });

        //initialize all the variables
        //ucountry = (EditText) findViewById(R.id.etCountry);
        uaddress = (EditText) findViewById(R.id.etaddress);
        upin = (EditText) findViewById(R.id.etpincode);
        ucity = (EditText) findViewById(R.id.etcity);
        udob = (EditText) findViewById(R.id.etdob);
        mAuth =FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        sp = (Spinner)findViewById(R.id.spCountry);

        //create spinner

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RegisterPage2.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.country_names));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(myAdapter);

    }

    public void registersave()
    {

        final Boolean[] result = {false};
        String country = sp.getSelectedItem().toString().trim();
        String address = uaddress.getText().toString().trim();
        String pin = upin.getText().toString().trim();
        String city = ucity.getText().toString().trim();
        String dob = udob.getText().toString().trim();

        if( TextUtils.isEmpty(country) || TextUtils.isEmpty(address) || TextUtils.isEmpty(pin) || TextUtils.isEmpty(city) || TextUtils.isEmpty(dob) )
        {
            Toast.makeText(RegisterPage2.this,"Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {

            Toast.makeText(RegisterPage2.this,"Save is successful",Toast.LENGTH_SHORT).show();
           // userID = mAuth.getCurrentUser().getUid();

            //creation for database
            DocumentReference documentReference = fStore.collection("users details").document("Personal Details");
            Map<String,Object> user = new HashMap<>();
            user.put("Country", country);
            user.put("Address",address);
            user.put("Pin",pin);
            user.put("City",city);
            user.put("DOB",dob);

            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess : details of user profile is created for "+ PersonalDetails);
                }
            });

            startActivity(new Intent(getApplicationContext(),RegisterPage3.class));
        }

    }
}
