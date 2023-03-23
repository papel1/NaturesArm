package hu.ppke.itk.android.papel1.nature_arm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import hu.ppke.itk.android.papel1.nature_arm.adapters.VerticalListAdapter;
import hu.ppke.itk.android.papel1.nature_arm.base.LanguageHelper;
import hu.ppke.itk.android.papel1.nature_arm.data.DAO.PlantDAO;
import hu.ppke.itk.android.papel1.nature_arm.data.DAO.factory.AppDatabase;
import hu.ppke.itk.android.papel1.nature_arm.data.model.Plant;

public class PlantListActivity extends LanguageHelper
{
    RecyclerView recyclerView;
    ImageButton addImageButton;
    SearchView searchView;
    VerticalListAdapter plantListAdapter;

    ArrayList<Plant> plantArrayList = new ArrayList<>();
    MyReceiver receiver;
    private PlantDAO plantDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantlist);

        IntentFilter plantListIntent = new IntentFilter(getString(R.string.plantListIntent));
        receiver = new MyReceiver();
        registerReceiver(receiver, plantListIntent);
        plantDB = AppDatabase.getDatabase(getApplicationContext());

        recyclerView = findViewById(R.id.plantsRecyclerView);
        addImageButton = findViewById(R.id.addImageButton);
        searchView = findViewById(R.id.searchView);

        refreshAdapter();
        recyclerView.setAdapter(plantListAdapter);

        searchView.setActivated(true);
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                plantListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        addImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), NewPlantActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    void refreshAdapter()
    {
        plantArrayList = new ArrayList<>(Arrays.asList(plantDB.selectAllPlants()));

        plantListAdapter = new VerticalListAdapter(this, plantArrayList);
        recyclerView.setAdapter(plantListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private class MyReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            refreshAdapter();
        }
    }
}
