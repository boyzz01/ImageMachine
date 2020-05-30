package com.ardysyahputra.imagemachine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ardysyahputra.imagemachine.Adapter.MachineAdapter;
import com.ardysyahputra.imagemachine.Database.DatabaseHelper;
import com.ardysyahputra.imagemachine.Model.Machine;
import com.ardysyahputra.imagemachine.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MachineData extends AppCompatActivity {

    RecyclerView recyclerView;
    public List<Machine> Machinelist;
    MachineAdapter machineAdapter;

    String[] listSort;

    FloatingActionButton floatingActionButton;
    ExtendedFloatingActionButton Sort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_data);
        listSort = getResources().getStringArray(R.array.sort_items);
        floatingActionButton = findViewById(R.id.floating_action_button);
        Sort = findViewById(R.id.sort_fab);

        recyclerView = findViewById(R.id.ListMachine);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MachineData.this));

        getData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MachineData.this,AddMachine.class);
                startActivity(intent);
            }
        });

        Sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MachineData.this);
                mBuilder.setTitle("Sort By");
                mBuilder.setSingleChoiceItems(listSort, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      //  mResult.setText(listSort[i]);

                        SortData(i);
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });




    }

    private  void SortData(int sortBy)
    {
        Machinelist = new ArrayList<>();
        Machine machine;
        Cursor cursor;

        DatabaseHelper databaseHelper = new DatabaseHelper(MachineData.this);
        if (sortBy==0)
        {
           Machinelist  = databaseHelper.getDataByName();
        }
        else
        {
            Machinelist  = databaseHelper.getDataByType();
        }

        machineAdapter = new MachineAdapter(MachineData.this,Machinelist);
        recyclerView.setAdapter(machineAdapter);
    }

    @Override
    protected void onResume() {
        getData();
        super.onResume();
    }

    public void getData() {

        Machinelist = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(MachineData.this);
        Machinelist = databaseHelper.getData();
        machineAdapter = new MachineAdapter(MachineData.this,Machinelist);
        recyclerView.setAdapter(machineAdapter);

        if (Machinelist.size()==0)
        {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.codeReader:
                Intent intent = new Intent(MachineData.this,CodeReader.class);
                startActivity(intent);
                return true;
            case R.id.MachineData:
               // showProfileView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press Back Again To Leave", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
