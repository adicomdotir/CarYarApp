package ir.adicom.caryar.car;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ir.adicom.caryar.App;
import ir.adicom.caryar.BaseActivity;
import ir.adicom.caryar.HelperUI;
import ir.adicom.caryar.R;
import ir.adicom.caryar.Utility.PermissionHandler;
import ir.adicom.caryar.models.Car;
import ir.adicom.caryar.models.CarDao;
import ir.adicom.caryar.models.DaoSession;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCarFragment extends Fragment {

    private EditText edtName, edtColor, edtPlaque, edtYear;

    public AddCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_car, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set font All activity element
        HelperUI.setFont((ViewGroup) view.findViewById(R.id.base_layout),
                Typeface.createFromAsset(getActivity().getAssets(), "Samim.ttf"));

        // Database initalize
        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        final CarDao carDao = daoSession.getCarDao();

        edtName = (EditText) view.findViewById(R.id.edtName);
        edtColor = (EditText) view.findViewById(R.id.edtColor);
        edtPlaque = (EditText) view.findViewById(R.id.edtPlaque);
        edtYear = (EditText) view.findViewById(R.id.edtYear);

        Button btnInsert = (Button) view.findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Car car = new Car();
                car.setName(edtName.getText().toString());
                car.setColor(edtColor.getText().toString());
                car.setPlaque(edtPlaque.getText().toString());
                car.setYear(Integer.parseInt(edtYear.getText().toString()));
                carDao.insert(car);
                edtName.setText("");
                edtColor.setText("");
                edtPlaque.setText("");
                edtYear.setText("");
            }
        });

        Button btnList = (Button) view.findViewById(R.id.btnList);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragmentParentViewGroup, new ListCarFragment())
                        .commit();
            }
        });
    }
}
