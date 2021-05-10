package devines.com.DeVines_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Register4 extends AppCompatActivity {
    private Button skip;
    private ImageView add;
    private Button before, after;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);

        skip = (Button) findViewById(R.id.btskip4);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register4.this, Register5.class);
                startActivity(intent);
            }
        });

        add = (ImageView) findViewById(R.id.ivadd4);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register4.this,EmployeeEntry.class);
                startActivity(i);
            }
        });

        before = (Button)findViewById(R.id.btregister4before);
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register4.this,RegisterPage3.class));
            }
        });

        after = (Button) findViewById(R.id.btregister4after);
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register4.this, Register5.class));
            }
        });
    }


}
