package ir.adicom.caryar;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public CarInfo c;
	public static Typeface custom_font;
	Button btnChert;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	RadioButton rbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Calendar calendar = Calendar.getInstance(); 
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		custom_font = Typeface.createFromAsset(getAssets(), "BZar.ttf");
		
		HelperUI.setFont((ViewGroup) getWindow().getDecorView());
		
		final DatabaseHandler db = new DatabaseHandler(this);
		
		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		final EditText edtKilo = (EditText) findViewById(R.id.editText1);
		final EditText edtprice = (EditText) findViewById(R.id.editText2);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setTypeface(custom_font);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				rbtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
				Calendar calendar = Calendar.getInstance();
				calendar.set(mYear, mMonth, mDay, 0, 0, 0);
				long startTime = calendar.getTimeInMillis();
				Log.e("MYTAG", "" + startTime);
				if(edtKilo.getText().toString().trim().length() == 0
					|| edtprice.getText().toString().trim().length() == 0 ) {
					Toast.makeText(getApplicationContext(), "هر دو فیلد را پر کنید", Toast.LENGTH_SHORT).show();
				} else {
					db.addInfo(
							new CarInfo(rbtn.getText().toString(), 
									Integer.valueOf(edtprice.getText().toString()),
									Integer.valueOf(edtKilo.getText().toString()),
									startTime/1000L)
							);
					edtKilo.setText("");
					edtprice.setText("");
					Toast.makeText(getApplicationContext(), "Record Added", Toast.LENGTH_SHORT).show();
				}
			}
		});
		// FIXME
		// XXX
		// TODO
		final TextView tv1 = (TextView) findViewById(R.id.textView4);
		tv1.setTypeface(custom_font);
		final TextView tv2 = (TextView) findViewById(R.id.textView5);
		tv2.setTypeface(custom_font);
		final TextView tv3 = (TextView) findViewById(R.id.textView6);
		tv3.setTypeface(custom_font);
		
		tv1.setVisibility(View.INVISIBLE);
		tv2.setVisibility(View.INVISIBLE);
		tv3.setVisibility(View.INVISIBLE);
		
		final Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
		
		Button btn1 = (Button) findViewById(R.id.button2);
		btn1.setTypeface(custom_font);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				List<CarInfo> list = db.getAllInfo();
				Log.e("TAG", "" + list.get(0).getPrice());
				String[] arr = new String[list.size()];
				for (int i = 0; i < arr.length; i++) {
					Calendar mydate = Calendar.getInstance();
					mydate.setTimeInMillis(list.get(i).getDate()*1000);
					CalendarTool irDate = new CalendarTool();
					irDate.setGregorianDate(
							mydate.get(Calendar.YEAR), 
							mydate.get(Calendar.MONTH)+1, 
							mydate.get(Calendar.DAY_OF_MONTH)
							);
					arr[i] = "تاریخ :‌ " + irDate.getIranianDate() + "   " +
							"نوع سوخت : " + list.get(i).getType() + "\n" + 
							"هزینه : " + list.get(i).getPrice() + " تومان\n" + 
							"کیلومتر : " + list.get(i).getKilometer();
				}
				
				c = db.getInfo(0);
				if(c!=null) {
					Calendar mydate = Calendar.getInstance();
					mydate.setTimeInMillis(c.getDate()*1000);
					CalendarTool irDate = new CalendarTool();
					irDate.setGregorianDate(
							mydate.get(Calendar.YEAR), 
							mydate.get(Calendar.MONTH)+1, 
							mydate.get(Calendar.DAY_OF_MONTH)
							);
					tv1.setText("تاریخ :‌ " + irDate.getIranianDate() + "   " +
								"نوع سوخت : " + c.getType() + "\n" + 
								"هزینه : " + c.getPrice() + " تومان\n" + 
								"کیلومتر : " + c.getKilometer());
					tv1.setVisibility(View.VISIBLE);
				}
				c = db.getInfo(1);
				if(c!=null) {
					Calendar mydate = Calendar.getInstance();
					mydate.setTimeInMillis(c.getDate()*1000);
					CalendarTool irDate = new CalendarTool();
					irDate.setGregorianDate(
							mydate.get(Calendar.YEAR), 
							mydate.get(Calendar.MONTH)+1, 
							mydate.get(Calendar.DAY_OF_MONTH)
							);
					tv2.setText("تاریخ :‌ " + irDate.getIranianDate() + "   " +
								"نوع سوخت : " + c.getType() + "\n" + 
								"هزینه : " + c.getPrice() + " تومان\n" + 
								"کیلومتر : " + c.getKilometer());
					tv2.setVisibility(View.VISIBLE);
				}
				c = db.getInfo(2);
				if(c!=null) {
					Calendar mydate = Calendar.getInstance();
					mydate.setTimeInMillis(c.getDate()*1000);
					CalendarTool irDate = new CalendarTool();
					irDate.setGregorianDate(
							mydate.get(Calendar.YEAR), 
							mydate.get(Calendar.MONTH)+1, 
							mydate.get(Calendar.DAY_OF_MONTH)
							);
					tv3.setText("تاریخ :‌ " + irDate.getIranianDate() + "   " +
								"نوع سوخت : " + c.getType() + "\n" + 
								"هزینه : " + c.getPrice() + " تومان\n" + 
								"کیلومتر : " + c.getKilometer());
					tv3.setVisibility(View.VISIBLE);
				}
				intent.putExtra("Array", arr);
				startActivity(intent);
			}
		});
				
		btnChert = (Button) findViewById(R.id.btn_date);
		btnChert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDialog(0);
			}
		});
		
		CalendarTool irDate = new CalendarTool();
		btnChert.setTypeface(custom_font);
		btnChert.setText(irDate.getIranianDate());
		
		
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
		    new DatePickerDialog.OnDateSetListener() {
		        public void onDateSet(DatePicker view, int year, 
		                              int monthOfYear, int dayOfMonth) {
		        	CalendarTool irDate = new CalendarTool(year, monthOfYear+1, dayOfMonth);
		        	btnChert.setText(irDate.getIranianDate());
		        	mYear = year;
		        	mMonth = monthOfYear;
		        	mDay = dayOfMonth;
		        }
		    };
		    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Calendar mydate = Calendar.getInstance();
    	switch (id) {
    	case 0:
    		return new DatePickerDialog(this,
    				mDateSetListener,
                    mydate.get(Calendar.YEAR), 
                    mydate.get(Calendar.MONTH), 
                    mydate.get(Calendar.DAY_OF_MONTH));
    	}
    	return null;
    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
