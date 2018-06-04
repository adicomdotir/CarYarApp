package ir.adicom.caryar;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;

public class FilterActivity extends Activity {

    private DatabaseHandler db;
    private ListView listView;
    private MyAdapter myAdapter;
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

        HelperUI.setFont((ViewGroup) findViewById(R.id.base_layout),
                Typeface.createFromAsset(getAssets(), App.FONT_NAME));

        final DaoSession daoSession = ((App) getApplication()).getDaoSession();

        db = new DatabaseHandler(this);
        listView = (ListView) findViewById(R.id.new_listview);

        rgOne = (RadioGroup) findViewById(R.id.rg_filter1);
        rgTwo = (RadioGroup) findViewById(R.id.rg_filter2);

        Button btn = (Button) findViewById(R.id.btn_search_filter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> stringArrayList = new ArrayList<String>();
                    // Sorting by year and month
                    // sortedList = sortByYearAndMonth(monthPriceMap);
                    RadioButton rbAlltime = (RadioButton) findViewById(rgTwo.getCheckedRadioButtonId());
                    if(rbAlltime.getText().equals("سالانه")) {
                        RadioButton rb = (RadioButton) findViewById(rgOne.getCheckedRadioButtonId());
                        if(rb.getText().equals("همه")) {
                            // Cursor cursor = db.getAll("", false);
                            Cursor cursor = daoSession.getDatabase().rawQuery("SELECT substr(DATE,1,4) AS mydate, sum(PRICE) FROM FUEL WHERE CAR_ID=" + HelperUI.CAR_ID + " AND TYPE LIKE '%" +
                                    "" + "%' GROUP BY mydate ORDER BY mydate DESC", null);
                            cursor.moveToFirst();
                            do {
                                String temp = "کل هزینه سال " + cursor.getString(0) + " : " + cursor.getInt(1) + " تومان";
                                stringArrayList.add(temp);
                            } while (cursor.moveToNext());
                        } else if(rb.getText().equals("گاز")) {
                            // Cursor cursor = db.getAll("گاز", false);
                            Cursor cursor = daoSession.getDatabase().rawQuery("SELECT substr(DATE,1,4) AS mydate, sum(PRICE) FROM FUEL WHERE CAR_ID=" + HelperUI.CAR_ID + " AND TYPE LIKE '%" +
                                    "گاز" + "%' GROUP BY mydate ORDER BY mydate DESC", null);
                            cursor.moveToFirst();
                            do {
                                String temp = "هزینه گاز سال " + cursor.getString(0) + " : " + cursor.getInt(1) + " تومان";
                                stringArrayList.add(temp);
                            } while (cursor.moveToNext());
                        } else {
                            // Cursor cursor = db.getAll("بنزین", false);
                            Cursor cursor = daoSession.getDatabase().rawQuery("SELECT substr(DATE,1,4) AS mydate, sum(PRICE) FROM FUEL WHERE CAR_ID=" + HelperUI.CAR_ID + " AND TYPE LIKE '%" +
                                    "بنزین" + "%' GROUP BY mydate ORDER BY mydate DESC", null);
                            cursor.moveToFirst();
                            do {
                                String temp = "هزینه بنزین سال " + cursor.getString(0) + " : " + cursor.getInt(1) + " تومان";
                                stringArrayList.add(temp);
                            } while (cursor.moveToNext());
                        }
                    } else {
                        RadioButton rb = (RadioButton) findViewById(rgOne.getCheckedRadioButtonId());
                        if(rb.getText().equals("همه")) {
                            // Cursor cursor = db.getAll("", true);
                            Cursor cursor = daoSession.getDatabase().rawQuery("SELECT substr(DATE,1,7) AS mydate, sum(PRICE) FROM FUEL WHERE CAR_ID=" + HelperUI.CAR_ID + " AND TYPE LIKE '%" +
                                    "" + "%' GROUP BY mydate ORDER BY mydate DESC", null);
                            cursor.moveToFirst();
                            do {
                                String[] array = cursor.getString(0).split("/");
                                int month = Integer.parseInt(array[1]);
                                String temp = "کل هزینه " + monthName[month-1] + " " + array[0] + " : " + cursor.getInt(1) + " تومان";
                                stringArrayList.add(temp);
                            } while (cursor.moveToNext());
                        } else if(rb.getText().equals("گاز")) {
                            // Cursor cursor = db.getAll("گاز", true);
                            Cursor cursor = daoSession.getDatabase().rawQuery("SELECT substr(DATE,1,7) AS mydate, sum(PRICE) FROM FUEL WHERE CAR_ID=" + HelperUI.CAR_ID + " AND TYPE LIKE '%" +
                                    "گاز" + "%' GROUP BY mydate ORDER BY mydate DESC", null);
                            cursor.moveToFirst();
                            do {
                                String[] array = cursor.getString(0).split("/");
                                int month = Integer.parseInt(array[1]);
                                String temp = "هزینه گاز " + monthName[month-1] + " " + array[0] + " : " + cursor.getInt(1) + " تومان";
                                stringArrayList.add(temp);
                            } while (cursor.moveToNext());
                        } else {
                            // Cursor cursor = db.getAll("بنزین", true);
                            Cursor cursor = daoSession.getDatabase().rawQuery("SELECT substr(DATE,1,7) AS mydate, sum(PRICE) FROM FUEL WHERE CAR_ID=" + HelperUI.CAR_ID + " AND TYPE LIKE '%" +
                                    "بنزین" + "%' GROUP BY mydate ORDER BY mydate DESC", null);
                            cursor.moveToFirst();
                            do {
                                String[] array = cursor.getString(0).split("/");
                                int month = Integer.parseInt(array[1]);
                                String temp = "هزینه بنزین " + monthName[month-1] + " " + array[0] + " : " + cursor.getInt(1) + " تومان";
                                stringArrayList.add(temp);
                            } while (cursor.moveToNext());
                        }
                    }

                String[] arr = new String[stringArrayList.size()];
                arr = stringArrayList.toArray(arr);
                myAdapter = new MyAdapter(FilterActivity.this, arr);
                listView.setAdapter(myAdapter);
            }
        });
    }

//    private List<String> sortByYearAndMonth(Map<String, Long> map) {
//        List<String> stringList = new ArrayList<String>();
//        for (String key : map.keySet()) {
//            String[] arr = key.split(",");
//            if(stringList.isEmpty()) {
//                stringList.add(key);
//            } else {
//                int i = 0;
//                for (String str : stringList) {
//                    String[] arrTemp = str.split(",");
//                    int y1 = Integer.valueOf(arr[0]);
//                    int yTemp = Integer.valueOf(arrTemp[0]);
//                    if(y1 < yTemp) {
//                        stringList.add(i, key);
//                        break;
//                    } else if(y1 == yTemp) {
//                        int m1 = Integer.valueOf(arr[1]);
//                        int mTemp = Integer.valueOf(arrTemp[1]);
//                        if(m1 < mTemp) {
//                            stringList.add(i, key);
//                            break;
//                        }
//                    }
//                    i++;
//                    if(stringList.size() == i) {
//                        stringList.add(key);
//                    }
//                }
//            }
//        }
//        return stringList;
//    }
}
