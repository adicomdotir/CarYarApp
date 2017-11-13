package ir.adicom.caryar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cengalabs.flatui.FlatUI;

import java.util.Calendar;
import java.util.Locale;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.Fuel;

public class MainActivity extends Activity {

    private static final String TAG = "TAG";
    public Typeface custom_font;
    public CarInfo c;
    RadioButton rbtn;
    CustomControl customBtn1, customBtn2, customBtn3;
    private int mYear;
    private int mMonth;
    private int mDay;
    CalendarTool irDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.SEA);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        irDate = new CalendarTool(mYear, mMonth + 1, mDay);

        final Button btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setText(irDate.getIranianDate());
        final AppDialog appDialog = new AppDialog(this, irDate);
        btnDate.setOnClickListener(new OnClickListener() {
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
        custom_font = Typeface.createFromAsset(getAssets(), "Vazir_Light.ttf");

        HelperUI.setFont((ViewGroup) getWindow().getDecorView(), custom_font);

        final DatabaseHandler db = new DatabaseHandler(this);
        // db.readFromFile(getApplicationContext());
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        final DaoSession daoSession = daoMaster.newSession();

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        final EditText edtKilo = (EditText) findViewById(R.id.editText1);
        final EditText edtprice = (EditText) findViewById(R.id.editText2);
        Button btnInsert = (Button) findViewById(R.id.btn_insert);
        btnInsert.setTypeface(custom_font);
        btnInsert.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                rbtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                Calendar calendar = Calendar.getInstance();
                CalendarTool irDate = new CalendarTool();

                // 1396/1/23
                // 012345678
                String date = btnDate.getText().toString();
                mYear = Integer.parseInt(date.substring(0,4));
                int firstIndex = date.indexOf('/');
                int lastIndex = date.lastIndexOf('/');
                mMonth = Integer.parseInt(date.substring(firstIndex + 1,lastIndex));
                mDay = Integer.parseInt(date.substring(lastIndex + 1));
                irDate.setIranianDate(mYear, mMonth, mDay);
                calendar.set(irDate.getGregorianYear(),
                        irDate.getGregorianMonth() - 1,
                        irDate.getGregorianDay(), 0, 0, 0);
                long startTime = calendar.getTimeInMillis();
                String strTime = mYear + "/" + String.format(Locale.US, "%02d", mMonth) + "/" + String.format(Locale.US, "%02d", mDay);
                // Log.e("MYTAG", "" + startTime);
                if (edtKilo.getText().toString().trim().length() == 0
                        || edtprice.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "هر دو فیلد را پر کنید", Toast.LENGTH_SHORT).show();
                } else {
                    Fuel fuel = new Fuel();
                    fuel.setDate(btnDate.getText().toString());
                    fuel.setKilometer(Integer.valueOf(edtKilo.getText().toString()));
                    fuel.setPrice(Integer.valueOf(edtprice.getText().toString()));
                    fuel.setType(rbtn.getText().toString());
                    daoSession.getFuelDao().insert(fuel);
//                    db.addInfo(
//                            new CarInfo(rbtn.getText().toString(),
//                                    Integer.valueOf(edtprice.getText().toString()),
//                                    Integer.valueOf(edtKilo.getText().toString()),
//                                    startTime / 1000L, strTime)
//                    );
                    edtKilo.setText("");
                    edtprice.setText("");
                    Toast.makeText(getApplicationContext(), "ثبت گردید.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // FIXME: 12/7/16 WTF?
        // TODO: 12/7/16 WTF?
        final Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
        Button btn1 = (Button) findViewById(R.id.button2);
        btn1.setTypeface(custom_font);
        btn1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(intent);
            }
        });
        Button btnReport = (Button) findViewById(R.id.btnReport);
        btnReport.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FilterActivity.class));
            }
        });
        TextView tvTotal = (TextView) findViewById(R.id.tv_total);
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT sum(price) FROM FUEL GROUP BY type", null);
        //Cursor cursor = db.get("SELECT sum(price) FROM info GROUP BY type");
        cursor.moveToFirst();
        int benzinCost = cursor.getInt(0);
        cursor.moveToNext();
        int gusCost = cursor.getInt(0);
        String temp = String.format(Locale.US, "%d تومان", benzinCost + gusCost);
        tvTotal.setText(temp);
        TextView tvTotalBenz = (TextView) findViewById(R.id.tv_total_benz);
        temp = String.format(Locale.US, "%d تومان", benzinCost);
        tvTotalBenz.setText(temp);
        TextView tvTotalGus = (TextView) findViewById(R.id.tv_total_gus);
        temp = String.format(Locale.US, "%d تومان", gusCost);
        tvTotalGus.setText(temp);
        TextView tvTotalKm = (TextView) findViewById(R.id.tv_total_km);
        // cursor = db.get("SELECT min(km),max(km) FROM info");
        cursor = daoSession.getDatabase().rawQuery("SELECT min(KILOMETER),max(KILOMETER) FROM FUEL", null);
        cursor.moveToFirst();
        tvTotalKm.setText(String.format(Locale.US, "%d کیلومتر", cursor.getInt(1) - cursor.getInt(0)));
    }

    @Override
    protected void onStop() {
        super.onStop();
        final DatabaseHandler db = new DatabaseHandler(this);
        db.getAllInfo();
    }
}