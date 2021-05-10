package devines.com.DeVines_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewMax extends AppCompatActivity {
    private ImageView popupimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_max);


        popupimage = (ImageView) findViewById(R.id.ivbackgroundMax);
    }
}
