package hu.ppke.itk.android.papel1.nature_arm;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Arrays;

import hu.ppke.itk.android.papel1.nature_arm.base.LanguageHelper;
import hu.ppke.itk.android.papel1.nature_arm.data.DAO.factory.AppDatabase;

public class SettingsActivity extends LanguageHelper
{
    ArrayList<String> languageCodes;
    ArrayList<String> languages;

    AutoCompleteTextView setting_language;
    Button eraseDb;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch theme_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        theme_switch = findViewById(R.id.light_dark_switch);
        setting_language = findViewById(R.id.autoCompleteTextView);
        eraseDb = findViewById(R.id.ereaseButton);

        eraseDb.setOnClickListener(v -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        AppDatabase.getDatabase(getApplicationContext()).eraseAll();
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_yes,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_no,
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.erase_alertDialog)
                    .setPositiveButton(R.string.erase_alertDialog_yes, dialogClickListener)
                    .setNegativeButton(R.string.erase_alertDialog_no, dialogClickListener).show();
        });

        setting_language.setOnItemClickListener((parent, view, position, id) -> {

            for (int i = 0; i < languages.size(); i++)
            {
                if (languages.get(i).equals(parent.getItemAtPosition(position).toString()))
                {
                    saveLanguage(getApplicationContext(), languageCodes.get(i));
                    loadMainActivity();
                }
            }
        });

        int nightModeFlags = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.themeSharable), MODE_PRIVATE);
        theme_switch.setChecked(sharedPreferences.getBoolean(getString(R.string.themeValue), nightModeFlags == Configuration.UI_MODE_NIGHT_YES));

        theme_switch.setOnClickListener(v -> {
            if (!theme_switch.isChecked())
            {
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.themeSharable), MODE_PRIVATE).edit();
                editor.putBoolean(getString(R.string.themeValue), false);
                editor.apply();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                theme_switch.setChecked(false);
            } else
            {
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.themeSharable), MODE_PRIVATE).edit();
                editor.putBoolean(getString(R.string.themeValue), true);
                editor.apply();
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                theme_switch.setChecked(true);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        languages = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.languages)));
        languageCodes = new ArrayList<>();

        for (int i = 0; i < languages.size(); i++)
        {
            String[] s = languages.get(i).split(getString(R.string.languageDelimeter));
            languages.set(i, s[1]);
            languageCodes.add(i, s[0]);
        }

        ArrayAdapter<String> languagesAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, languages);
        setting_language.setAdapter(languagesAdapter);
    }
}