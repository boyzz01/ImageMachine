package com.ardysyahputra.imagemachine.Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;

import com.ardysyahputra.imagemachine.Model.ImageModel;
import com.ardysyahputra.imagemachine.Model.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MachineManager";

    private static final String TABLE_NAME = "machine_table";
    private static final String TABLE_IMAGE = "IMAGE";

    private static final String TAG = "DatabaseHelper";


    private static final String ID_MACHINE = "Id_Machine";
    private static final String ID_IMAGE = "Id_Image";
    private static final String NAME = "Name";
    private static final String TYPE = "Type";
    private static final String QRCODE = "QR_Code_Number";
    private static final String LASTMT = "Last_Maintenance_Date";
    private static final String PATH = "IMAGE_PATH";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + ID_MACHINE + " TEXT PRIMARY KEY," + NAME + " TEXT NOT NULL,"
                + TYPE + " TEXT NOT NULL," + QRCODE + " INTEGER NOT NULL,"
                + LASTMT + " TEXT NOT NULL )";

        String createTable2 = "CREATE TABLE IMAGE (Id_Image STRING PRIMARY KEY, Id_Machine TEXT,"
                + PATH + " TEXT," +
                " FOREIGN KEY (Id_Machine) REFERENCES machine_table(Id_Machine))";

        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String Name,String Type,Integer QrCode,String LastMaintenance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String Id = UUID.randomUUID().toString();
        contentValues.put(ID_MACHINE, Id);
        contentValues.put(NAME, Name);
        contentValues.put(TYPE, Type);
        contentValues.put(QRCODE, QrCode);
        contentValues.put(LASTMT, LastMaintenance);

        Log.d(TAG, "addData: Adding " + Id + " Date "+LastMaintenance+"  to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addImage(String idMachine,String path) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String Id = UUID.randomUUID().toString();
        contentValues.put(ID_IMAGE, Id);
        contentValues.put(ID_MACHINE, idMachine);
        contentValues.put(PATH, path);


        long result = db.insert(TABLE_IMAGE, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public List<Machine> getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY NAME ASC,TYPE ASC";
        List<Machine> machineList = new ArrayList<>();
        Machine machine;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                String Id = cursor.getString(cursor.getColumnIndex("Id_Machine"));
                String Name = cursor.getString(cursor.getColumnIndex("Name"));
                String Type = cursor.getString(cursor.getColumnIndex("Type"));
                int QrNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex("QR_Code_Number")));
                String LastMT = cursor.getString(cursor.getColumnIndex("Last_Maintenance_Date"));
                machine = new Machine(Id,Name,Type,LastMT,QrNumber);
                machineList.add(machine);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return machineList;
    }

    public List<Machine> getDataByName(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY NAME ASC";
        List<Machine> machineList = new ArrayList<>();
        Machine machine;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                String Id = cursor.getString(cursor.getColumnIndex("Id_Machine"));
                String Name = cursor.getString(cursor.getColumnIndex("Name"));
                String Type = cursor.getString(cursor.getColumnIndex("Type"));
                int QrNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex("QR_Code_Number")));
                String LastMT = cursor.getString(cursor.getColumnIndex("Last_Maintenance_Date"));
                machine = new Machine(Id,Name,Type,LastMT,QrNumber);
                machineList.add(machine);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return machineList;
    }

    public List<Machine> getDataByType(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY TYPE ASC";
        List<Machine> machineList = new ArrayList<>();
        Machine machine;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                String Id = cursor.getString(cursor.getColumnIndex("Id_Machine"));
                String Name = cursor.getString(cursor.getColumnIndex("Name"));
                String Type = cursor.getString(cursor.getColumnIndex("Type"));
                int QrNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex("QR_Code_Number")));
                String LastMT = cursor.getString(cursor.getColumnIndex("Last_Maintenance_Date"));
                machine = new Machine(Id,Name,Type,LastMT,QrNumber);
                machineList.add(machine);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return machineList;
    }

    public List<Machine> getDataFromId(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + ID_MACHINE + " = "+ " \"" + id  +"\"";
        List<Machine> machineList = new ArrayList<>();
        Machine machine;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                String Id = cursor.getString(cursor.getColumnIndex("Id_Machine"));
                String Name = cursor.getString(cursor.getColumnIndex("Name"));
                String Type = cursor.getString(cursor.getColumnIndex("Type"));
                int QrNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex("QR_Code_Number")));
                String LastMT = cursor.getString(cursor.getColumnIndex("Last_Maintenance_Date"));
                machine = new Machine(Id,Name,Type,LastMT,QrNumber);
                machineList.add(machine);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return machineList;
    }

    public int getImageCount(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM "+TABLE_IMAGE+" WHERE "+ID_MACHINE + " = "+ " \"" + id  +"\"";
        int count;
        Cursor cursor = db.rawQuery(query, null);
        count= cursor.getCount();
        cursor.close();
        return count;
    }

    public ArrayList<ImageModel> getImage(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_IMAGE +" WHERE " + ID_MACHINE + " = "+ " \"" + id  +"\"";
        ArrayList<ImageModel> imageModelList = new ArrayList<>();
        ImageModel imageModel = new ImageModel();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                    String id_image = cursor.getString(cursor.getColumnIndex(ID_IMAGE));
                    String path = cursor.getString(cursor.getColumnIndex(PATH));
                    ImageModel imagemodel = new ImageModel(id_image,id,path);
                    //add in array list
                    imageModelList.add(imagemodel);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return imageModelList;
    }



    /**
     * Returns only the ID that matches the name passed in
     * @param QrCode
     * @return
     */
    public String getItemID(int QrCode){
        SQLiteDatabase db = this.getWritableDatabase();
        String Id="";
        String query = "SELECT " + ID_MACHINE + " FROM " + TABLE_NAME +
                " WHERE " + QRCODE + " = '" + QrCode + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Id = cursor.getString(cursor.getColumnIndex("Id_Machine"));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return Id;
    }



    public void updateData(String id, String name,String type, int qr, String last){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET "
                + NAME + " = '" + name+ "'," + TYPE + " = '" + type+ "',"
                + QRCODE + " = '" + qr+ "'," + LASTMT + " = '" + last +
                "' WHERE " + ID_MACHINE + " = "+ " \"" + id  +"\"";
        Log.d(TAG, "updateName: query: " + query);

        db.execSQL(query);
    }



    /**
     * Delete from database
     * @param id_image
     * @param id_machine
     */
    public void deleteImage(String id_image,String id_machine){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_IMAGE + " WHERE "
                + ID_IMAGE + " = '" + id_image + "'" +
                " AND " + ID_MACHINE + " = '" + id_machine + "'";

        db.execSQL(query);
    }

    public void deleteData(String id_machine){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + ID_MACHINE + " = '" + id_machine + "'";

        db.execSQL(query);
    }


}
