package ir.adicom.caryar;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.Fuel;
import ir.adicom.caryar.models.FuelDao;

public class ReportActivity extends Activity {
    private ListView listView;
    private List<Fuel> fuels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        listView = (ListView) findViewById(R.id.listView1);
        init();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ReportActivity.this, EditAcitivity.class);
                intent.putExtra("INDEX", fuels.get(i).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        DaoSession daoSession = daoMaster.newSession();
        FuelDao fuelDao = daoSession.getFuelDao();
        // List<Fuel> fuels = fuelDao.loadAll();
        fuels = fuelDao.queryBuilder().orderDesc(FuelDao.Properties.Id).list();

        String[] arr = new String[fuels.size()];
        if (fuels != null) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = "تاریخ :‌ " + fuels.get(i).getDate() + "   " +
                        "نوع سوخت : " + fuels.get(i).getType() + "\n" +
                        "هزینه : " + NumberFormat.getNumberInstance(Locale.US).format(fuels.get(i).getPrice()) + " تومان\n" +
                        "کیلومتر : " + fuels.get(i).getKilometer();
            }
        }
        MyAdapter myAdapter = new MyAdapter(this, arr);
        listView.setAdapter(myAdapter);
    }
}
