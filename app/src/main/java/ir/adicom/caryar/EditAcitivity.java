package ir.adicom.caryar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class EditAcitivity extends Activity {

    private static final String TAG = "TAG";
    private EditText edtCost, edtKM;
    private CustomControl customBtn1, customBtn2, customBtn3;
    private RadioGroup radioGroup;
    private RadioButton rBtn01, rBtn02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        MainActivity.custom_font = Typeface.createFromAsset(getAssets(), "BTraffic.ttf");
        HelperUI.setFont((ViewGroup) getWindow().getDecorView());

        edtCost = (EditText) findViewById(R.id.edtCost);
        edtKM = (EditText) findViewById(R.id.edtKm);

        customBtn1 = (CustomControl) findViewById(R.id.custom_btn_1);
        customBtn2 = (CustomControl) findViewById(R.id.custom_btn_2);
        customBtn3 = (CustomControl) findViewById(R.id.custom_btn_3);

        radioGroup = (RadioGroup) findViewById(R.id.rgEdit);
        rBtn01 = (RadioButton) findViewById(R.id.radiobutton01);
        rBtn02 = (RadioButton) findViewById(R.id.radiobutton02);

        final int index = getIntent().getExtras().getInt("INDEX");

        final DatabaseHandler db = new DatabaseHandler(this);
        final List<CarInfo> list = db.getAllInfo();
        if(list != null) {
            edtCost.setText("" + list.get(index).getPrice());
            edtKM.setText("" + list.get(index).getKilometer());
            Log.e("TAG", "onCreate: " + list.get(0).getDate());
            Calendar mydate = Calendar.getInstance();
            mydate.setTimeInMillis(list.get(index).getDate() * 1000);
            CalendarTool irDate = new CalendarTool();
            irDate.setGregorianDate(
                    mydate.get(Calendar.YEAR),
                    mydate.get(Calendar.MONTH) + 1,
                    mydate.get(Calendar.DAY_OF_MONTH)
            );
            try {
                customBtn1.setNumber(irDate.getIranianYear());
                customBtn1.setMinMax(1395, 1499);
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
                Log.e(TAG, "" + e.getMessage());
            }
            if(list.get(index).getType().equals("گاز")) {
                rBtn01.setChecked(true);
            } else rBtn02.setChecked(true);
        }

        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rbtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                Log.e(TAG, "" + rbtn.getText());
                Calendar calendar = Calendar.getInstance();

                int mYear = Integer.parseInt(customBtn1.getText());
                int mMonth = Integer.parseInt(customBtn2.getText());
                int mDay = Integer.parseInt(customBtn3.getText());
                CalendarTool irDate = new CalendarTool();
                irDate.setIranianDate(mYear, mMonth, mDay);
                calendar.set(irDate.getGregorianYear(),
                        irDate.getGregorianMonth()-1,
                        irDate.getGregorianDay(), 0, 0, 0);
                long startTime = calendar.getTimeInMillis();
                if(edtKM.getText().toString().trim().length() == 0
                        || edtCost.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "هر دو فیلد را پر کنید", Toast.LENGTH_SHORT).show();
                } else {
                    int log = db.updateInfo(
                            new CarInfo(
                                    list.get(index).getId(),
                                    rbtn.getText().toString(),
                                    Integer.valueOf(edtCost.getText().toString()),
                                    Integer.valueOf(edtKM.getText().toString()),
                                    startTime/1000L)
                    );
                    Log.e(TAG, "onClick: " + log);
                    edtKM.setText("");
                    edtCost.setText("");
                    finish();
                }
            }
        });
    }
}