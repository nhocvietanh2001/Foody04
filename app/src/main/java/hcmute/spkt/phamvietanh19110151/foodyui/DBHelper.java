package hcmute.spkt.phamvietanh19110151.foodyui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(@Nullable Context context) {
        super(context,"Login.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(uphone TEXT primary key, uname TEXT, upass TEXT, uaddress TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {

        MyDB.execSQL("drop Table if exists users");
    }

    public  Boolean insertData(String username, String phone, String password, String address)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uphone",phone);
        contentValues.put("uname",username);
        contentValues.put("upass",password);
        contentValues.put("uaddress",address);
        long result = MyDB.insert("users",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }
    public Boolean checkUser(String phone){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where uphone = ?",new String[] {phone});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    public Boolean checkUserAndPass(String phone, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where uphone = ? and upass = ?", new String[]{phone,password});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }
}
