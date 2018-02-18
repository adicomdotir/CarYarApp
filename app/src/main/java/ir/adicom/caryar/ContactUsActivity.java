package ir.adicom.caryar;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;

import com.cengalabs.flatui.FlatUI;

public class ContactUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.DARK);
        setContentView(R.layout.activity_contact_us);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Samim.ttf");
        HelperUI.setFont((ViewGroup) getWindow().getDecorView(), custom_font);
    }
}
