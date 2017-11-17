package ir.adicom.caryar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Locale;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.Fuel;

public class EditAcitivity extends Activity {

    private static final String TAG = "TAG";
    private EditText edtCost, edtKM;
    private CustomControl customBtn1, customBtn2, customBtn3;
    private RadioGroup radioGroup;
    private int mYear;
    private int mMonth;
    private int mDay;
    CalendarTool irDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        irDate = new CalendarTool(mYear, mMonth + 1, mDay);

        final Button btnDate = (Button) findViewById(R.id.btnDate);
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

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Vazir_Light.ttf");
        HelperUI.setFont((ViewGroup) getWindow().getDecorView(), custom_font);

        edtCost = (EditText) findViewById(R.id.edtCost);
        edtCost.addTextChangedListener(new CustomTextWacher(edtCost));
        edtKM = (EditText) findViewById(R.id.edtKm);

        customBtn1 = (CustomControl) findViewById(R.id.custom_btn_1);
        customBtn2 = (CustomControl) findViewById(R.id.custom_btn_2);
        customBtn3 = (CustomControl) findViewById(R.id.custom_btn_3);

        radioGroup = (RadioGroup) findViewById(R.id.rgEdit);
        RadioButton rBtn01 = (RadioButton) findViewById(R.id.radiobutton01);
        RadioButton rBtn02 = (RadioButton) findViewById(R.id.radiobutton02);

        final Long index = getIntent().getExtras().getLong("INDEX");

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        final DaoSession daoSession = daoMaster.newSession();
        final Fuel tempFuel = daoSession.getFuelDao().load(index);

        if (tempFuel != null) {
            edtCost.setText(String.format(Locale.US,"%d", tempFuel.getPrice()));
            edtKM.setText(String.format(Locale.US, "%d", tempFuel.getKilometer()));
            btnDate.setText(tempFuel.getDate());
            try {
                customBtn1.setNumber(Integer.parseInt(tempFuel.getDate().substring(0,4)));
                customBtn1.setMinMax(1390, 1499);
                customBtn1.setLen(4);
                customBtn1.setUp();
                customBtn2.setNumber(Integer.parseInt(tempFuel.getDate().substring(4,6)));
                customBtn2.setMinMax(1, 12);
                customBtn2.setLen(2);
                customBtn2.setUp();
                customBtn3.setNumber(Integer.parseInt(tempFuel.getDate().substring(6)));
                customBtn3.setMinMax(1, 31);
                customBtn3.setLen(2);
                customBtn3.setUp();
            } catch (Exception e) {
                Log.e(TAG, "" + e.getMessage());
            }
            if(tempFuel.getType().equals("گاز")) {
                rBtn01.setChecked(true);
            } else {
                rBtn02.setChecked(true);
            }
        }

//        final DatabaseHandler db = new DatabaseHandler(this);
//        final List<CarInfo> list = db.getAllInfo();
//        if(list != null) {
//            edtCost.setText(String.format(Locale.US,"%d", list.get(index).getPrice()));
//            edtKM.setText(String.format(Locale.US, "%d", list.get(index).getKilometer()));
//            Log.e("TAG", "onCreate: " + list.get(0).getDate());
//            Calendar mydate = Calendar.getInstance();
//            mydate.setTimeInMillis(list.get(index).getDate() * 1000);
//            CalendarTool irDate = new CalendarTool();
//            irDate.setGregorianDate(
//                    mydate.get(Calendar.YEAR),
//                    mydate.get(Calendar.MONTH) + 1,
//                    mydate.get(Calendar.DAY_OF_MONTH)
//            );
//            try {
//                customBtn1.setNumber(irDate.getIranianYear());
//                customBtn1.setMinMax(1390, 1499);
//                customBtn1.setLen(4);
//                customBtn1.setUp();
//                customBtn2.setNumber(irDate.getIranianMonth());
//                customBtn2.setMinMax(1, 12);
//                customBtn2.setLen(2);
//                customBtn2.setUp();
//                customBtn3.setNumber(irDate.getIranianDay());
//                customBtn3.setMinMax(1, 31);
//                customBtn3.setLen(2);
//                customBtn3.setUp();
//            } catch (Exception e) {
//                Log.e(TAG, "" + e.getMessage());
//            }
//            if(list.get(index).getType().equals("گاز")) {
//                rBtn01.setChecked(true);
//            } else rBtn02.setChecked(true);
//        }

        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rbtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
//                Calendar calendar = Calendar.getInstance();
//                int mYear = Integer.parseInt(customBtn1.getText());
//                int mMonth = Integer.parseInt(customBtn2.getText());
//                int mDay = Integer.parseInt(customBtn3.getText());
//                CalendarTool irDate = new CalendarTool();
//                irDate.setIranianDate(mYear, mMonth, mDay);
//                calendar.set(irDate.getGregorianYear(),
//                        irDate.getGregorianMonth()-1,
//                        irDate.getGregorianDay(), 0, 0, 0);
//                long startTime = calendar.getTimeInMillis();
//                String strTime = String.format(Locale.US, "%d/%02d/%02d", mYear, mMonth, mDay);
                if(edtKM.getText().toString().trim().length() == 0
                        || edtCost.getText().toString().trim().length() == 0 ) {
                    Toast.makeText(getApplicationContext(), "هر دو فیلد را پر کنید", Toast.LENGTH_SHORT).show();
                } else {
//                    int log = db.updateInfo(
//                            new CarInfo(
//                                    list != null ? list.get(index).getId() : 0,
//                                    rbtn.getText().toString(),
//                                    Integer.valueOf(edtCost.getText().toString()),
//                                    Integer.valueOf(edtKM.getText().toString()),
//                                    startTime/1000L, strTime)
//                    );
                    tempFuel.setType(rbtn.getText().toString());
                    tempFuel.setDate(btnDate.getText().toString());
                    tempFuel.setPrice(Integer.parseInt(edtCost.getText().toString()));
                    tempFuel.setKilometer(Integer.parseInt(edtKM.getText().toString()));
                    tempFuel.setCarId(1L);
                    daoSession.getFuelDao().update(tempFuel);
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
                                daoSession.getFuelDao().delete(tempFuel);
                                // db.deleteInfo(new CarInfo(list != null ? list.get(index).getId() : 0, null, 0, 0, 0, null));
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
