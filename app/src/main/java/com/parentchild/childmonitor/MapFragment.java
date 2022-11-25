package com.parentchild.childmonitor;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
    String username;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    double lon, lat;

    MapFragment(String username){
        this.username = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        getLocation();

        return  v;
    }

    public void getLocation(){
        ref.child(auth.getUid()).child("Childrens").child(username).child("Locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot s: snapshot.getChildren()){
                    lat = Double.parseDouble(s.child("latitude").getValue().toString());
                    lon = Double.parseDouble(s.child("longitude").getValue().toString());
                }
                if(lon == 0.0 || lat == 0.0){
                    Toast.makeText(getContext(), "No location available", Toast.LENGTH_SHORT).show();
                }
                else{
                    SupportMapFragment frag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map );
                    frag.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            MarkerOptions options = new MarkerOptions();
                            options.position(new LatLng(lat, lon));

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
                            googleMap.addMarker(options);

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}