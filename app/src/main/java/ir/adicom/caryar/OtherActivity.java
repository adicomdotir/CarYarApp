package ir.adicom.caryar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cengalabs.flatui.FlatUI;

import ir.adicom.caryar.other.OtherFragment;

public class OtherActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.DEEP);
        setContentView(R.layout.activity_other);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentParentViewGroup, new OtherFragment())
                .commit();
    }
}
