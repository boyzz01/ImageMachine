package com.ardysyahputra.imagemachine.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ardysyahputra.imagemachine.Adapter.ImageAdapter;
import com.ardysyahputra.imagemachine.Database.DatabaseHelper;
import com.ardysyahputra.imagemachine.GridItemView;
import com.ardysyahputra.imagemachine.Model.ImageModel;
import com.ardysyahputra.imagemachine.R;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Detail_MachineData extends AppCompatActivity {

    Button addImage,edit,delete;
    Boolean deleteMode = false;

    String id,name,type,last;
    TextView idTxt,nameTxt,typeTxt,qrTxt,lastTxt;
    int qrNumber;
    FloatingActionButton fabDelete;
    int selectedIndex;
    ImageAdapter adapter;
    ArrayList<ImageModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__machine_data);



        addImage = findViewById(R.id.addImage);
        idTxt = findViewById(R.id.IdTxt);
        nameTxt = findViewById(R.id.NameTxt);
        typeTxt = findViewById(R.id.TypeTxt);
        qrTxt= findViewById(R.id.qrNumber);
        lastTxt = findViewById(R.id.LastTxt);
        edit = findViewById(R.id.editBtn);
        delete = findViewById(R.id.deleteBtn);


        fabDelete = findViewById(R.id.fabDelete);

        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        type = intent.getStringExtra("type");
        last = intent.getStringExtra("last");
        qrNumber = intent.getIntExtra("number",0);


        idTxt.setText(id);
        nameTxt.setText(name);
        typeTxt.setText(type);
        qrTxt.setText(""+qrNumber);
        lastTxt.setText(last);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Detail_MachineData.this, EditMachineData.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("type",type);
                intent.putExtra("number",qrNumber);
                intent.putExtra("last",last);
                Log.d("tes","id"+id);

                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert(id);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showImage();

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.create(Detail_MachineData.this)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                        .includeVideo(false) // Show video on image picker
                        .multi()
                        .limit(10) // max images can be selected (99 by default)
                        .showCamera(false) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .enableLog(false) // disabling log
                        .start(); // start image picker activity with request code
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper databaseHelper = new DatabaseHelper(Detail_MachineData.this);
                for (int i=0;i<adapter.selectedPositions.size();i++)
                {
                    int position = adapter.selectedPositions.get(i);
                    String idMachine = arrayList.get(position).getId_Machine();
                    String idImage = arrayList.get(position).getId_Image();
                    String path = arrayList.get(position).getPath();
                    databaseHelper.deleteImage(idImage,idMachine);
                    Toast.makeText(Detail_MachineData.this, "Image Deleted", Toast.LENGTH_SHORT).show();

                }
                showImage();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            for (int i=0;i<images.size();i++)
            {

                DatabaseHelper databaseHelper = new DatabaseHelper(Detail_MachineData.this);
                   databaseHelper.addImage(id,images.get(i).getPath());

            }





        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showImage()
    {


        GridView gridView;

        gridView = findViewById(R.id.gridImage);
        arrayList = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(Detail_MachineData.this);
        int count = databaseHelper.getImageCount(id);
        Log.d("tes ","count "+count);
        arrayList = databaseHelper.getImage(id);

        Log.d("tes ","size "+arrayList.size());
        adapter= new ImageAdapter(getApplicationContext(), arrayList);
        gridView.setAdapter(adapter);
        //item click listner
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                selectedIndex  = adapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    ((GridItemView)view).display(false);
                } else {
                    adapter.selectedPositions.add(position);
                    ((GridItemView)view).display(true);
                }


                if (adapter.selectedPositions.size()>0)
                {
                    fabDelete.setVisibility(View.VISIBLE);
                }
                else
                {
                    fabDelete.setVisibility(View.GONE);
                }
                Log.d("tes","tes"+selectedIndex);
                return true;
            }



        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                    if (adapter.selectedPositions.size()>0) {

                        selectedIndex  = adapter.selectedPositions.indexOf(position);
                        if (selectedIndex > -1) {
                            adapter.selectedPositions.remove(selectedIndex);
                            ((GridItemView)view).display(false);
                        } else {
                            adapter.selectedPositions.add(position);
                            ((GridItemView)view).display(true);
                        }

                    }
                    else
                    {
                        Intent intent = new Intent(Detail_MachineData.this, FullImage.class);
                        String path_image = arrayList.get(position).getPath();
                        intent.putExtra("path", path_image);
                        startActivity(intent);
                    }

            }
        });
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
                Intent intent1 = new Intent(Detail_MachineData.this,CodeReader.class);
                startActivity(intent1);
                return true;
            case R.id.MachineData:
                // showProfileView();
                Intent intent2 = new Intent(Detail_MachineData.this,MachineData.class);
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
    protected void onResume() {
        showImage();
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public void showAlert(String id)
    {
        new AlertDialog.Builder(Detail_MachineData.this)
                .setTitle("Delete data")
                .setMessage("Are you sure you want to delete this data?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(Detail_MachineData.this);
                        databaseHelper.deleteData(id);
                        Toast.makeText(Detail_MachineData.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Detail_MachineData.this,MachineData.class);
                        startActivity(intent);
                        finish();
                    }
                })


                .setNeutralButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_warning_red_24dp)
                .show();
    }
}
