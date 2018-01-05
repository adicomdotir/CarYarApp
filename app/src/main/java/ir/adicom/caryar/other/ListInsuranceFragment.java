package ir.adicom.caryar.other;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ir.adicom.caryar.HelperUI;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.Insurance;
import ir.adicom.caryar.models.InsuranceDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListInsuranceFragment extends Fragment {


    private List<Insurance> insurances;
    public ListInsuranceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_insurance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Database initalize
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        DaoSession daoSession = daoMaster.newSession();
        InsuranceDao insuranceDao = daoSession.getInsuranceDao();

        insurances = insuranceDao.queryBuilder().where(InsuranceDao.Properties.CarId.eq(HelperUI.CAR_ID))
                .orderDesc(InsuranceDao.Properties.Id).list();

        ListView listView = (ListView) view.findViewById(R.id.listview_insurance);
        InsuranceAdapter myAdapter = new InsuranceAdapter(getContext(), insurances);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", insurances.get(i).getId());
                EditInsuranceFragment myObj = new EditInsuranceFragment();
                myObj.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragmentParentViewGroup, myObj)
                        .commit();
            }
        });
    }

}
