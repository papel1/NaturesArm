package hu.ppke.itk.android.papel1.nature_arm.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

import hu.ppke.itk.android.papel1.nature_arm.MainActivity;
import hu.ppke.itk.android.papel1.nature_arm.R;

public class LanguageHelper extends AppCompatActivity
{
    Locale loadLanguage(Context ctx)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        String defaultValue = sharedPreferences.getString("Language", "en");

        return new Locale(defaultValue);
    }

    protected void setLanguage(Context ctx, String code)
    {
        updateResourcesLocale(ctx, new Locale(code));
    }

    protected void saveLanguage(Context ctx, String code)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Language", code);
        editor.apply();
    }

    @SuppressWarnings("deprecation")
    protected Context updateResourcesLocale(Context context, Locale locale)
    {
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            configuration.setLocale(locale);
        } else
        {
            configuration.locale = locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            context = context.createConfigurationContext(configuration);
        } else
        {
            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }
//        configuration.setLayoutDirection(locale);
        return context;
    }

    protected void loadMainActivity()
    {
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(updateResourcesLocale(base, loadLanguage(base)));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
