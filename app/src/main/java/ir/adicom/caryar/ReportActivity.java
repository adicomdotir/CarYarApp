package ir.adicom.caryar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReportActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_activity);

		String[] stringArray = getIntent().getExtras().getStringArray("Array");

		// For reverse array
		for(int start=0, end=stringArray.length-1; start<=end; start++, end--){
            String aux = stringArray[start];
            stringArray[start]=stringArray[end];
            stringArray[end]=aux;
        }

		MyAdapter myAdapter = new MyAdapter(this, stringArray);
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(myAdapter);
		Log.e("TAG", "onCreate: " + myAdapter.getCount());
	}
}
