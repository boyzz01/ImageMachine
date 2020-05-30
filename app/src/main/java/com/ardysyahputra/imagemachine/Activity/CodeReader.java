package com.ardysyahputra.imagemachine.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ardysyahputra.imagemachine.Database.DatabaseHelper;
import com.ardysyahputra.imagemachine.Model.Machine;
import com.ardysyahputra.imagemachine.R;
import com.google.zxing.Result;


import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CodeReader extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView QrScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},
                    50); }

        QrScanner = new ZXingScannerView(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(QrScanner);


    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
        int QrNumber = Integer.parseInt(rawResult.toString());
        DatabaseHelper databaseHelper = new DatabaseHelper(CodeReader.this);
        String id=databaseHelper.getItemID(QrNumber);

        List<Machine> machineList;
        machineList= new ArrayList<>();
        machineList = databaseHelper.getDataFromId(id);

        Intent intent = new Intent(CodeReader.this, Detail_MachineData.class);
        intent.putExtra("id",machineList.get(0).getId());
        intent.putExtra("name",machineList.get(0).getName());
        intent.putExtra("type",machineList.get(0).getType());
        intent.putExtra("number",machineList.get(0).getQR_Code_Number());
        intent.putExtra("last",machineList.get(0).getLast_Maintenance_Date());
        startActivity(intent);
        finish();
        QrScanner.stopCamera();



//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setMessage(rawResult.getText());
//        AlertDialog alert1 = builder.create();
//        alert1.show();

//        QrScanner.resumeCameraPreview(this);
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
                Intent intent1 = new Intent(this,CodeReader.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.MachineData:
                // showProfileView();
                Intent intent2 = new Intent(this,MachineData.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        QrScanner.setResultHandler(this);
        QrScanner.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        QrScanner.stopCamera();
    }

}
