package ir.adicom.caryar;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cengalabs.flatui.FlatUI;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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

        int[] data = new int[3];

        TextView tvFuelCost = (TextView) findViewById(R.id.tvFuelCost);
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT sum(PRICE) FROM FUEL", null);
        cursor.moveToFirst();
        String str = NumberFormat.getNumberInstance(Locale.US).format(cursor.getInt(0));
        data[0] = cursor.getInt(0);
        tvFuelCost.setText("هزینه سوخت : " + str + " تومان");
        TextView tvOilCost = (TextView) findViewById(R.id.tvOilCost);
        cursor = daoSession.getDatabase().rawQuery("SELECT sum(PRICE) FROM ENGINE_OIL", null);
        cursor.moveToFirst();
        str = NumberFormat.getNumberInstance(Locale.US).format(cursor.getInt(0));
        data[1] = cursor.getInt(0);
        tvOilCost.setText("هزینه روغن : " + str + " تومان");
        TextView tvServiceCost = (TextView) findViewById(R.id.tvServiceCost);
        cursor = daoSession.getDatabase().rawQuery("SELECT sum(PART_PRICE),sum(EXPERT_PRICE) FROM SERVICE", null);
        cursor.moveToFirst();
        str = NumberFormat.getNumberInstance(Locale.US).format(cursor.getInt(0) + cursor.getInt(1));
        data[2] = cursor.getInt(0) + cursor.getInt(1);
        tvServiceCost.setText("هزینه تعمیرات: " + str + " تومان");

        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, data[0]),
                new DataPoint(1, data[1]),
                new DataPoint(2, data[2]),
        });
        graph.addSeries(series);
        series.setSpacing(60);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if (data.getX() == 0) return getResources().getColor(R.color.sea_primary);
                if (data.getX() == 1) return getResources().getColor(R.color.candy_primary);
                if (data.getX() == 2) return getResources().getColor(R.color.grape_primary);
                return 0;
            }
        });
    }
}
