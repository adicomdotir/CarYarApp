package ir.adicom.caryar;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ir.adicom.caryar.models.Car;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base2);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        final DaoSession daoSession = daoMaster.newSession();
        if (daoSession.getCarDao().count() == 0) {
            Car car = new Car();
            car.setName("پراید");
            car.setColor("یشمی");
            car.setPlaque("91ه251");
            car.setYear(1384);
            daoSession.getCarDao().insert(car);
        }

        HelperUI.setFont((ViewGroup) findViewById(R.id.base_layout),
                Typeface.createFromAsset(getAssets(), "Vazir.ttf"));

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
    }
}
