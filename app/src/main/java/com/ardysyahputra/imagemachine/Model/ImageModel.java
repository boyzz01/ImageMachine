package com.ardysyahputra.imagemachine.Model;

public class ImageModel {
    String Id_Image,Id_Machine,path;

    public ImageModel() {
    }

    public ImageModel(String id_Image, String id_Machine, String path) {
        Id_Image = id_Image;
        Id_Machine = id_Machine;
        this.path = path;
    }

    public String getId_Image() {
        return Id_Image;
    }

    public void setId_Image(String id_Image) {
        Id_Image = id_Image;
    }

    public String getId_Machine() {
        return Id_Machine;
    }

    public void setId_Machine(String id_Machine) {
        Id_Machine = id_Machine;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
