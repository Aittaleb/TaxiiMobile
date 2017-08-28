package com.aittaleb.abdelhamid.taximobile;

import android.location.Location;

/**
 * Created by abdelhamid on 14/05/17.
 */

public class Client {
    public int image;
    public String name;
    public String des ;
    public double power;
    public boolean isCatch;
    public Location location;
    Client(int image,String name,String des,double power,double lat,double log)
    {
        this.image=image;
        this.name=name;
        this.des=des;
        this.power=power;
        this.isCatch=false;
        location=new Location(name);
        location.setLatitude(lat);
        location.setLongitude(log);
    }


}
