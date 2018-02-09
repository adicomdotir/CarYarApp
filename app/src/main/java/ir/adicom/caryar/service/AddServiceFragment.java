package ir.adicom.caryar.service;


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

import ir.adicom.caryar.App;
import ir.adicom.caryar.AppDialog;
import ir.adicom.caryar.CalendarTool;
import ir.adicom.caryar.CustomTextWatcher;
import ir.adicom.caryar.HelperUI;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.Service;
import ir.adicom.caryar.models.ServiceDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddServiceFragment extends Fragment {

    private Calendar calendar;
    private CalendarTool irDate;
    private EditText edtTitle, edtPriceExpert, edtPricePart;
    private Button btnDate;

    public AddServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_service, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set font All activity element
        HelperUI.setFont((ViewGroup) view.findViewById(R.id.base_layout),
                Typeface.createFromAsset(getActivity().getAssets(), "Samim.ttf"));

        // Database initalize
        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        final ServiceDao serviceDao = daoSession.getServiceDao();

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

        edtPriceExpert = (EditText) view.findViewById(R.id.edt_price_expert);
        edtPriceExpert.addTextChangedListener(new CustomTextWatcher(edtPriceExpert));
        edtPricePart = (EditText) view.findViewById(R.id.edt_price_part);
        edtPricePart.addTextChangedListener(new CustomTextWatcher(edtPricePart));
        edtTitle = (EditText) view.findViewById(R.id.edtTitle);

        Button btnInsert = (Button) view.findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service = new Service();
                service.setDate(btnDate.getText().toString());
                service.setTitle(edtTitle.getText().toString());
                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                Number myNumber = null;
                try {
                    myNumber = nf.parse(edtPriceExpert.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                service.setExpertPrice(myNumber.intValue());
                myNumber = null;
                try {
                    myNumber = nf.parse(edtPricePart.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                service.setPartPrice(myNumber.intValue());
                service.setCarId(HelperUI.CAR_ID);
                serviceDao.insert(service);
                edtPriceExpert.setText("");
                edtPricePart.setText("");
                edtPricePart.setText("");
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
                        .replace(R.id.fragmentParentViewGroup, new ListServiceFragment())
                        .commit();
            }
        });
    }
}
