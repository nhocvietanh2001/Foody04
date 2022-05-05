package hcmute.spkt.phamvietanh19110151.foodyui.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(uphone TEXT primary key, uname TEXT, upass TEXT, uaddress TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertUser(User user) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uphone", user.getPhone());
        contentValues.put("uname", user.getName());
        contentValues.put("upass", user.getPass());
        contentValues.put("uaddress", user.getAddress());
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean updateUser(User user) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uname", user.getName());
        contentValues.put("upass", user.getPass());
        contentValues.put("uaddress", user.getAddress());
        int result = MyDB.update("users", contentValues, "uphone = ?", new String[]{user.getPhone()});
        if (result > 0)
            return true;
        return false;
    }

    public void exec(String sql) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL(sql);
    }

    public Cursor getUserWithPhone(String phone) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("SELECT * FROM users WHERE uphone = ?", new String[] {phone});
    }

    public Boolean checkExistPhone(String phone) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where uphone = ?", new String[]{phone});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkUserAndPass(String phone, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where uphone = ? and upass = ?", new String[]{phone, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
