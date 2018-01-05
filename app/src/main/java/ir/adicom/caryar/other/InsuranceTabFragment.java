package ir.adicom.caryar.other;


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
import ir.adicom.caryar.CustomTextWatcher;
import ir.adicom.caryar.HelperUI;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.Insurance;
import ir.adicom.caryar.models.InsuranceDao;
import ir.adicom.caryar.models.Service;
import ir.adicom.caryar.models.ServiceDao;
import ir.adicom.caryar.service.ListServiceFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsuranceTabFragment extends Fragment {

    private Calendar calendar;
    private CalendarTool irDate;
    private EditText edtTitle, edtPrice;
    private Button btnDate;


    public InsuranceTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insurance_tab, container, false);
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
        final InsuranceDao insuranceDao = daoSession.getInsuranceDao();

        calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        irDate = new CalendarTool(mYear, mMonth + 1, mDay);
        btnDate = (Button) view.findViewById(R.id.btnDate);
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

        edtPrice = (EditText) view.findViewById(R.id.edt_price);
        edtPrice.addTextChangedListener(new CustomTextWatcher(edtPrice));
        edtTitle = (EditText) view.findViewById(R.id.edtTitle);

        Button btnInsert = (Button) view.findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insurance insurance = new Insurance();
                insurance.setDate(btnDate.getText().toString());
                insurance.setName(edtTitle.getText().toString());
                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                Number myNumber = null;
                try {
                    myNumber = nf.parse(edtPrice.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                insurance.setPrice(myNumber.intValue());
                insurance.setCarId(HelperUI.CAR_ID);
                insuranceDao.insert(insurance);
                edtPrice.setText("");
                edtTitle.setText("");
            }
        });

        Button btnList = (Button) view.findViewById(R.id.btnList);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragmentParentViewGroup, new ListInsuranceFragment())
                        .commit();
            }
        });
    }
}
