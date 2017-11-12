package ir.adicom.caryar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cengalabs.flatui.FlatUI;

import ir.adicom.caryar.service.AddServiceFragment;

public class ServiceActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.GRAPE);
        setContentView(R.layout.activity_service);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentParentViewGroup, new AddServiceFragment())
                .commit();
    }
}
