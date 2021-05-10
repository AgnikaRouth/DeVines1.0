package devines.com.DeVines_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText login_email;
    private EditText login_password;
    private TextView forgot;
    private Button bt_login;
   // private CheckBox checkbox;
    //private TextView passwordreset;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize all variables
        login_email = (EditText)findViewById(R.id.etlogin);
        login_password= (EditText) findViewById(R.id.etpasswordlogin);
        bt_login = (Button) findViewById(R.id.btlogin);
        forgot = (TextView) findViewById(R.id.tvforgotpassword);
       // checkbox = (CheckBox) findViewById(R.id.cbrememberme);

        bt_login = (Button) findViewById(R.id.btlogin);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(login_email.getText().toString(), login_password.getText().toString());

            }
        });

        mAuth = FirebaseAuth.getInstance();
        //to check the user is already registered or not
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
        {
            startActivity(new Intent(Login.this, Home.class));
        }

        progressDialog = new ProgressDialog(this);

        //forgot password
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset code

                final EditText resetmail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Mot de passe oublié ? ?");
                passwordResetDialog.setMessage("Entrez votre e-mail pour réinitialiser votre mot de passe");
                passwordResetDialog.setView(resetmail);

                //alert Dialogue functions
                passwordResetDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the email and set reset link
                        String mail = resetmail.getText().toString().trim();
                        //Firebase Reset Email option
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Lien de réinitialisation du mot de passe envoyé à votre e-mail",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) { //call exception if error is caught
                                Toast.makeText(Login.this, "Erreur ! Lien de réinitialisation NON envoyé"+ e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialogue
                    }
                });
                passwordResetDialog.create().show();
            }
        });



    }

    //validate your user inputs
    private void validate(String userName, String userPassword)
    {
        progressDialog.setMessage("L'utilisateur se connecte...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    //startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    Toast.makeText(Login.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "La connexion a échoué, veuillez réessayer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified(); //flag will be true if user has verified his email

        if(emailflag)
        {
            finish();
            startActivity(new Intent(Login.this,Home.class));
        }
        else
        {
            Toast.makeText(this, "Verifié votre email", Toast.LENGTH_SHORT).show();
            mAuth.signOut();

        }
    }
}
