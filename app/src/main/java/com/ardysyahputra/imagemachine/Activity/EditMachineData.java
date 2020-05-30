package com.ardysyahputra.imagemachine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ardysyahputra.imagemachine.Database.DatabaseHelper;
import com.ardysyahputra.imagemachine.Model.Machine;
import com.ardysyahputra.imagemachine.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditMachineData extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    TextInputLayout LastMaintenance,MachineName,MachineType,MachineQrCode;
    String  Name,Type,Date,Id;
    Button Edit ;
    Integer QrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_machine_data);

        LastMaintenance = findViewById(R.id.LastMaintenance_Input);
        MachineName = findViewById(R.id.MachineName_Input);
        MachineType = findViewById(R.id.MachineType_Input);
        MachineQrCode = findViewById(R.id.MachineQrNumber_Input);
        Edit = findViewById(R.id.Edit_Data);

        Intent intent = getIntent();
        Id = intent.getStringExtra("id");
        Name = intent.getStringExtra("name");
        Type = intent.getStringExtra("type");
        Date = intent.getStringExtra("last");
        QrCode = intent.getIntExtra("number",0);

        MachineName.getEditText().setText(Name);
        MachineType.getEditText().setText(Type);
        MachineQrCode.getEditText().setText(""+QrCode);
        LastMaintenance.getEditText().setText(Date);


        LastMaintenance.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditMachineData.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name = MachineName.getEditText().getText().toString();
                Type = MachineType.getEditText().getText().toString();
                QrCode = Integer.parseInt(MachineQrCode.getEditText().getText().toString());
                Date = LastMaintenance.getEditText().getText().toString();
                Log.d("tes","dateee "+Date);

                DatabaseHelper databaseHelper = new DatabaseHelper(EditMachineData.this);
                databaseHelper.updateData(Id,Name,Type,QrCode,Date);
                Toast.makeText(EditMachineData.this, "Edit Sucsess", Toast.LENGTH_SHORT).show();

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
