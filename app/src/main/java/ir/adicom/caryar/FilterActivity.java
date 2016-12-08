package ir.adicom.caryar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FilterActivity extends Activity {

    private DatabaseHandler db;
    private ListView listView;
    private MyAdapter myAdapter;
    private List<CarInfo> arrayList;
    private RadioGroup rgOne, rgTwo;
    private String[] monthName = {
            "فروردین","اردیبهشت","خرداد",
            "تیر","مرداد","شهریور",
            "مهر","ابان","اذر",
            "دی","بهمن","اسفند"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        db = new DatabaseHandler(this);
        listView = (ListView) findViewById(R.id.new_listview);

        rgOne = (RadioGroup) findViewById(R.id.rg_filter1);
        rgTwo = (RadioGroup) findViewById(R.id.rg_filter2);

        final Calendar mydate = Calendar.getInstance();
        final CalendarTool irDate = new CalendarTool();

        arrayList = db.getAllInfo();
        ArrayList<String> stringArrayList = new ArrayList<String>();
        long sumPrice = 0;
        if(arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                sumPrice += arrayList.get(i).getPrice();
            }
            stringArrayList.add("کل هزینه: " + sumPrice + " تومان");
        }

        String[] arr = new String[stringArrayList.size()];
        arr = stringArrayList.toArray(arr);
        myAdapter = new MyAdapter(this, arr);
        listView.setAdapter(myAdapter);

        Button btn = (Button) findViewById(R.id.btn_search_filter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList = db.getAllInfo();
                ArrayList<String> stringArrayList = new ArrayList<String>();
                long[] sumPrice = new long[12];
                long[] sumPriceForGus = new long[12];
                if(arrayList != null) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        mydate.setTimeInMillis(arrayList.get(i).getDate() * 1000);
                        irDate.setGregorianDate(
                                mydate.get(Calendar.YEAR),
                                mydate.get(Calendar.MONTH) + 1,
                                mydate.get(Calendar.DAY_OF_MONTH)
                        );
                        int id = irDate.getIranianMonth()-1;
                        sumPrice[id] += arrayList.get(i).getPrice();
                        if(arrayList.get(i).getType().equals("گاز"))
                            sumPriceForGus[id] += arrayList.get(i).getPrice();
                    }
                    RadioButton rbAlltime = (RadioButton) findViewById(rgTwo.getCheckedRadioButtonId());
                    if(rbAlltime.getText().equals("کل")) {
                        long sp = 0;
                        long spfg = 0;
                        for (int j=0; j<12; j++) {
                            sp += sumPrice[j];
                            spfg += sumPriceForGus[j];
                        }
                        RadioButton rb = (RadioButton) findViewById(rgOne.getCheckedRadioButtonId());
                        if(rb.getText().equals("همه")) {
                            stringArrayList.add("کل هزینه: " + sp + " تومان");
                        } else if(rb.getText().equals("گاز")) {
                            stringArrayList.add("هزینه گاز: " + spfg + " تومان");
                        } else {
                            stringArrayList.add("هزینه بنزین: " + (sp-spfg) + " تومان");
                        }
                    } else {
                        RadioButton rb = (RadioButton) findViewById(rgOne.getCheckedRadioButtonId());
                        for (int j=0; j<12; j++) {
                            if(rb.getText().equals("همه")) {
                                stringArrayList.add("کل هزینه " + monthName[j] + " : " + sumPrice[j] + " تومان");
                            } else if(rb.getText().equals("گاز")) {
                                stringArrayList.add("هزینه گاز " + monthName[j] + " : " +  sumPriceForGus[j] + " تومان");
                            } else {
                                stringArrayList.add("هزینه بنزین " + monthName[j] + " : " +  (sumPrice[j]-sumPriceForGus[j]) + " تومان");
                            }
                        }
                    }

                }
                String[] arr = new String[stringArrayList.size()];
                arr = stringArrayList.toArray(arr);
                myAdapter = new MyAdapter(FilterActivity.this, arr);
                listView.setAdapter(myAdapter);
            }
        });
    }
}