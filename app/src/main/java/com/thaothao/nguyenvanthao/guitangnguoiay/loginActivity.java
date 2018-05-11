package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class loginActivity extends AppCompatActivity {
EditText user, password;
Button ok;
LinearLayout manHinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        anhXa();
        manHinh.setBackgroundResource( R.drawable.logindangnhap );
        ok.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user1=user.getText().toString().trim();
                String password1=password.getText().toString().trim();
                String chuoi=user1+" "+password1;
                if(user1.length()>0&&password1.length()>0){
                    Intent intent=new Intent(  );
                    intent.putExtra( "user_password",chuoi );
                    setResult( RESULT_OK,intent );
                    finish();
                }
            }
        } );
    }
    private void anhXa(){
        manHinh=(LinearLayout) findViewById( R.id.manHinhLogin ) ;
        user=(EditText) findViewById( R.id.user );
        password=(EditText) findViewById( R.id.password );
        ok=(Button) findViewById( R.id.btnOK );
    }
}
