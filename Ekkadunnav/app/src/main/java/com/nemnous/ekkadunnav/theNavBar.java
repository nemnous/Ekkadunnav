package com.nemnous.ekkadunnav;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class theNavBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private GoogleMap mMap;
    FirebaseAuth auth;
    GoogleApiClient client;
    LocationRequest request;
    LatLng latLngLocation;
    DatabaseReference reference;
    FirebaseUser user;
    String userName;
    String userEmail;
    TextView nameTxt;
    TextView emailTxt;
    String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        setContentView(R.layout.activity_the_nav_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        nameTxt = header.findViewById(R.id.HeaderNameId);
        emailTxt = header.findViewById(R.id.HeaderEmailId);
        userId = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                userEmail = dataSnapshot.child(user.getUid()).child("email").getValue(String.class);
                nameTxt.setText(userName);
                emailTxt.setText(userEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        getMenuInflater().inflate(R.menu.the_nav_bar, menu);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        https://developers.google.com/android/reference/com/google/android/gms/common/api/GoogleApiClient
        client = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
        client.connect();
//
//        // Add a marker in Sydney and move the camera

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//
        if (id == R.id.nav_logout) {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                auth.signOut();
                finish();
                Intent intent = new Intent(theNavBar.this, MainActivity.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String mess = "My Location Shared through App Ekkadunnav :\n " + "https://www.google.com/maps/@" + latLngLocation.latitude + "," + latLngLocation.longitude + ",17z";
            intent.putExtra(Intent.EXTRA_TEXT, mess );
            startActivity(intent.createChooser(intent, "Share Location using:"));
        } else if(id == R.id.nav_join) {
            Intent intent = new Intent(this, joinGang.class);
            startActivity(intent);
        }
// else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest().create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //requests for every 3 seconds
        request.setInterval(10000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if(location == null) {
            Toast.makeText(getApplicationContext(), "Something wrong retrieving location", Toast.LENGTH_SHORT).show();
        } else {
            final MarkerOptions option = new MarkerOptions();
            final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

            Query query = rootRef.child("Users").child(userId).child("myCircle").orderByValue();
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        for(final DataSnapshot circleData: dataSnapshot.getChildren()) {

                            Log.d("TAG", circleData.toString());
                            Query Userquery = rootRef.child("Users");
                            mMap.clear();
                                Userquery.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnap) {
                                        if(dataSnap.exists()) {
                                            for(DataSnapshot allUserSnap : dataSnap.getChildren()) {
                                                Log.d("All Users", allUserSnap.toString());
                                                if (circleData.getKey().equals(allUserSnap.getKey())) {

                                                    Double  latitude = Double.parseDouble(allUserSnap.child("lat").getValue().toString());
                                                    Double longitude = Double.parseDouble(allUserSnap.child("lon").getValue().toString());
                                                    LatLng latlongLocation = new LatLng(latitude, longitude);

                                                    option.position(latlongLocation);
                                                    option.title(allUserSnap.child("name").getValue().toString());
                                                    mMap.addMarker(option);
//                                                    Double latti = Double.parseDouble(rootRef.child("Users").child(userId).child("myCircle").child(userId).child("Lat").ge);
                                                    Log.d("Latti", allUserSnap.child("name").getValue().toString() + " lat " + allUserSnap.child("lat").getValue().toString() + "long " + allUserSnap.child("lon").getValue().toString());
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            latLngLocation = new LatLng(location.getLatitude(), location.getLongitude());
//            MarkerOptions option = new MarkerOptions();
//            option.position(latLngLocation);
//            option.title("Current Location");
////            mMap.clear();
//            mMap.addMarker(option);
//            LatLng sydney = new LatLng(-34, 151);


            rootRef.child("Users").child(userId).child("lat").setValue(latLngLocation.latitude);
            rootRef.child("Users").child(userId).child("lon").setValue(latLngLocation.longitude);
            rootRef.child("Users").child(userId).child("myCircle").child(userId).child("Lat").setValue(latLngLocation.latitude);
            rootRef.child("Users").child(userId).child("myCircle").child(userId).child("Lon").setValue(latLngLocation.longitude);

//
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngLocation));


        }
    }
}
