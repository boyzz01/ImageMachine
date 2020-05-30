package com.ardysyahputra.imagemachine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

                Date = LastMaintenance.getEditText().getText().toString();


                if ( (Name.isEmpty()) || (Type.isEmpty()) || (MachineQrCode.getEditText().getText().toString().isEmpty()) || Date.isEmpty() )
                {
                    if (Name.isEmpty())
                    {
                        Toast.makeText(AddMachine.this, "Name Cannot Empty", Toast.LENGTH_SHORT).show();
                        MachineName.getEditText().requestFocus();
                    }
                    if (Type.isEmpty())
                    {
                        Toast.makeText(AddMachine.this, "Type Cannot Empty", Toast.LENGTH_SHORT).show();
                        MachineType.getEditText().requestFocus();
                    }
                    if (MachineQrCode.getEditText().getText().toString().isEmpty())
                    {
                        Toast.makeText(AddMachine.this, "Qr Code Number Cannot Empty", Toast.LENGTH_SHORT).show();
                        MachineQrCode.getEditText().requestFocus();
                    }
                    if (Date.isEmpty())
                    {
                        Toast.makeText(AddMachine.this, "Date Cannot Empty", Toast.LENGTH_SHORT).show();
                        new DatePickerDialog(AddMachine.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                }
                else
                {
                    QrCode = Integer.parseInt(MachineQrCode.getEditText().getText().toString());
                    DatabaseHelper databaseHelper = new DatabaseHelper(AddMachine.this);
                    databaseHelper.addData(Name,Type,QrCode,Date);
                    Toast.makeText(AddMachine.this, "Data Added", Toast.LENGTH_SHORT).show();
                    MachineName.getEditText().setText("");
                    MachineType.getEditText().setText("");
                    MachineQrCode.getEditText().setText("");
                    LastMaintenance.getEditText().setText("");
                    hideSoftKeyboard(AddMachine.this);
                }



            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
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
