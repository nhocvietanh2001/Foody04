package hcmute.spkt.phamvietanh19110151.foodyui.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hcmute.spkt.phamvietanh19110151.foodyui.Model.Food;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Restaurant;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Voucher;

public class DBFoody extends SQLiteOpenHelper {

    public static final String DBNAME = "Foody.db";

    public DBFoody(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void insertVoucher(int id, Voucher voucher) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("vid", id);
        contentValues.put("vname", voucher.getName());
        contentValues.put("vtype", voucher.getType());
        contentValues.put("vamount", voucher.getAmount());
        MyDB.insert("vouchers", null, contentValues);
    }

    public void updateVoucher(int id, Voucher voucher) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("vname", voucher.getName());
        contentValues.put("vtype", voucher.getType());
        contentValues.put("vamount", voucher.getAmount());
        MyDB.update("vouchers", contentValues, "vid = ?", new String[] {Integer.toString(id)});
    }

    public void insertRestaurant(Restaurant restaurant) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("rid", restaurant.getId());
        contentValues.put("rname", restaurant.getName());
        contentValues.put("raddress", restaurant.getAddress());
        contentValues.put("rphone", restaurant.getPhone());
        MyDB.insert("restaurants",null, contentValues);
    }

    public void insertFood(int fid, Food food, int rid) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fname", food.getName());
        contentValues.put("fcategory", food.getCategory());
        contentValues.put("fimage", food.getImage());
        contentValues.put("fid", fid);
        contentValues.put("rid", rid);
        MyDB.insert("foods",null, contentValues);
    }

    public Cursor getVouchers() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("SELECT * FROM vouchers", null);
    }

    public Cursor getRestaurants() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("SELECT * FROM restaurants", null);
    }

    public Cursor getFoodsAtRestaurant(int rid) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("SELECT * FROM foods WHERE rid = ?",new String[]{Integer.toString(rid)});
    }

    public void exec(String sql) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL(sql);
    }
}
