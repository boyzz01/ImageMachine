package com.ardysyahputra.imagemachine.Model;

import java.util.Date;

public class Machine {
    String Id,Name,Type,Last_Maintenance_Date;
    int QR_Code_Number;


    public Machine(String id, String name, String type, String last_Maintenance_Date, int QR_Code_Number) {
        Id = id;
        Name = name;
        Type = type;
        Last_Maintenance_Date = last_Maintenance_Date;
        this.QR_Code_Number = QR_Code_Number;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getQR_Code_Number() {
        return QR_Code_Number;
    }

    public void setQR_Code_Number(int QR_Code_Number) {
        this.QR_Code_Number = QR_Code_Number;
    }

    public String getLast_Maintenance_Date() {
        return Last_Maintenance_Date;
    }

    public void setLast_Maintenance_Date(String last_Maintenance_Date) {
        Last_Maintenance_Date = last_Maintenance_Date;
    }
}
