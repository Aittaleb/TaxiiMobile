package com.aittaleb.abdelhamid.taximobile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aittaleb.abdelhamid.taximobile.POJO.Example;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
//import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/*
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
*/
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,NavigationView.OnNavigationItemSelectedListener,GoogleMap.OnMarkerDragListener {

    private static final int REQUEST_CODE_ASK_PERMISSION =10 ;
    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private EditText pickUp=null;

    EditText ShowDistanceDuration;

    double myPower=0;
    ArrayList<Client> listClients = new ArrayList<Client>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MarkerPoints = new ArrayList<>();
        MarkerPoints.add(new LatLng(0,0));

        ShowDistanceDuration = (EditText) findViewById(R.id.comment) ;


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LoadClients();
        CheckPermission();
        /*
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);*/


       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        //autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));



        /*autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                Toast.makeText(MapsActivity.this, description, Toast.LENGTH_SHORT).show();
            }
        });*/
    }



    private Emitter.Listener onSendTest = new Emitter.Listener() {
        String ss="";
        @Override
        public void call(Object... args) {
            //JSONObject data = (JSONObject) args[0];
            //Toast.makeText(getApplicationContext()," Success send Test",Toast.LENGTH_LONG).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //edit.setText("Success!!!!!");
                    Toast.makeText(getApplicationContext(),"Success send Test ",Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    //method of sending message
    public void onSend(View v){
        Socket msocket;
        try {
            msocket = IO.socket("http://10.0.2.2:9080/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //msocket=cApp.getSocket();
        msocket.connect();
        msocket.emit("sendTest","fkg");
        msocket.on("sendTest",onSendTest);
    }






    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.BLUE);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }


    void CheckPermission()
    {
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSION);
                return;
            }

        }
        GetLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_CODE_ASK_PERMISSION:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    GetLocation();//init the context list
                }else
                {
                    Toast.makeText(this,"did not accept location",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    void GetLocation()
    {

        LocationManager locationManager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MylocationListener mylocationListener = new MylocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1,mylocationListener);
        MyThread myThread= new MyThread();
        myThread.start();
    }
    Location oldLocation;




    class MyThread extends Thread
    {
        MyThread()
        {
            oldLocation = new Location("zero");
            oldLocation.setLongitude(0);
            oldLocation.setLatitude(0);

        }
        @Override
        public void run()
        {
            //pickUp= (EditText) findViewById(R.id.pickup);
            while (true)
            {
                //double mylat = mMap.getCameraPosition().target.latitude;
               // double mylang = mMap.getCameraPosition().target.longitude;
                //pickUp.setText(mylat+" , "+mylang);
                if(oldLocation.distanceTo(MylocationListener.location)==0)
                {
                    continue;
                }
                //hna kayna l3ba
                oldLocation=MylocationListener.location;
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // mMap.clear();
                            // Add MyLocation
                            LatLng sydney = new LatLng(MylocationListener.location.getLatitude(),MylocationListener.location.getLongitude());
                            MarkerPoints.set(0,sydney);
                            Toast.makeText(MapsActivity.this,"added position 1 "+MarkerPoints.size(),Toast.LENGTH_SHORT).show();

                            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker on my location")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.taximarker)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
                            //show clients



                            for (int i =0 ; i<listClients.size();i++)
                            {
                                Client client = listClients.get(i);
                                if(client.isCatch==false)
                                {
                                    LatLng clientLocation = new LatLng(client.location.getLatitude(),client.location.getLongitude());
                                    mMap.addMarker(new MarkerOptions().position(clientLocation).title("Marker on my location")
                                            .icon(BitmapDescriptorFactory.fromResource(client.image)).title(client.name).snippet(client.des+" power "+client.power));
                                    if(client.location.distanceTo(MylocationListener.location)<2){
                                        myPower = myPower + client.power;
                                        client.isCatch=true;
                                        listClients.set(i,client);
                                        Toast.makeText(MapsActivity.this,"client attended",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                        }
                    });
                    Thread.sleep(1000);
                }catch (Exception e){
                }
            }
        }
    }
    //MarkerOptions marker = new MarkerOptions().draggable(true).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.flag));
    public void onMapSearch(View view) {

        EditText locationSearch = (EditText) findViewById(R.id.arrival);
        String location = locationSearch.getText().toString();
        List<Address>addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                Toast.makeText(this,"Destination not found",Toast.LENGTH_SHORT).show();

            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            MarkerPoints.add(latLng);
            Toast.makeText(MapsActivity.this,"added position 2 "+MarkerPoints.size(),Toast.LENGTH_SHORT).show();

            mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title(location+" ("+address.getLatitude()+","+address.getLongitude()+")").icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
            //listClients.add(new Client(R.drawable.flag,"Nasser","client2 requesting cab",100,address.getLatitude(), address.getLongitude()));
            //Toast.makeText(MapsActivity.this,"client attended at "+" ("+address.getLatitude()+","+address.getLongitude()+")",Toast.LENGTH_SHORT).show();



            /* if (MarkerPoints.size() > 2) {
                   // MarkerPoints.clear();
                    mMap.clear();
                }*/


            // Checks, whether start and end locations are captured
            if (MarkerPoints.size() >= 2) {
                LatLng origin = MarkerPoints.get(0);
                LatLng dest = MarkerPoints.get(MarkerPoints.size()-1);

                // Getting URL to the Google Directions API
                String url = getUrl(origin, dest);
                Log.d("onMapClick", url.toString());
                FetchUrl FetchUrl = new FetchUrl();

                // Start downloading json data from Google Directions API
                FetchUrl.execute(url);
                //move map camera
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                build_retrofit_and_get_response("driving");

                final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
                if (mapView.getViewTreeObserver().isAlive()) {
                    mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onGlobalLayout() {
                            LatLngBounds.Builder bld = new LatLngBounds.Builder();
                            for (int i = 0; i < MarkerPoints.size(); i++) {
                                LatLng ll = new LatLng(MarkerPoints.get(i).latitude, MarkerPoints.get(i).longitude);
                                bld.include(ll);
                            }
                            LatLngBounds bounds = bld.build();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 70));
                            mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                        }
                    });
                }
            }

        }
    }

    private void build_retrofit_and_get_response(String type) {

        String url = "https://maps.googleapis.com/maps/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMaps service = retrofit.create(RetrofitMaps.class);

        Call<Example> call = service.getDistanceDuration("metric", MarkerPoints.get(0).latitude + "," + MarkerPoints.get(0).longitude,MarkerPoints.get(MarkerPoints.size()-1).latitude + "," + MarkerPoints.get(MarkerPoints.size()-1).longitude, type);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {

                try {

                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                        ShowDistanceDuration.setText("Distance:" + distance + ", Duration:" + time);
                       /* String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = decodePoly(encodedString);
                        line = mMap.addPolyline(new PolylineOptions()
                                .addAll(list)
                                .width(20)
                                .color(Color.RED)
                                .geodesic(true)
                        );*/
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

    }


    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Toast.makeText(this," ("+marker.getPosition().latitude+","+marker.getPosition().longitude+")",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMarkerDragEnd (Marker marker)
    {
        marker.setTitle(" ("+marker.getPosition().latitude+","+marker.getPosition().longitude+")");
        Toast.makeText(this," ("+marker.getPosition().latitude+","+marker.getPosition().longitude+")",Toast.LENGTH_SHORT).show();
    }



    void LoadClients()
    {
        /*listClients.add(new Client(R.drawable.taxicaller,"Abdelhamid","client1 requesting cab",100,31.591455, -8.050502));
        listClients.add(new Client(R.drawable.taxicaller,"Nasser","client2 requesting cab",100,31.592788, -8.051637));
        listClients.add(new Client(R.drawable.taxicaller,"Mustapha","client3 requesting cab",100,31.591690, -8.052118));
        listClients.add(new Client(R.drawable.taxicaller,"Mustapha","client3 requesting cab",100,31.591690, -8.052118) );*/

       /* if(destination!=null)
        {
            listClients.add(destination);
            Toast.makeText(MapsActivity.this,"client attended",Toast.LENGTH_SHORT).show();


        }*/
    }





    //Drawer Menu Methods

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(this,"nav_camera",Toast.LENGTH_SHORT).show();
        }  else if (id == R.id.nav_slideshow) {
            Toast.makeText(this,"nav_slideshow",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_manage) {
            Toast.makeText(this,"nav_manage",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_share) {
            Toast.makeText(this,"nav_share",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            Toast.makeText(this,"nav_send",Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
