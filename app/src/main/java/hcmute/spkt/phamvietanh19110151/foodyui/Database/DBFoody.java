package hcmute.spkt.phamvietanh19110151.foodyui.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import hcmute.spkt.phamvietanh19110151.foodyui.Model.Food;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Restaurant;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Voucher;

public class DBFoody extends SQLiteOpenHelper {

    private Context context;
    public static final String DBNAME = "Foody.db";
    public static String DB_PATH = "";
    private SQLiteDatabase database;

    public DBFoody(Context context) {
        super(context, DBNAME, null, 1);
        this.context = context;
        if (Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    }

    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }

    private boolean checkDatabase() {
        SQLiteDatabase temp = null;
        try {
            String path = DB_PATH + DBNAME;
            temp = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception exception) {

        }
        if (temp != null)
            temp.close();
        return temp != null ? true : false;
    }

    public void copyDatabase() {
        try {
            InputStream myInput = context.getAssets().open(DBNAME);
            String outputFileName = DB_PATH + DBNAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDatabase() {
        String path = DB_PATH + DBNAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void createDatabase() {
        boolean isDBExist = checkDatabase();
        if (isDBExist) {

        }
        else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        MyDB.update("vouchers", contentValues, "vid = ?", new String[]{Integer.toString(id)});
    }

    public void insertRestaurant(Restaurant restaurant) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("rid", restaurant.getId());
        contentValues.put("rname", restaurant.getName());
        contentValues.put("raddress", restaurant.getAddress());
        contentValues.put("rphone", restaurant.getPhone());
        MyDB.insert("restaurants", null, contentValues);
    }

    public void insertFood(int fid, Food food, int rid) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fname", food.getName());
        contentValues.put("fcategory", food.getCategory());
        contentValues.put("fprice", food.getPrice());
        contentValues.put("fimage", food.getImage());
        contentValues.put("fid", fid);
        contentValues.put("rid", rid);
        MyDB.insert("foods", null, contentValues);
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
        return MyDB.rawQuery("SELECT * FROM foods WHERE rid = ?", new String[]{Integer.toString(rid)});
    }

    public Cursor getFoodsWithCategory(String category) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("SELECT * FROM foods WHERE fcategory = ?", new String[]{category});
    }

    public Cursor getFoods() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("SELECT * FROM foods", null);
    }

    public Cursor getRestaurantByID(int id){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("SELECT rname FROM restaurants WHERE rid = ?", new String[]{Integer.toString(id)});
    }

    public void exec(String sql) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL(sql);
    }
}
