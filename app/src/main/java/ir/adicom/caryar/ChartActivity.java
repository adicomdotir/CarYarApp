package ir.adicom.caryar;

import android.app.Activity;
import android.database.Cursor;
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
                Typeface.createFromAsset(getAssets(), App.FONT_NAME));

        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        float[] data = new float[3];

        TextView tvFuelCost = (TextView) findViewById(R.id.tvFuelCost);
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT sum(PRICE) FROM FUEL WHERE CAR_ID=" + HelperUI.CAR_ID, null);
        cursor.moveToFirst();
        String str = NumberFormat.getNumberInstance(Locale.US).format(cursor.getInt(0));
        data[0] = cursor.getInt(0);
        tvFuelCost.setText("هزینه سوخت : " + str + " تومان");
        TextView tvOilCost = (TextView) findViewById(R.id.tvOilCost);
        cursor = daoSession.getDatabase().rawQuery("SELECT sum(PRICE) FROM ENGINE_OIL WHERE CAR_ID=" + HelperUI.CAR_ID, null);
        cursor.moveToFirst();
        str = NumberFormat.getNumberInstance(Locale.US).format(cursor.getInt(0));
        data[1] = cursor.getInt(0);
        tvOilCost.setText("هزینه روغن : " + str + " تومان");
        TextView tvServiceCost = (TextView) findViewById(R.id.tvServiceCost);
        cursor = daoSession.getDatabase().rawQuery("SELECT sum(PART_PRICE),sum(EXPERT_PRICE) FROM SERVICE WHERE CAR_ID=" + HelperUI.CAR_ID, null);
        cursor.moveToFirst();
        str = NumberFormat.getNumberInstance(Locale.US).format(cursor.getInt(0) + cursor.getInt(1));
        data[2] = cursor.getInt(0) + cursor.getInt(1);
        tvServiceCost.setText("هزینه تعمیرات: " + str + " تومان");

        CustomChart customChart = (CustomChart) findViewById(R.id.customChart);
        customChart.setChartValue(
                new int[] {
                        getResources().getColor(R.color.sea_primary),
                        getResources().getColor(R.color.candy_primary),
                        getResources().getColor(R.color.grape_primary)
                }, data
        );
    }
}
