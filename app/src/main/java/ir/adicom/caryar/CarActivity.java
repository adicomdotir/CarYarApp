package ir.adicom.caryar;

import android.os.Bundle;
import com.cengalabs.flatui.FlatUI;
import ir.adicom.caryar.Utility.ParentActivity;
import ir.adicom.caryar.car.AddCarFragment;

public class CarActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.GRASS);
        setContentView(R.layout.activity_car);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentParentViewGroup, new AddCarFragment())
                .commit();
    }

}
