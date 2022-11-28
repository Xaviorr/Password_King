package com.onemorelvl.passwordking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {
    private ArrayList<PasswordKingModel> mPasswordKingModels = new ArrayList<>();
    private final String TAG = "MainActivity";
    private PasswordKingAdapter adapter;
    private AccountViewModel mAccountViewModel;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.d(TAG, "onActivityResult: ");
                if (result.getResultCode() == 1) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        setData(intent);
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView);

        mAccountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        mAccountViewModel.getAllAccounts().observe(this, new Observer<List<PasswordKingModel>>() {
            @Override
            public void onChanged(List<PasswordKingModel> passwordKingModels) {
                adapter.updateList((ArrayList<PasswordKingModel>) passwordKingModels);
                mPasswordKingModels = (ArrayList<PasswordKingModel>) passwordKingModels;
                Log.d(TAG, "Livedata onChanged: ");
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mAccountViewModel.delete(adapter.getAccountAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        Intent intent = new Intent(this, DataEntry.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivityResultLauncher.launch(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        MenuItem sortAsc = menu.findItem(R.id.sort_ASC);
        MenuItem sortDsc = menu.findItem(R.id.sort_DSC);
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
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_ASC:
                Toast.makeText(this, "Show Accounts Sorted in Ascending Order", Toast.LENGTH_SHORT).show();
                //mAccountViewModel.getAllAccountsAsc();
                return true;
            case R.id.sort_DSC:
                Toast.makeText(this, "Show Accounts Sorted in Descending Order", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void filter(String text) {
        ArrayList<PasswordKingModel> filteredList = new ArrayList<>();

        for (PasswordKingModel item : mPasswordKingModels) {
            if (item.getCompanyName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Items Found", Toast.LENGTH_SHORT).show();
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
        intent1.putExtra("ID", accountInfo.getId());
        intent1.putExtra("CompanyName", accountInfo.getCompanyName());
        intent1.putExtra("UserName", accountInfo.getUserName());
        intent1.putExtra("Password", accountInfo.getPassword());
        mActivityResultLauncher.launch(intent1);
    }

    private void setData(Intent intent) {
        String companyName = intent.getStringExtra("CompanyName");
        String userName = intent.getStringExtra("UserName");
        String password = intent.getStringExtra("Password");
        if (companyName.trim().isEmpty() || userName.trim().isEmpty() || password.trim().isEmpty()) {
            Toast.makeText(this, "Save Failed. The account is missing info.  Please check it and enter missing info.", Toast.LENGTH_LONG).show();
            return;
        }
        PasswordKingModel account = new PasswordKingModel(companyName.charAt(0), companyName, userName, password);

        if (intent.hasExtra("ID")) {
            int id = intent.getIntExtra("ID", -1);
            if (id >= 0) {
                account.setId(id);
                mAccountViewModel.update(account);
                Log.d(TAG, "setData: account updated " + id);
            }
        } else {
            mAccountViewModel.insert(account);
        }
    }
}