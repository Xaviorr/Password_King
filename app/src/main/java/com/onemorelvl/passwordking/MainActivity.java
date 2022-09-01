package com.onemorelvl.passwordking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemClickListener{
    private ArrayList<PasswordKingModel> mPasswordKingModels = new ArrayList<>();
    private String TAG = "MainActivity";
    private PasswordKingAdapter adapter;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: ");
                    if (result.getResultCode() == 1) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            setData(intent);
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        Intent intent = new Intent(this, DataEntry.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivityResultLauncher.launch(intent);

            }
        });

        initAccountInfo();
        initRecyclerView(recyclerView);

    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Enter Text to Search");
        searchView.setIconifiedByDefault(false);
        searchView.requestFocusFromTouch();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                adapter.updateList(mPasswordKingModels);
                searchView.setQuery("", true);
                InputMethodManager inputMethodManager
                        = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);

            }
        });

        return true;
    }





    private void filter(String text) {
        ArrayList<PasswordKingModel> filteredList = new ArrayList<>();

        for (PasswordKingModel item : mPasswordKingModels) {
            if (item.getCompanyName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Items Found", Toast.LENGTH_LONG).show();
        } else {
            adapter.updateList(filteredList);
        }
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        adapter = new PasswordKingAdapter(this, mPasswordKingModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent1 = new Intent(this, DataEntry.class);
        PasswordKingModel accountInfo = adapter.getPasswordKingModels().get(position);
        intent1.putExtra("ID", position);
        intent1.putExtra("CompanyName", accountInfo.getCompanyName());
        intent1.putExtra("UserName", accountInfo.getUserName());
        intent1.putExtra("Password", accountInfo.getPassword());
        mActivityResultLauncher.launch(intent1);

    }

    private void setData(Intent intent) {
        String companyName = intent.getStringExtra("CompanyName");
        String userName = intent.getStringExtra("UserName");
        String password = intent.getStringExtra("Password");

        if (intent.hasExtra("ID")){
            int id = intent.getIntExtra("ID", -1);
            if (id >= 0) {
                mPasswordKingModels.remove(id);
                mPasswordKingModels.add(id, new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, companyName, userName, password));
            }
        }else {
            mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, companyName, userName, password));
        }
        adapter.notifyDataSetChanged();

    }


    private void initAccountInfo() {
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Discord", "franzz@charter.net", "Password"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Pandora", "FStanley", "Password1"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Disney+", "franst2b@gmail.com", "Password2"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "BattleNet", "Xaviorr", "Password3"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Discover Fran", "FStanley", "Password4"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Discover Amy", "FSYanley2b", "Password5"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Discover ERS", "ERSResources", "Password6"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Bank Of America", "AMyst2b", "Password7"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Capital One", "Fstanley", "Password8"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "Web First", "Stanley", "Password9"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "SCU", "Stanley", "Password10"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "ULine", "fran.stanley@hologic.com", "Password11"));
        mPasswordKingModels.add(new PasswordKingModel(R.drawable.ic_baseline_account_circle_48, "NetFlix", "franzz@charter.net", "Password12"));

    }
}