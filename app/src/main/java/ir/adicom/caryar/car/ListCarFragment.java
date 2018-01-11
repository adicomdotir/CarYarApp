package ir.adicom.caryar.car;


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
import ir.adicom.caryar.R;
import ir.adicom.caryar.engineoil.EditEngineOilFragment;
import ir.adicom.caryar.engineoil.EngineOilAdapter;
import ir.adicom.caryar.models.Car;
import ir.adicom.caryar.models.CarDao;
import ir.adicom.caryar.models.DaoMaster;
import ir.adicom.caryar.models.DaoSession;
import ir.adicom.caryar.models.EngineOilDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListCarFragment extends Fragment {

    private List<Car> carList;

    public ListCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_car, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Database initalize
        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        CarDao carDao = daoSession.getCarDao();

        carList = carDao.queryBuilder().orderDesc(CarDao.Properties.Id).list();

        ListView listView = (ListView) view.findViewById(R.id.listview_car);
        CarAdapter myAdapter = new CarAdapter(getContext(), carList);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", carList.get(i).getId());
                EditCarFragment myObj = new EditCarFragment();
                myObj.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragmentParentViewGroup, myObj)
                        .commit();
            }
        });
    }

}
