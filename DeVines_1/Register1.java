package devines.com.DeVines_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Register1 extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =  //custom define pattern for password
            Pattern.compile("^" +
                    "(?=.*[0-9])" +           //at least 1 number
                    //"(?=.*[a-z A-Z])" +      //any letter
                    //"(?=.*[a-z])" +             //atleast 1 lower case letter
                    //"(?=.*[A-Z])" +             //atleast 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    private TextInputLayout textInputEmail;
   private TextInputLayout textPhoneNumber;
    private TextInputLayout textInputPassword1,textInputPassword2;
    private EditText usurname, uname;
    private Button buttonsave, buttonnext;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    public static final String TAG = "MainActivity";
    //FirebaseDatabase rootNode;
    //DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);


        //initialize variables
        usurname = (EditText) findViewById(R.id.etsurname);
        uname = (EditText) findViewById(R.id.etname);


       textPhoneNumber= findViewById(R.id.tiphone);
        textInputEmail = findViewById(R.id.tiemail);
        textInputPassword1 = findViewById(R.id.tipass1);
        textInputPassword2 = findViewById(R.id.tipass2);
        //buttonsave =(Button)findViewById(R.id.btsave1);
        buttonnext = (Button) findViewById(R.id.btnext1);

        //next page
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) { //check for all details and then go next
                    //register new user in the database
                    registerNewUser();
                    Intent intent = new Intent(Register1.this, RegisterPage2.class);
                    startActivity(intent);

                }
                }
        });



        //taking Firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        //Firestore instantiation
        fStore = FirebaseFirestore.getInstance();



        //Set on Click listener for Registration Button

       /* buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate());
                {
                    registerNewUser();
                   //startActivity(new Intent(Register1.this, RegisterPage2.class));

                }


                //or directly call the function registerNewUser()
            }
        });*/


    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Le champ ne peut pas être vide\"");
            return false;
        }
        //else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        else if(!emailInput.matches(emailPattern))
        {
            textInputEmail.setError("Veuillez mettez une adresse email valide");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }
    //validate phone number
    private boolean validatePhoneNumber() {
        String phoneNumber = textPhoneNumber.getEditText().getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            textPhoneNumber.setError("Le champ ne peut pas être vide");
            return false;
        } else if (phoneNumber.length() > 12) {
            textPhoneNumber.setError("Numéro de téléphone est trop long");
            return false;
        } else {
            textPhoneNumber.setError(null);
            return true;
        }
    }

    //validate password

    private boolean validatePassword() {
        String passwordInput1 = textInputPassword1.getEditText().getText().toString().trim();
        String passwordInput2 = textInputPassword2.getEditText().getText().toString().trim();
            if ((passwordInput1.isEmpty()) | passwordInput2.isEmpty())
            {
                textInputPassword1.setError("Le champ ne peut pas être vide");
                textInputPassword2.setError("Le champ ne peut pas être vide");
                return false;
            }
            else if (!PASSWORD_PATTERN.matcher(passwordInput1).matches())
            {
                textInputPassword1.setError("Mot de passe est trop faible");
                return false;
            }
            else if(!(passwordInput2.equals(passwordInput1)))
            {
                textInputPassword2.setError("Veuillez saisir le même mot de passe");
                return false;
            }
        else {
            textInputPassword1.setError(null);
            return true;
        }
    }



    public void ConfirmInput(View v) {
        if (!validateEmail() | !validatePhoneNumber() | !validatePassword()) {
            return;
        }
        String input = "Email: " + textInputEmail.getEditText().getText().toString();
        input += "\n";
        input += "Phone number: " +textPhoneNumber.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + textInputPassword2.getEditText().getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();


    }

    //validate all inputs are filled in
    private Boolean validate()
    {
        Boolean result = false;
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        String passwordInput1 = textInputPassword1.getEditText().getText().toString().trim();
        //String phoneNumber = textPhoneNumber.getEditText().getText().toString().trim();
        String name = uname.getText().toString().trim();
        String surname = usurname.getText().toString().trim();
        String phone = textPhoneNumber.getEditText().getText().toString().trim();



        if(emailInput.isEmpty() || passwordInput1.isEmpty() || phone.isEmpty() || name.isEmpty() || surname.isEmpty() )
        {
            Toast.makeText(this, "Veuillez saisir tous les détails", Toast.LENGTH_SHORT).show();

        }
        else
        {
            if (!validateEmail() | !validatePhoneNumber() | !validatePassword()) {
                return true;
            }

            userID = mAuth.getCurrentUser().getUid();

            //creation for database
            DocumentReference documentReference = fStore.collection("users").document("userID");
            Map<String,Object> user = new HashMap<>();
            user.put("Name", name);
            user.put("Last Name", surname);
            user.put("Phone",phone);
            user.put("Email",emailInput);

            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess : user profile is created for "+userID);

                }
            });

            result = true;
        }
        return  result;
    }

    //create and register new user
    private void registerNewUser() {
        //Take value of two user inputs in Strings
        final String email, password, name, surname, phone;
        email = textInputEmail.getEditText().getText().toString().trim();
        password = textInputPassword1.getEditText().getText().toString().trim();
        name = uname.getText().toString().trim();
        surname = usurname.getText().toString().trim();
        phone = textPhoneNumber.getEditText().getText().toString().trim();


        //Validations for input email and password
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }


        //Create or register new user
        mAuth
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(Register1.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            sendEmailVerification();


                        }
                        else
                        {
                            //Registration failed

                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }

                    }
                });

        //creation for database
        DocumentReference documentReference = fStore.collection("users").document("First Page");
        Map<String,Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Last Name", surname);
        user.put("Phone",phone);

        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Register1.this, "Success", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onSuccess : user profile is created ");

            }
        });

    }

    //send verification email in your mailbox

    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = mAuth .getCurrentUser();
        if(firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Register1.this,"Enregistrement réussi, un courrier de vérification a été envoyé", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(Register1.this, RegisterPage2.class));
                    }
                    else
                    {
                        Toast.makeText(Register1.this,"Erreur d'envoyer l'email de vérification", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



}
