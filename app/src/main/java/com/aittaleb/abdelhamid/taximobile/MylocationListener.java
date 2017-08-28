package com.aittaleb.abdelhamid.taximobile;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by abdelhamid on 14/05/17.
 */

public class MylocationListener implements LocationListener {
    public static Location location;
    MylocationListener()
    {
        location = new Location("zero");
        location.setLongitude(0);
        location.setLatitude(0);
    }
    @Override
    public void onLocationChanged(Location location) {
        this.location=location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
