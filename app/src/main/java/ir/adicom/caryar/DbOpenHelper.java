package ir.adicom.caryar;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.ServiceDao;

/**
 * Created by adicom on 11/13/17.
 */

public class DbOpenHelper extends DaoMaster.OpenHelper {

    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        // Log.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 3:
                ServiceDao.createTable(db, true);
        }
    }
}
