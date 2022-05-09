package hcmute.spkt.phamvietanh19110151.foodyui.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Model.CartItem;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Food;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Invoice;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.User;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "User.db";

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

    public void insertInvoice(Invoice invoice){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("iID", invoice.getiID());
        contentValues.put("uID", invoice.getuID());
        contentValues.put("iTotalMoney", invoice.getiTotalMoney());
        contentValues.put("listOrdered", invoice.getListOrdered());
        contentValues.put("iTime", invoice.getiTime());
        MyDB.insert("foods", null, contentValues);
    }

    public void insertCart(String name, int fid) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor items = MyDB.rawQuery("SELECT * FROM cart WHERE uname = ? and fid = ?", new String[] {name, Integer.toString(fid)});
        while (items.moveToNext()) {
            int item = items.getInt(3);
            if (item >= 1) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("uname", name);
                contentValues.put("fid", fid);
                item = item + 1;
                contentValues.put("amount", item);
                MyDB.update("cart", contentValues,"uname = ? and fid = ?", new String[] {name, Integer.toString(fid)});
                return;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("uname", name);
        contentValues.put("fid", fid);
        contentValues.put("amount", 1);
        MyDB.insert("cart", null, contentValues);
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

    public Cursor getCart() {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.rawQuery("Select * from cart", null);
    }

    public void updateAmount(int cid, int amount) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        if (amount == 0) {
            MyDB.delete("cart", "cid = ?", new String[]{Integer.toString(cid)});
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);
        MyDB.update("cart", contentValues, "cid = ?", new String[]{Integer.toString(cid)});
    }
}
