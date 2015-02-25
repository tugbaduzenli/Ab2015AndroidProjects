package com.example.tugbasalih.ab2015map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationManager lm;
    LocationListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();


        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);



        if(!isNetwork && !isGPS){ //network gps kapalıysa kişiyi uyar

            new AlertDialog.Builder(this)
                    .setTitle("Lokasyon")
                    .setMessage("Konum ayarlarınız kapalı açılsın mı?")
                    .setPositiveButton("Evet" , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                    }
            }).show();

        }

        Location locGPS = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(locGPS != null){
            Toast.makeText(this, "Konum:" + locGPS.getLatitude()
                    + "-" + locGPS.getLongitude(), Toast.LENGTH_SHORT).show();
        }

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(MapsActivity.this, "Konum Degişti:" + location.getLatitude()
                        + "-" + location.getLongitude(), Toast.LENGTH_SHORT).show();
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
        };


        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,listener);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lm.removeUpdates(listener);
            }
        },30000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                //Toast.makeText(MapsActivity.this,location.getLatitude()+ "-" + location.getLongitude(),Toast.LENGTH_LONG).show();

                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).tilt(65).build();


                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

                mMap.animateCamera(cameraUpdate);

                mMap.setMyLocationEnabled(false);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Anadolu Üniversitesi");
                markerOptions.snippet("Akademik Bilişim 2015");
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
                Marker marker = mMap.addMarker(markerOptions);

                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault()); //adress bilgileri getirmek için yaptık

                try {
                    List<Address> adrList =geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); //1 tane adres vardı

                    StringBuilder builder = new StringBuilder();

                    for(Address adr : adrList){


                        builder.append(adr.getCountryName());

                        if(adr.getAdminArea() != null)
                            builder.append(adr.getAdminArea());
                        if(adr.getSubAdminArea() != null)
                            builder.append(adr.getSubAdminArea());

                        for (int i=0 ; i<adr.getMaxAddressLineIndex() ;i++){
                            builder.append(adr.getAddressLine(i));
                        }
                    }

                  marker.setSnippet(builder.toString()); // yukarıdaki snippet yerine yaptık
                  marker.showInfoWindow();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
