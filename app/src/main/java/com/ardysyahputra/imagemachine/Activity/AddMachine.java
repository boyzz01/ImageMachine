package com.ardysyahputra.imagemachine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ardysyahputra.imagemachine.Database.DatabaseHelper;
import com.ardysyahputra.imagemachine.Model.Machine;
import com.ardysyahputra.imagemachine.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMachine extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    TextInputLayout LastMaintenance,MachineName,MachineType,MachineQrCode;

    String  Name,Type,Date;
    Integer QrCode;
    Button Add ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_machine);

        LastMaintenance = findViewById(R.id.LastMaintenance_Input);
        MachineName = findViewById(R.id.MachineName_Input);
        MachineType = findViewById(R.id.MachineType_Input);
        MachineQrCode = findViewById(R.id.MachineQrNumber_Input);




        Add = findViewById(R.id.Add_Data);


        LastMaintenance.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddMachine.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = MachineName.getEditText().getText().toString();
                Type = MachineType.getEditText().getText().toString();
                QrCode = Integer.parseInt(MachineQrCode.getEditText().getText().toString());
                Date = LastMaintenance.getEditText().getText().toString();
                Log.d("tes","dateee "+Date);

                DatabaseHelper databaseHelper = new DatabaseHelper(AddMachine.this);
                databaseHelper.addData(Name,Type,QrCode,Date);
                Toast.makeText(AddMachine.this, "Data Added", Toast.LENGTH_SHORT).show();

            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        LastMaintenance.getEditText().setText(sdf.format(myCalendar.getTime()));
    }

}
