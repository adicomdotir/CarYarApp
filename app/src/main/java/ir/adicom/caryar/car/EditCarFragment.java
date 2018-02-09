package ir.adicom.caryar.car;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ir.adicom.caryar.App;
import ir.adicom.caryar.HelperUI;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.Car;
import ir.adicom.caryar.models.CarDao;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditCarFragment extends Fragment {

    private EditText edtName, edtColor, edtPlaque, edtYear;

    public EditCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_car, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Long globalId = getArguments().getLong("id", -1);

        // Set font All activity element
        HelperUI.setFont((ViewGroup) view.findViewById(R.id.base_layout),
                Typeface.createFromAsset(getActivity().getAssets(), "Samim.ttf"));

        // Database initalize
        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        final CarDao carDao = daoSession.getCarDao();
        Car car = carDao.load(globalId);

        edtName = (EditText) view.findViewById(R.id.edtName);
        edtName.setText(car.getName());
        edtColor = (EditText) view.findViewById(R.id.edtColor);
        edtColor.setText(car.getColor());
        edtPlaque = (EditText) view.findViewById(R.id.edtPlaque);
        edtPlaque.setText(car.getPlaque());
        edtYear = (EditText) view.findViewById(R.id.edtYear);
        edtYear.setText("" + car.getYear());

        Button btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Car temp = new Car();
                temp.setId(globalId);
                temp.setColor(edtColor.getText().toString());
                temp.setName(edtName.getText().toString());
                temp.setPlaque(edtPlaque.getText().toString());
                temp.setYear(Integer.parseInt(edtYear.getText().toString()));
                carDao.update(temp);
                getActivity().onBackPressed();
            }
        });

        Button btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("آیا مطمئن هستید که می خواهید این رکورد حذف کنید؟")
                        .setCancelable(false)
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                carDao.deleteByKey(globalId);
                                getActivity().onBackPressed();
                            }
                        })
                        .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Button btnSelect = (Button) view.findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getActivity()
                        .getSharedPreferences(getActivity().getApplicationContext().getPackageName(),
                                getContext().MODE_PRIVATE);
                prefs.edit().putLong("CARID", globalId).apply();
                HelperUI.CAR_ID = globalId;
                getActivity().onBackPressed();
            }
        });
    }

}
