package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class DanhSachVitri extends AppCompatActivity {
public Toolbar toolbar;
private ArrayList<userOnline> DSBanBe;
private ArrayList<ViTri> DSViTri;
private DatabaseReference mDatabase;
private listAdapterDanhSachViTri adapterDanhSachViTri;
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_danh_sach_vitri );
        toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        Intent intent = getIntent();
        ArrayList<String> list = intent.getStringArrayListExtra( "banBe" );
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) findViewById( R.id.ListViTri );
        DSViTri = new ArrayList<>();
        adapterDanhSachViTri = new listAdapterDanhSachViTri( this, R.layout.item_list_vi_tri_ban_be, DSViTri );
        listView.setAdapter( adapterDanhSachViTri );
        Toast.makeText( this, "kich thuoc chuoi la: " + list.size(), Toast.LENGTH_LONG ).show();


        for (int i=0;i<list.size();i++) {

            String[] k = list.get( i ).toString().trim().split( "@" );
            DSViTri.add( i,new ViTri( list.get( i ).toString(),"Người này chưa chia sẻ vị trí",1 ) );
            final int f=i;
            mDatabase.child( "ViTri" + k[0].toString().trim() ).addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final ViTri viTri = dataSnapshot.getValue( ViTri.class );
                    DSViTri.set( f,new ViTri(viTri.getEmail().toString(),viTri.getToaDo().toString(),1  ) );

                    adapterDanhSachViTri.notifyDataSetChanged();
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
        }
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (DSViTri.get( i ).getToaDo().toString().trim().equals( "Người này chưa chia sẻ vị trí" )!=true){
                    Intent intent=new Intent( DanhSachVitri.this,MapsActivity_1.class );
                    intent.putExtra( "du lieu",DSViTri.get( i ).getToaDo().toString().trim() );
                    startActivity( intent );
                }else {
                    Toast.makeText( DanhSachVitri.this,"người này không chia sẻ vị trí",Toast.LENGTH_SHORT ).show();
                }

            }
        } );
    }




}
//    LocationManager locationManager = (LocationManager) MainActivity_vi_tri.this.getSystemService(LOCATION_SERVICE);
//    boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // get location from Network Provider
//             if (isNetworkEnabled) { locationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 1000, 10, (LocationListener) this );
//                     location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); }