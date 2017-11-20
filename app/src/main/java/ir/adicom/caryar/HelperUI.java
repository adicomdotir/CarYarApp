package ir.adicom.caryar;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HelperUI {

	public static long CAR_ID = 0;
	
	public static void setFont(ViewGroup viewGroup, Typeface font) {
		int childCount = viewGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = viewGroup.getChildAt(i);
			if(child instanceof ViewGroup) {
				setFont((ViewGroup) child, font);
				continue;
			}
			if(child instanceof TextView) {
				((TextView) child).setTypeface(font);
			}
		}
	}


}
