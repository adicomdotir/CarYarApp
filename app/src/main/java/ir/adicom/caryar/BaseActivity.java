package ir.adicom.caryar;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import ir.adicom.caryar.Utility.ParentActivity;
import ir.adicom.caryar.Utility.PermissionHandler;
import ir.adicom.caryar.models.Car;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;

public class BaseActivity extends ParentActivity {

    private String carName;
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase dbDao;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TextView txtCarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base2);

        String temp[] = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        };
        new PermissionHandler().checkPermission(this, temp, new PermissionHandler.OnPermissionResponse() {
            @Override
            public void onPermissionGranted() {
                init();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(BaseActivity.this, "مجوزها را صادر کنید", Toast.LENGTH_SHORT).show();
            }
        });

        myTest();
    }

    public void myTest() {
        Integer[] numbers = new Integer[] {1, 2, 3, 4, 5};
        Observable<Integer> observable = io.reactivex.Observable.fromArray(numbers);
        observable = observable.map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {
                return integer * integer;
            }
        });
        observable = observable.filter(new Predicate<Integer>() {
            @Override
            public boolean test(@NonNull Integer integer) throws Exception {
                return integer > 10;
            }
        });
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("TAG", "" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void init() {
        daoSession = ((App) getApplication()).getDaoSession();
        if (daoSession.getCarDao().count() == 0) {
            Car car = new Car();
            car.setName("پراید");
            car.setColor("یشمی");
            car.setPlaque("91ه251");
            car.setYear(1384);
            daoSession.getCarDao().insert(car);
            // TODO: Problem here
            SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(),
                    this.MODE_PRIVATE);
            prefs.edit().putLong("CARID", 1).apply();
        }

        SharedPreferences prefs = getSharedPreferences(getApplicationContext().getPackageName(),
                this.MODE_PRIVATE);
        HelperUI.CAR_ID = prefs.getLong("CARID", 0);

        HelperUI.setFont((ViewGroup) findViewById(R.id.base_layout),
                Typeface.createFromAsset(getAssets(), "Vazir_Medium.ttf"));

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fuel_layer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.oil_layer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, EngineOilActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.service_layer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, ServiceActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.report_layer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, ChartActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.car_layer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, CarActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.other_layer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, OtherActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        txtCarName = (TextView) findViewById(R.id.carName);
        getName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getName();
    }

    private void getName() {
        Car tempCar = daoSession.getCarDao().load(HelperUI.CAR_ID);
        carName = tempCar.getName();
        txtCarName.setText(carName);
    }

    public void copyFile() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/ir.adicom.caryar/databases/carhelper-db";
                String backupDBPath = "database_name";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        }
        catch (Exception e) {
            Log.w("Settings Backup", e);
        }
    }
}
