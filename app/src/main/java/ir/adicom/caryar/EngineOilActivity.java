package ir.adicom.caryar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cengalabs.flatui.FlatUI;

import ir.adicom.caryar.engineoil.AddEngineOilFragment;

public class EngineOilActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.CANDY);
        setContentView(R.layout.activity_engine_oil);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentParentViewGroup, new AddEngineOilFragment())
                .commit();
    }
}
