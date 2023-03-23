package hu.ppke.itk.android.papel1.nature_arm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity
{
    ImageView logoImage;
    TextView logoText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        logoImage = findViewById(R.id.logo);
        logoText = findViewById(R.id.logo_text);

        new Handler(Looper.myLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);

            Pair<View, String>[] pairs = new Pair[]{
                    new Pair<View, String>(logoImage, "logoTransition"),
                    new Pair<View, String>(logoText, "textTransition")
            };

            /*if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M)
            {
                startActivity(intent);
            }
            else
            {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }*/

            startActivity(intent);
        }, 1000);
    }

}
