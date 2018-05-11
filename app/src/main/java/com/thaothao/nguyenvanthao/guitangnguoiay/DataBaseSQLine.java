package com.thaothao.nguyenvanthao.guitangnguoiay;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by NguyenVanThao on 2/12/2018.
 */

//truy van ko tao ket qua: nhu tao bang, insert,delete,them,xoa,sua

public class DataBaseSQLine extends SQLiteOpenHelper {
    public DataBaseSQLine(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {//tham so t1:, tham so thu 2:ten database, tham so thu 3 la con tro duyet trong database , tham so con lai la version

        super( context, name, factory, version );
    }

    public DataBaseSQLine(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super( context, name, factory, version, errorHandler );
    }
    public void QueryData(String sql){
        SQLiteDatabase database=getWritableDatabase();//ghi vao co so du lieu cua minh
        database.execSQL( sql );
    }
    //truy van tra ket qua
    public Cursor getData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery( sql,null );
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    public void INSERT_hinhAnh(String ten, String moTa, byte [] hinhAnh){
       SQLiteDatabase database=getWritableDatabase();
       String sql="INSERT INTO hinh VALUES(null, ?, ?, ?)";
        SQLiteStatement statement=database.compileStatement( sql );
        statement.clearBindings();
        statement.bindString( 1,ten );
        statement.bindString( 2,moTa );
        statement.bindBlob( 3,hinhAnh );
        statement.executeInsert();
    }
    public void INSERT_hinhAnhDaiDien( byte [] hinhAnh){
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO hinhDaiDien VALUES(null, ?)";
        SQLiteStatement statement=database.compileStatement( sql );
        statement.clearBindings();

        statement.bindBlob( 1,hinhAnh );
        statement.executeInsert();
    }
    public void INSERT_hinhNen( byte [] hinhAnh){
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO hinhNen VALUES(null, ?)";
        SQLiteStatement statement=database.compileStatement( sql );
        statement.clearBindings();

        statement.bindBlob( 1,hinhAnh );
        statement.executeInsert();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
