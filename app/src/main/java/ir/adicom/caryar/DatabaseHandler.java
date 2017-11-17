package ir.adicom.caryar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.EngineOilDao;
import ir.adicom.caryar.models.Fuel;
import ir.adicom.caryar.models.FuelDao;

class DatabaseHandler extends SQLiteOpenHelper {
    private Context mContext;
    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "CarDB";
 
    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("TAG", "onCreate");
        String CREATE_BOOK_TABLE = "CREATE TABLE info ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "type TEXT, "+
                "price INTEGER, "+
                "km INTEGER," +
                "date INTEGER," +
                "date2 TEXT)";
        db.execSQL(CREATE_BOOK_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("TAG", "onUpgrade");
        String upgradeQuery = "ALTER TABLE info ADD COLUMN date2 TEXT";
        if (newVersion == 3) {
            db.execSQL(upgradeQuery);
        } else if(newVersion == 4) {
            String createEngineOilTable = "CREATE TABLE IF NOT EXISTS engineoils ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, "+
                    "nowkm INTEGER, "+
                    "maxkm INTEGER," +
                    "price INTEGER," +
                    "date TEXT)";
            db.execSQL(createEngineOilTable);
        }
//        else {
//            db.execSQL("DROP TABLE IF EXISTS info");
//            this.onCreate(db);
//        }
    }

    /**
     * CRUD operations (create "add", read "get", update, delete)
     */
 
    // CarInformations table name
    private static final String TABLE_CAR = "info";
 
    // CarInformations Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_PRICE = "price";
    private static final String KEY_KM = "km";
    private static final String KEY_DATE = "date";
    private static final String KEY_DATE2 = "date2";

    // private static final String[] COLUMNS = {KEY_ID,KEY_TYPE,KEY_PRICE,KEY_KM,KEY_DATE2};

    void addInfo(CarInfo info){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, info.getType());
        values.put(KEY_PRICE, info.getPrice());
        values.put(KEY_KM, info.getKilometer());
        values.put(KEY_DATE, info.getDate());
        values.put(KEY_DATE2, info.getDate2());
 
        // 3. insert
        db.insert(TABLE_CAR, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close(); 
    }
 
//    public CarInfo getInfo(int n){
//        SQLiteDatabase db = this.getReadableDatabase();
//    	Cursor c = db.rawQuery("SELECT * FROM info ", null);
//    	c.moveToLast();
//    	if(n==1) {
//    		if(!c.moveToPrevious())
//    			return null;
//    	}
//    	if(n==2) {
//    		if(!c.moveToPrevious())
//    			return null;
//    		if(!c.moveToPrevious())
//    			return null;
//    	}
//    	int column1 = c.getInt(0);
//        String column2 = c.getString(1);
//        int column3 = c.getInt(2);
//        int column4 = c.getInt(3);
//        int c5 = c.getInt(4);
//        String date = null;
//        try {
//            date = c.getString(5);
//        } catch (Exception e) {
//            Log.e("TAG", e.getMessage());
//        }
//        c.close();
//        CarInfo i = new CarInfo(column2, column3, column4, c5, date);
//        i.setId(column1);
//
//        return i;
//    }
 
    // Get All CarInfo
    List<CarInfo> getAllInfo() {
        List<CarInfo> info = new LinkedList<CarInfo>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_CAR + " ORDER By date ASC";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CarInfo ci;
        if (cursor.moveToFirst()) {
            do {
                String column2 = cursor.getString(1);
                int column3 = cursor.getInt(2);
                int column4 = cursor.getInt(3);
                int c5 = cursor.getInt(4);
                String date = null;
                try {
                    date = cursor.getString(5);
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
                // Log.e("TAG", "Add: Date=" + date);
                ci = new CarInfo(column2, column3, column4, c5, date);
                ci.setId(Integer.parseInt(cursor.getString(0)));
                info.add(ci);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return

//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < info.size(); i++) {
//            sb.append("\n");
//            sb.append(info.get(i).getType() + "##");
//            sb.append(info.get(i).getDate2() + "##");
//            sb.append(info.get(i).getKilometer() + "##");
//            sb.append(info.get(i).getPrice());
//        }
//        writeToFile(sb.toString(), mContext);
        return info;
    }

    Cursor getAll(String type, boolean monthly) {
        String query;
        if (monthly) {
            query = "SELECT substr(date2,1,7) AS mydate, sum(price) FROM info WHERE type LIKE '%" +
                    type + "%' GROUP BY mydate ORDER BY mydate DESC";
        } else {
            query = "SELECT substr(date2,1,4) AS mydate, sum(price) FROM info WHERE type LIKE '%" +
                    type + "%' GROUP BY mydate ORDER BY mydate DESC";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }

    Cursor get(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        db.close();
        return cursor;
    }
 
     // Updating single
    int updateInfo(CarInfo linfo) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_PRICE, linfo.getPrice());
        values.put(KEY_TYPE, linfo.getType());
        values.put(KEY_KM, linfo.getKilometer());
        values.put(KEY_DATE, linfo.getDate());
        values.put(KEY_DATE2, linfo.getDate2());
 
        // 3. updating row
        int i = db.update(TABLE_CAR, //table
                values, // column/value
                KEY_ID + " = " + String.valueOf(linfo.getId()), // selections
                null); //selection args
 
        // 4. close
        db.close();
        return i;
 
    }
 
    // Deleting single
    void deleteInfo(CarInfo linfo) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_CAR,
                KEY_ID + " = ?",
                new String[] { String.valueOf(linfo.getId()) });
 
        // 3. close
        db.close();
  
    }

    int getCount() {
        String query = "SELECT  * FROM " + TABLE_CAR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    private void writeToFile(String data, Context context) {
        final File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM + "/YourFolder/"
        );
        // Make sure the path directory exists.
        if (!path.exists()) {
            // Make it, if it doesn't exit
            path.mkdirs();
        }
        final File file = new File(path, "config.txt");
        // Log.e("TAG", file.getAbsolutePath());
        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void readFromFile(Context context) {
        // Database initalize
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        DaoSession daoSession = daoMaster.newSession();
        FuelDao fuelDao = daoSession.getFuelDao();

        final File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM + "/YourFolder/"
        );
        final File file = new File(path, "config.txt");
        InputStream instream = null;
        try {
            // open the file for reading
            instream = new FileInputStream(file);
            // if file the available for reading
            if (instream != null) {
                // prepare the file for reading
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                // read every line of the file into the line-variable, on line at the time
                do {
                    line = buffreader.readLine();
                    if (line.length() > 0) {
                        String[] temp = line.split("##");
                        Fuel fuel = new Fuel();
                        fuel.setType(temp[0]);
                        fuel.setDate(temp[1]);
                        fuel.setKilometer(Integer.parseInt(temp[2]));
                        fuel.setPrice(Integer.parseInt(temp[3]));
                        fuel.setCarId(1L);
                        fuelDao.insert(fuel);
                        Log.e("TAG", "size: " + fuelDao.count() + "");
                    }
                } while (line != null);
            }
        } catch (Exception ex) {
            Log.e("TAG", ex.getMessage());
            // print stack trace.
        } finally {
            // close the file.
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}