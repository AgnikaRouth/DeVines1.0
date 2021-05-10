package devines.com.DeVines_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Reg_Login extends AppCompatActivity {
    Button blogin, breg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg__login);

        //initialize buttons
        blogin = (Button)findViewById(R.id.btlogin);
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reg_Login.this,Login.class);
                startActivity(intent);

            }
        });
        breg = (Button) findViewById(R.id.btregister);
        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reg_Login.this,Register1.class);
                startActivity(intent);

            }
        });
    }
}
