package devines.com.DeVines_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register1New extends AppCompatActivity {
    FirebaseFirestore fStore;
    private TextInputLayout textPhoneNumber;
    private EditText usurname, uname;
    Button next;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1_new);

        next = (Button)findViewById(R.id.btnext1);
        fStore = FirebaseFirestore.getInstance();
        usurname = (EditText) findViewById(R.id.etsurname);
        uname = (EditText) findViewById(R.id.etname);
        textPhoneNumber= findViewById(R.id.tiphone);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String email = uemail.getText().toString().trim();
                //String password = upassword.getText().toString().trim();
                final String name = uname.getText().toString().trim();
                final String surname = usurname.getText().toString().trim();
                final String phone = textPhoneNumber.getEditText().getText().toString().trim();

                //creation for database
                DocumentReference documentReference = fStore.collection("users").document("First Page");
                Map<String,Object> user = new HashMap<>();
                user.put("Name", name);
                user.put("Last Name", surname);
                user.put("Phone",phone);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Register1New.this, "Success", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onSuccess : user profile is created ");

                    }
                });

                startActivity(new Intent(getApplicationContext(),RegisterPage2.class));


            }
        });

    }
}
