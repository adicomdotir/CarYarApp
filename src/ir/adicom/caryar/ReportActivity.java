package ir.adicom.caryar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReportActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_activity);
		
		String[] stringArray = getIntent().getExtras().getStringArray("Array");
		for(int start=0, end=stringArray.length-1; start<=end; start++, end--){
            String aux = stringArray[start];
            stringArray[start]=stringArray[end];
            stringArray[end]=aux;
        }
		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				R.layout.listview,
				R.id.label,
				stringArray);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}
}
