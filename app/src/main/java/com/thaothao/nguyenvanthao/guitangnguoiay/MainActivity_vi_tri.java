package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity_vi_tri extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private Location location;

    // Đối tượng tương tác với Google API
    private GoogleApiClient gac;

    // Hiển thị vị trí
    private TextView tvLocation;
    private Button btnChiaSe;
    double latitude=0;
    double longitude=0;
    private DatabaseReference mDatabase;
    public ArrayList<String> al;
    public static String toaDO="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_your_location);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        btnChiaSe=(Button) findViewById( R.id.btnDispLocation );
        // Trước tiên chúng ta cần phải kiểm tra play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
        al=new ArrayList<>(  );

        mDatabase.child( "ban be"+k[0].toString().trim() ).addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final userOnline banBe= dataSnapshot.getValue(userOnline.class);
                al.add( banBe.getEmail().toString() );
                //   String [] kx=banBe.getEmail().toString().trim().split( "@" );

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
        btnChiaSe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latitude>1 &&longitude>1){
                    toaDO=latitude+", "+longitude;
                    final String [] k=DangKiDangNhapOnlineActivity.tk.toString().trim().split( "@" );
//                    mDatabase.child( "ViTri"+k[0] ).addValueEventListener( new ValueEventListener() {
//                     @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                     mDatabase.child( "ViTri"+k[0]  ).setValue( tvLocation.getText().toString() );
//                    }
//
//                    @Override
//                     public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                    } );
                    mDatabase.child( "ViTri"+k[0]  ).push().setValue( new ViTri( DangKiDangNhapOnlineActivity.tk.toString().trim(),tvLocation.getText().toString().trim(),1 ) );
                    Intent intent=new Intent( MainActivity_vi_tri.this,DanhSachVitri.class );

                    intent.putStringArrayListExtra( "banBe",al );
                    startActivity( intent );


                }else {
                    Toast.makeText(MainActivity_vi_tri.this, "chưa bật vị trí hoặc thiết bị không phù hợp.", Toast.LENGTH_LONG).show();
                }
            }
        } );
    }

    public void dispLocation(View view) {
        getLocation();
    }

    /**
     * Phương thức này dùng để hiển thị trên UI
     * */
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Kiểm tra quyền hạn
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            location = LocationServices.FusedLocationApi.getLastLocation(gac);


            if (location != null) {
              latitude = location.getLatitude();
              longitude = location.getLongitude();
                // Hiển thị
                tvLocation.setText(latitude + ", " + longitude);
            } else {
                tvLocation.setText("(Không thể hiển thị vị trí. " +
                        "Bạn đã kích hoạt location trên thiết bị chưa?)");
            }
        }
    }

    /**
     * Tạo đối tượng google api client
     * */
    protected synchronized void buildGoogleApiClient() {
        if (gac == null) {
            gac = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi( LocationServices.API).build();
        }
    }

    /**
     * Phương thức kiểm chứng google play services trên thiết bị
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1000).show();
            } else {
                Toast.makeText(this, "Thiết bị này không hỗ trợ.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Đã kết nối với google api, lấy vị trí
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        gac.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Lỗi kết nối: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    protected void onStart() {
        gac.connect();
        super.onStart();
    }

    protected void onStop() {
        gac.disconnect();
        super.onStop();
    }
}