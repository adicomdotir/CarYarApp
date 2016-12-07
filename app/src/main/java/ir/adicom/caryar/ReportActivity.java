package ir.adicom.caryar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

public class ReportActivity extends Activity {

	private DatabaseHandler db;
	private List<CarInfo> list;
	private MyAdapter myAdapter;
	private ListView listView;
	private String[] arr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_activity);

		db = new DatabaseHandler(this);
		listView = (ListView) findViewById(R.id.listView1);
		init();

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(ReportActivity.this, EditAcitivity.class);
				intent.putExtra("INDEX", (list.size()-1-i));
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}


	private void init() {
		list = db.getAllInfo();
		arr = new String[list.size()];
		if(list != null) {
			for (int i = 0; i < arr.length; i++) {
				Calendar mydate = Calendar.getInstance();
				mydate.setTimeInMillis(list.get(i).getDate() * 1000);
				CalendarTool irDate = new CalendarTool();
				irDate.setGregorianDate(
						mydate.get(Calendar.YEAR),
						mydate.get(Calendar.MONTH) + 1,
						mydate.get(Calendar.DAY_OF_MONTH)
				);
				arr[i] = "تاریخ :‌ " + irDate.getIranianDate() + "   " +
						"نوع سوخت : " + list.get(i).getType() + "\n" +
						"هزینه : " + list.get(i).getPrice() + " تومان\n" +
						"کیلومتر : " + list.get(i).getKilometer();
			}
		}

		// For reverse array
		for(int start=0, end=arr.length-1; start<=end; start++, end--){
			String aux = arr[start];
			arr[start]=arr[end];
			arr[end]=aux;
		}

		myAdapter = new MyAdapter(this, arr);
		listView.setAdapter(myAdapter);
	}
}
