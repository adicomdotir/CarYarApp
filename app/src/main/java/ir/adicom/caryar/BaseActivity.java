package ir.adicom.caryar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base2);

        HelperUI.setFont((ViewGroup) findViewById(R.id.base_layout),
                Typeface.createFromAsset(getAssets(), "Vazir.ttf"));

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fuel_layer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.oil_layer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, EngineOilActivity.class));
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.service_layer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseActivity.this, ServiceActivity.class));
            }
        });
    }
}
