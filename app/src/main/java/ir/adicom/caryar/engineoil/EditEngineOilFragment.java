package ir.adicom.caryar.engineoil;


import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.Calendar;

import ir.adicom.caryar.AppDialog;
import ir.adicom.caryar.CalendarTool;
import ir.adicom.caryar.HelperUI;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.EngineOil;
import ir.adicom.caryar.models.EngineOilDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditEngineOilFragment extends Fragment {

    private Calendar calendar;
    private CalendarTool irDate;
    private EditText edtTitle, edtKm, edtKmMax, edtPrice;

    public EditEngineOilFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_engine_oil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Long id = getArguments().getLong("id", -1);

        // Set font All activity element
        HelperUI.setFont((ViewGroup) view.findViewById(R.id.base_layout),
                Typeface.createFromAsset(getActivity().getAssets(), "Yekan.ttf"));

        calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        irDate = new CalendarTool(mYear, mMonth + 1, mDay);
        final Button btnDate = (Button) view.findViewById(R.id.btnDate);
        btnDate.setText(irDate.getIranianDate());
        final AppDialog appDialog = new AppDialog(getContext(), irDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDialog.show();
            }
        });
        appDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                AppDialog appDialog1 = (AppDialog) dialogInterface;
                btnDate.setText(appDialog1.date);
            }
        });

        // Database initalize
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        DaoSession daoSession = daoMaster.newSession();
        final EngineOilDao engineOilDao = daoSession.getEngineOilDao();
        EngineOil engineOil = engineOilDao.load(id);

        edtKm = (EditText) view.findViewById(R.id.edtKm);
        edtKm.setText("" + engineOil.getNowKilometer());
        edtKmMax = (EditText) view.findViewById(R.id.edtKmMax);
        edtKmMax.setText("" + engineOil.getMaxKilometer());
        edtPrice = (EditText) view.findViewById(R.id.edtCost);
        edtPrice.setText("" + engineOil.getPrice());
        edtTitle = (EditText) view.findViewById(R.id.edtTitle);
        edtTitle.setText("" + engineOil.getName());
        btnDate.setText(engineOil.getDate());

        Button btnEdit = (Button) view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EngineOil temp = new EngineOil();
                temp.setId(id);
                temp.setPrice(Integer.parseInt(edtPrice.getText().toString()));
                temp.setNowKilometer(Integer.parseInt(edtKm.getText().toString()));
                temp.setMaxKilometer(Integer.parseInt(edtKmMax.getText().toString()));
                temp.setDate(btnDate.getText().toString());
                temp.setName(edtTitle.getText().toString());
                engineOilDao.update(temp);
                getActivity().onBackPressed();
            }
        });

        Button btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                engineOilDao.deleteByKey(id);
                getActivity().onBackPressed();
            }
        });
    }
}
