package edu.sampleproject.movielens.pojo;

public enum MovieFormat {
    TWOD("2D"),
    THREED("3D"),
    FOURD("4DX"),
    IMAX("IMAX");

    String screenOpt;

    MovieFormat(String screenOpt){
        this.screenOpt = screenOpt;
    }

}
