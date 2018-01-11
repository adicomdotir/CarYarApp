package ir.adicom.caryar.engineoil;


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

import ir.adicom.caryar.App;
import ir.adicom.caryar.HelperUI;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.EngineOil;
import ir.adicom.caryar.models.EngineOilDao;
import ir.adicom.caryar.models.FuelDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListEngineOilFragment extends Fragment {

    private List<EngineOil> engineOils;

    public ListEngineOilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_engine_oil, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Database initalize
        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        EngineOilDao engineOilDao = daoSession.getEngineOilDao();

        engineOils = engineOilDao.queryBuilder().where(EngineOilDao.Properties.CarId.eq(HelperUI.CAR_ID))
                .orderDesc(EngineOilDao.Properties.Id).list();

        ListView listView = (ListView) view.findViewById(R.id.listview_oil);
        EngineOilAdapter myAdapter = new EngineOilAdapter(getContext(), engineOils);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", engineOils.get(i).getId());
                EditEngineOilFragment myObj = new EditEngineOilFragment();
                myObj.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragmentParentViewGroup, myObj)
                        .commit();
            }
        });
    }
}
