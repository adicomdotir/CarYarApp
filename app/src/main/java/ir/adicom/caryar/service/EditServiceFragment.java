package ir.adicom.caryar.service;


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

import java.util.Calendar;

import ir.adicom.caryar.AppDialog;
import ir.adicom.caryar.CalendarTool;
import ir.adicom.caryar.HelperUI;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.EngineOil;
import ir.adicom.caryar.models.EngineOilDao;
import ir.adicom.caryar.models.Service;
import ir.adicom.caryar.models.ServiceDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditServiceFragment extends Fragment {

    private Calendar calendar;
    private CalendarTool irDate;
    private EditText edtTitle, edtPriceExpert, edtPricePart;
    private Button btnDate;

    public EditServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_service, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Long id = getArguments().getLong("id", -1);

        // Set font All activity element
        HelperUI.setFont((ViewGroup) view.findViewById(R.id.base_layout),
                Typeface.createFromAsset(getActivity().getAssets(), "Vazir_Light.ttf"));

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

        // Database initalize
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        DaoSession daoSession = daoMaster.newSession();
        final ServiceDao serviceDao = daoSession.getServiceDao();
        Service service = serviceDao.load(id);

        edtPriceExpert = (EditText) view.findViewById(R.id.edt_price_expert);
        edtPricePart = (EditText) view.findViewById(R.id.edt_price_part);
        edtTitle = (EditText) view.findViewById(R.id.edtTitle);

        edtPriceExpert.setText(service.getExpertPrice());
        edtPricePart.setText(service.getPartPrice());
        edtTitle.setText(service.getTitle());
        btnDate.setText(service.getDate());

        Button btnEdit = (Button) view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service = new Service();
                service.setId(id);
                service.setDate(btnDate.getText().toString());
                service.setTitle(edtTitle.getText().toString());
                service.setExpertPrice(Integer.parseInt(edtPriceExpert.getText().toString()));
                service.setPartPrice(Integer.parseInt(edtPricePart.getText().toString()));
                serviceDao.update(service);
                getActivity().onBackPressed();
            }
        });

        Button btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceDao.deleteByKey(id);
                getActivity().onBackPressed();
            }
        });
    }
}
