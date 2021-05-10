package devines.com.DeVines_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class RegisterPage3 extends AppCompatActivity {
    private Button skip;
            private ImageView add;
    Button before,after;
    Spinner sp;
    //private FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page3);
        skip = (Button) findViewById(R.id.btskip3);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPage3.this, Register4.class);
                startActivity(intent);
            }
        });

        add = (ImageView) findViewById(R.id.ivadd3);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterPage3.this,ParcelEntry.class);
                startActivity(i);
            }
        });

        before = (Button) findViewById(R.id.btregister3before);
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage3.this,RegisterPage2.class));
            }
        });
        after = (Button) findViewById(R.id.btregister3after);
        after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPage3.this,Register4.class));
            }
        });


        //spinner define
        sp = (Spinner) findViewById(R.id.spSoil);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RegisterPage3.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.soil_types));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(myAdapter);

        /*actionButton = (FloatingActionButton) findViewById(R.id.fab1);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPage3.this,ParcelEntry.class);
                startActivity(intent);

                // Toast.makeText(Register3.this,"Fab clicked",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

}
