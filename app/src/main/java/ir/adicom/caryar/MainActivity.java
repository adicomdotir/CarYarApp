package ir.adicom.caryar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends Activity {

    private static final String TAG = "TAG";
    public static Typeface custom_font;
    public CarInfo c;
    RadioButton rbtn;
    CustomControl customBtn1, customBtn2, customBtn3;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.DEEP);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        custom_font = Typeface.createFromAsset(getAssets(), "BTraffic.ttf");

        HelperUI.setFont((ViewGroup) getWindow().getDecorView());

        customBtn1 = (CustomControl) findViewById(R.id.custom_btn_1);
        customBtn2 = (CustomControl) findViewById(R.id.custom_btn_2);
        customBtn3 = (CustomControl) findViewById(R.id.custom_btn_3);
        try {
            CalendarTool irDate = new CalendarTool(mYear, mMonth + 1, mDay);
            customBtn1.setNumber(irDate.getIranianYear());
            customBtn1.setMinMax(1390, 1499);
            customBtn1.setLen(4);
            customBtn1.setUp();
            customBtn2.setNumber(irDate.getIranianMonth());
            customBtn2.setMinMax(1, 12);
            customBtn2.setLen(2);
            customBtn2.setUp();
            customBtn3.setNumber(irDate.getIranianDay());
            customBtn3.setMinMax(1, 31);
            customBtn3.setLen(2);
            customBtn3.setUp();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        final DatabaseHandler db = new DatabaseHandler(this);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        final EditText edtKilo = (EditText) findViewById(R.id.editText1);
        final EditText edtprice = (EditText) findViewById(R.id.editText2);
        Button btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setTypeface(custom_font);
        btnInsert.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                rbtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                Calendar calendar = Calendar.getInstance();
                CalendarTool irDate = new CalendarTool();

                mYear = Integer.parseInt(customBtn1.getText());
                mMonth = Integer.parseInt(customBtn2.getText());
                mDay = Integer.parseInt(customBtn3.getText());
                irDate.setIranianDate(mYear, mMonth, mDay);
                calendar.set(irDate.getGregorianYear(),
                        irDate.getGregorianMonth() - 1,
                        irDate.getGregorianDay(), 0, 0, 0);
                long startTime = calendar.getTimeInMillis();
                String strTime = mYear + "/" + String.format(Locale.US, "%02d", mMonth) + "/" + String.format(Locale.US, "%02d", mDay);
                Log.e("MYTAG", "" + startTime);
                if (edtKilo.getText().toString().trim().length() == 0
                        || edtprice.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "هر دو فیلد را پر کنید", Toast.LENGTH_SHORT).show();
                } else {
                    db.addInfo(
                            new CarInfo(rbtn.getText().toString(),
                                    Integer.valueOf(edtprice.getText().toString()),
                                    Integer.valueOf(edtKilo.getText().toString()),
                                    startTime / 1000L, strTime)
                    );
                    edtKilo.setText("");
                    edtprice.setText("");
                    Toast.makeText(getApplicationContext(), "Record Added", Toast.LENGTH_SHORT).show();
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
        Cursor cursor = db.get("SELECT sum(price) FROM info GROUP BY type");
        int benzinCost = cursor.getInt(0);
        cursor.moveToNext();
        int gusCost = cursor.getInt(0);
        String temp = String.format(Locale.US, "کل هزینه %d تومان\nکل هزینه گاز %d تومان\nکل هزینه بنزین %d تومان",
                benzinCost + gusCost, gusCost, benzinCost);
        tvTotal.setText(temp);
        TextView tvTotalKm = (TextView) findViewById(R.id.tv_total_km);
        cursor = db.get("SELECT min(km),max(km) FROM info");
        tvTotalKm.setText(String.format(Locale.US, "کل مسافت طی شده %d کیلومتر", cursor.getInt(1) - cursor.getInt(0)));
    }
}
