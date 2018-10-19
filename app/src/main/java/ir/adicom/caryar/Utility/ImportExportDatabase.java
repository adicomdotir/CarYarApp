package ir.adicom.caryar.Utility;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by adicom on 10/19/18.
 */

public class ImportExportDatabase {
    public static void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//ir.adicom.caryar//databases//carhelper-db";
                String backupDBPath  = "/caryar/DatabaseName";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Log.e("TAG", backupDB.toString());
            }
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    public static void exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath= "//data//ir.adicom.caryar//databases//carhelper-db";
                String backupDBPath  = "/caryar/DatabaseName";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Log.e("TAG", backupDB.toString());
            }
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }
}