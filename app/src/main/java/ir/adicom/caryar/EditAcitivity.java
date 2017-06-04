package ir.adicom.caryar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Locale;

public class EditAcitivity extends Activity {

    private static final String TAG = "TAG";
    private EditText edtCost, edtKM;
    private CustomControl customBtn1, customBtn2, customBtn3;
    private RadioGroup radioGroup;

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
        RadioButton rBtn01 = (RadioButton) findViewById(R.id.radiobutton01);
        RadioButton rBtn02 = (RadioButton) findViewById(R.id.radiobutton02);

        final int index = getIntent().getExtras().getInt("INDEX");

        final DatabaseHandler db = new DatabaseHandler(this);
        final List<CarInfo> list = db.getAllInfo();
        if(list != null) {
            edtCost.setText(String.format(Locale.US,"%d", list.get(index).getPrice()));
            edtKM.setText(String.format(Locale.US, "%d", list.get(index).getKilometer()));
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
                String strTime = String.format(Locale.US, "%d/%02d/%02d", mYear, mMonth, mDay);
                if(edtKM.getText().toString().trim().length() == 0
                        || edtCost.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "هر دو فیلد را پر کنید", Toast.LENGTH_SHORT).show();
                } else {
                    int log = db.updateInfo(
                            new CarInfo(
                                    list != null ? list.get(index).getId() : 0,
                                    rbtn.getText().toString(),
                                    Integer.valueOf(edtCost.getText().toString()),
                                    Integer.valueOf(edtKM.getText().toString()),
                                    startTime/1000L, strTime)
                    );
                    Log.e(TAG, "onClick: " + log);
                    edtKM.setText("");
                    edtCost.setText("");
                    finish();
                }
            }
        });

        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAcitivity.this);
                builder.setMessage("آیا مطمئن هستید که می خواهید این رکورد حذف کنید؟")
                        .setCancelable(false)
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.deleteInfo(new CarInfo(list != null ? list.get(index).getId() : 0, null, 0, 0, 0, null));
                                EditAcitivity.this.finish();
                            }
                        })
                        .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }
}
