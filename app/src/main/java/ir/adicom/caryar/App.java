package ir.adicom.caryar;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;

/**
 * Created by adicom on 1/11/18.
 */

public class App extends Application {

    public static String FONT_NAME = "Samim.ttf";
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.OpenHelper helper = new ir.adicom.caryar.MyOpenHelper(this, "carhelper-db");
        SQLiteDatabase db = helper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
