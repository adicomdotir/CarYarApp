package ir.adicom.caryar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.InsuranceDao;

/**
 * Created by adicom on 1/11/18.
 */

public class MyOpenHelper extends DaoMaster.DevOpenHelper {

    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        BaseActivity.backupDataBase();
        switch(oldVersion) {
            case 6:
                InsuranceDao.createTable(db, false);
                break;
            default:
                throw new IllegalStateException("unknown oldVersion " + oldVersion);
        }
    }
}
