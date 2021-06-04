package com.example.restaurantapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.restaurantapp.loctemplate.Locations;
import com.example.restaurantapp.utilities.Util;
import java.util.ArrayList;

public class Databasehelper extends SQLiteOpenHelper
{
    public Databasehelper(@Nullable Context context)
    {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String CREATE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " (" + Util.LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Util.LOCATION_NAME + " TEXT, " + Util.LATITUDE + " TEXT, " + Util.LONGITUDE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ind1)
    {
        String DROP_LOCATION_TABLE = "DROP TABLE IF EXISTS " + Util.TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_LOCATION_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long addLocation(Locations location)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.LOCATION_NAME, location.getName());
            contentValues.put(Util.LATITUDE, location.getLatitude());
            contentValues.put(Util.LONGITUDE, location.getLongitude());
            long rowid = db.insert(Util.TABLE_NAME, null, contentValues);
            db.close();
            return rowid;
        }
        catch (Exception e)
        {
            Log.e("Error", "Exception cannot add location " + e.getMessage());
            return 0;
        }
    }
    public ArrayList<Locations> getLocation()
    {
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + Util.TABLE_NAME;
            Cursor cursor = db.rawQuery(query, new String[]{});
            ArrayList<Locations> locations = new ArrayList<>();
            if (cursor.moveToFirst())
            {
                do {
                    int locid = cursor.getInt(cursor.getColumnIndex(Util.LOCATION_ID));
                    String locationname = cursor.getString(cursor.getColumnIndex(Util.LOCATION_NAME));
                    String latitude = cursor.getString(cursor.getColumnIndex(Util.LATITUDE));
                    String longitude = cursor.getString(cursor.getColumnIndex(Util.LONGITUDE));
                    Locations location = new Locations(locid,locationname,latitude,longitude);
                    locations.add(location);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return locations;
        }
        catch (Exception e)
        {
            Log.e("Error", "Exception sql array failed " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
