package ir.adicom.caryar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cengalabs.flatui.FlatUI;

import ir.adicom.caryar.Utility.ImportExportDatabase;

public class ContactUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlatUI.initDefaultValues(this);
        FlatUI.setDefaultTheme(FlatUI.DARK);
        setContentView(R.layout.activity_contact_us);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Samim.ttf");
        HelperUI.setFont((ViewGroup) getWindow().getDecorView(), custom_font);


        Button btnImport = (Button) findViewById(R.id.btn_import);
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportExportDatabase.importDB();
            }
        });

        Button btnExport = (Button) findViewById(R.id.btn_export);
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportExportDatabase.exportDB();
            }
        });

        Button btnCm = (Button) findViewById(R.id.btn_cm);
//        btnCm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url= "myket://comment?id=ir.adicom.caryar";
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);
//            }
//        });
    }
}
