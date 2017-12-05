package ir.adicom.caryar.Utility;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

/**
 * Created by adicom on 12/4/17.
 */

public class ParentActivity extends FragmentActivity {
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent("PERMISSION_RECEIVER");
        intent.putExtra("requestCode",requestCode);
        intent.putExtra("permissions",permissions);
        intent.putExtra("grantResults",grantResults);
        sendBroadcast(intent);
    }
}
