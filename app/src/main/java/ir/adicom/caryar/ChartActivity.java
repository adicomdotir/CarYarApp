package ir.adicom.caryar;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cengalabs.flatui.FlatUI;

import java.text.NumberFormat;
import java.util.Locale;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;

public class ChartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.CANDY);
        setContentView(R.layout.activity_chart);

        HelperUI.setFont((ViewGroup) findViewById(R.id.base_layout),
                Typeface.createFromAsset(getAssets(), "Vazir.ttf"));

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "carhelper-db", null);
        DaoSession daoSession = new DaoMaster(helper.getWritableDatabase()).newSession();

        TextView tvFuelCost = (TextView) findViewById(R.id.tvFuelCost);
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT sum(PRICE) FROM FUEL", null);
        cursor.moveToFirst();
        String str = NumberFormat.getNumberInstance(Locale.US).format(cursor.getInt(0));
        tvFuelCost.setText("کل هزینه : " + str + " تومان");
        TextView tvOilCost = (TextView) findViewById(R.id.tvOilCost);
        cursor = daoSession.getDatabase().rawQuery("SELECT sum(PRICE) FROM ENGINE_OIL", null);
        cursor.moveToFirst();
        str = NumberFormat.getNumberInstance(Locale.US).format(cursor.getInt(0));
        tvOilCost.setText("کل هزینه : " + str + " تومان");
        TextView tvServiceCost = (TextView) findViewById(R.id.tvServiceCost);
        cursor = daoSession.getDatabase().rawQuery("SELECT sum(PART_PRICE),sum(EXPERT_PRICE) FROM SERVICE", null);
        cursor.moveToFirst();
        str = NumberFormat.getNumberInstance(Locale.US).format(cursor.getInt(0) + cursor.getInt(1));
        tvServiceCost.setText("کل هزینه : " + str + " تومان");
    }
}
