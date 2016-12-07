package ir.adicom.caryar;
import java.util.LinkedList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "CarDB";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE info ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "type TEXT, "+
                "price INTEGER, "+
                "km INTEGER," +
                "date INTEGER )";
 
        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS info");
 
        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------
 
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
 
    private static final String[] COLUMNS = {KEY_ID,KEY_TYPE,KEY_PRICE,KEY_KM};
 
    public void addInfo(CarInfo info){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, info.getType());
        values.put(KEY_PRICE, info.getPrice());
        values.put(KEY_KM, info.getKilometer());
        values.put(KEY_DATE, info.getDate());
 
        // 3. insert
        db.insert(TABLE_CAR, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close(); 
    }
 
    public CarInfo getInfo(int n){

        SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.rawQuery("SELECT * FROM info ", null);
    	c.moveToLast();
    	if(n==1) {
    		if(!c.moveToPrevious())
    			return null;
    	}
    	if(n==2) {
    		if(!c.moveToPrevious())
    			return null;
    		if(!c.moveToPrevious())
    			return null;
    	}
    	int column1 = c.getInt(0);
        String column2 = c.getString(1);
        int column3 = c.getInt(2);
        int column4 = c.getInt(3);
        int c5 = c.getInt(4);
        c.close();
        
        CarInfo i = new CarInfo(column2, column3, column4, c5);
        i.setId(column1);
        
        return i;
    }
 
    // Get All CarInfo
    public List<CarInfo> getAllInfo() {
        List<CarInfo> info = new LinkedList<CarInfo>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_CAR;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        CarInfo ci = null;
        if (cursor.moveToFirst()) {
            do {
                String column2 = cursor.getString(1);
                int column3 = cursor.getInt(2);
                int column4 = cursor.getInt(3);
                int c5 = cursor.getInt(4);
                ci = new CarInfo(column2, column3, column4, c5);
                ci.setId(Integer.parseInt(cursor.getString(0)));
                info.add(ci);
            } while (cursor.moveToNext());
        }
 
        // return 
        return info;
    }
 
     // Updating single
    public int updateInfo(CarInfo linfo) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_PRICE, linfo.getPrice());
        values.put(KEY_TYPE, linfo.getType());
        values.put(KEY_KM, linfo.getKilometer());
        values.put(KEY_DATE, linfo.getDate());
 
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
    public void deleteInfo(CarInfo linfo) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_CAR,
                KEY_ID+" = ?",
                new String[] { String.valueOf(linfo.getId()) });
 
        // 3. close
        db.close();
  
    }
}