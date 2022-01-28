package be.ehb.CovidRecords.ui;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import be.ehb.CovidRecords.MainActivity;
import be.ehb.CovidRecords.R;
import be.ehb.CovidRecords.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private GoogleMap myMap;

    private double la = 50.8476, lo = 4.3572;

    private OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            myMap = googleMap;
            Geocoder geocoder = new Geocoder(getActivity());
            Bundle bundle = getArguments();
            String Location = bundle.getString("location");
            try {


                List<Address> addresses =geocoder.getFromLocationName(Location, 10);
                if(addresses.size() != 0){
                    Address address = addresses.get(0);

                    la = address.getLatitude();
                    lo = address.getLongitude();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            LatLng coordinator = new LatLng(la,lo);
            CameraUpdate movetolocatin = CameraUpdateFactory.newLatLngZoom(coordinator, 12);
            myMap.animateCamera(movetolocatin);
            myMap.getUiSettings().setZoomControlsEnabled(true);
            myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        }
    };
    private MapView mapView;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);




        mapView.getMapAsync(onMapReadyCallback);

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        binding = null;
    }

}