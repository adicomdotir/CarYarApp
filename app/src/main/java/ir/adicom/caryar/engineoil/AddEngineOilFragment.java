package ir.adicom.caryar.engineoil;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import ir.adicom.caryar.AppDialog;
import ir.adicom.caryar.CalendarTool;
import ir.adicom.caryar.CustomTextWacher;
import ir.adicom.caryar.HelperUI;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.EngineOil;
import ir.adicom.caryar.models.EngineOilDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEngineOilFragment extends Fragment {

    private Calendar calendar;
    private CalendarTool irDate;
    private EditText edtTitle, edtKm, edtKmMax, edtPrice;

    public AddEngineOilFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_engine_oil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set font All activity element
        HelperUI.setFont((ViewGroup) view.findViewById(R.id.base_layout),
                Typeface.createFromAsset(getActivity().getAssets(), "Vazir_Light.ttf"));

        // Database initalize
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        DaoSession daoSession = daoMaster.newSession();
        final EngineOilDao engineOilDao = daoSession.getEngineOilDao();

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

        edtKm = (EditText) view.findViewById(R.id.edtKm);
        edtKmMax = (EditText) view.findViewById(R.id.edtKmMax);
        edtPrice = (EditText) view.findViewById(R.id.edtCost);
        edtPrice.addTextChangedListener(new CustomTextWacher(edtPrice));
        edtTitle = (EditText) view.findViewById(R.id.edtTitle);

        Button btnInsert = (Button) view.findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EngineOil engineOil = new EngineOil();
                engineOil.setDate(btnDate.getText().toString());
                engineOil.setName(edtTitle.getText().toString());
                engineOil.setMaxKilometer(Integer.parseInt(edtKmMax.getText().toString()));
                engineOil.setNowKilometer(Integer.parseInt(edtKm.getText().toString()));
                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                Number myNumber = null;
                try {
                    myNumber = nf.parse(edtPrice.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                engineOil.setPrice(myNumber.intValue());
                engineOil.setCarId(HelperUI.CAR_ID);
                engineOilDao.insert(engineOil);
            }
        });

        Button btnList = (Button) view.findViewById(R.id.btnList);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragmentParentViewGroup, new ListEngineOilFragment())
                        .commit();
            }
        });
    }
}
