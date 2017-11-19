package ir.adicom.caryar.service;


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

import ir.adicom.caryar.R;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.Service;
import ir.adicom.caryar.models.ServiceDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListServiceFragment extends Fragment {

    private List<Service> services;

    public ListServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_service, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Database initalize
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "carhelper-db", null);
        SQLiteDatabase dbDao = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(dbDao);
        DaoSession daoSession = daoMaster.newSession();
        ServiceDao serviceDao = daoSession.getServiceDao();

        services = serviceDao.queryBuilder().orderDesc(ServiceDao.Properties.Id).list();

        ListView listView = (ListView) view.findViewById(R.id.listview_service);
        ServiceAdapter myAdapter = new ServiceAdapter(getContext(), services);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", services.get(i).getId());
                EditServiceFragment myObj = new EditServiceFragment();
                myObj.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragmentParentViewGroup, myObj)
                        .commit();
            }
        });
    }

}
