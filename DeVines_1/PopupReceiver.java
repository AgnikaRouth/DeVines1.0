package devines.com.DeVines_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class PopupReceiver extends AppCompatActivity {
    TextView textView1, textView2;

    ImageView im1,im2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_receiver);

        textView1 = (TextView) findViewById(R.id.tvArea1);
        textView2 = (TextView) findViewById(R.id.tvEmployee1);
        im1 = (ImageView) findViewById(R.id.ivArea1);
        im2 =(ImageView) findViewById(R.id.ivEmployee);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.5),(int)(height*.4));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x=0;
        params.y= -20;

        getWindow().setAttributes(params);
    }
}
