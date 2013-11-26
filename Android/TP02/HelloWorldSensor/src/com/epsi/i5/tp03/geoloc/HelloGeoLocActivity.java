package com.epsi.i5.tp03.geoloc;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.epsi.i5.tp03.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HelloGeoLocActivity extends Activity implements LocationListener {

	private static final String TAG = "HelloGeoLocActivity";

	private Geocoder geocoder = null;
	private LocationManager service = null;
	private GoogleMap map = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_map);

		service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}
		geocoder = new Geocoder(this, Locale.getDefault());
		// Get a handle to the Map Fragment
		initializeMap();
	}

	private void initializeMap() {
		if (map == null) {
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			if (map != null) {
				LatLng sydney = new LatLng(-33.867, 151.206);

				map.setMyLocationEnabled(true);
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

				map.addMarker(new MarkerOptions().title("Sydney").snippet("The most populous city in Australia.").position(sydney));
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		service.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initializeMap();
		service.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
		Location location = service.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null) {
			onLocationChanged(location);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		if (map != null) {
			LatLng myPlace = new LatLng(location.getLatitude(), location.getLongitude());

			map.setMyLocationEnabled(true);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 13));
			List<Address> addresses;
			MarkerOptions marker = new MarkerOptions().position(myPlace);
			try {
				addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				if (addresses != null && addresses.size() > 0) {
					Address myAdress = addresses.get(0);
					marker.title(myAdress.getLocality()).snippet(myAdress.toString());
				}
			} catch (IOException e) {
				Log.e(TAG, "Error getting location");
			}
			map.addMarker(marker);

		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
