package ir.adicom.caryar;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cengalabs.flatui.FlatUI;

import java.util.Calendar;
import java.util.List;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.EngineOil;
import ir.adicom.caryar.models.EngineOilDao;

public class EngineOilActivity extends Activity {

    private Calendar calendar;
    private CalendarTool irDate;
    private EditText edtTitle, edtKm, edtKmMax, edtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.CANDY);
        setContentView(R.layout.activity_engine_oil);

        // Set font All activity element
        HelperUI.setFont((ViewGroup) findViewById(R.id.base_layout),
                Typeface.createFromAsset(getAssets(), "Yekan.ttf"));

        // Database initalize
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        DaoSession daoSession = daoMaster.newSession();
        final EngineOilDao engineOilDao = daoSession.getEngineOilDao();

        calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        irDate = new CalendarTool(mYear, mMonth + 1, mDay);
        final Button btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setText(irDate.getIranianDate());
        final AppDialog appDialog = new AppDialog(this, irDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDialog.show();
            }
        });
        appDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                AppDialog appDialog1 = (AppDialog) dialogInterface;
                btnDate.setText(appDialog1.date);
            }
        });

        edtKm = (EditText) findViewById(R.id.edtKm);
        edtKmMax = (EditText) findViewById(R.id.edtKmMax);
        edtPrice = (EditText) findViewById(R.id.edtCost);
        edtTitle = (EditText) findViewById(R.id.edtTitle);

        Button btnInsert = (Button) findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EngineOil engineOil = new EngineOil();
                engineOil.setDate(btnDate.getText().toString());
                engineOil.setName(edtTitle.getText().toString());
                engineOil.setMaxKilometer(Integer.parseInt(edtKmMax.getText().toString()));
                engineOil.setNowKilometer(Integer.parseInt(edtKm.getText().toString()));
                engineOilDao.insert(engineOil);
                List<EngineOil> list = engineOilDao.loadAll();
                for (EngineOil e : list) {
                    Log.e("TAG", "" + e.getId());
                }
            }
        });
    }
}
