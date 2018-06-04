package ir.adicom.caryar.car;

/**
 * Created by adicom on 11/20/17.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ir.adicom.caryar.App;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.Car;

/**
 * Created by adicom on 11/9/17.
 */

public class CarAdapter extends ArrayAdapter<Car> {
    private final Context context;
    private final List<Car> cars;

    public CarAdapter(Context context, List<Car> cars) {
        super(context, -1, cars);
        this.context = context;
        this.cars = cars;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        rowView = inflater.inflate(R.layout.listview_car, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        // textView.setText(values[position]);

        StringBuilder sb = new StringBuilder();
        sb.append("نام: " + cars.get(position).getName() + "\n");
        sb.append("پلاک: " + cars.get(position).getPlaque() + "\n");
        sb.append("رنگ: " + cars.get(position).getColor() + "\n");
        sb.append("سال ساخت: " + cars.get(position).getYear());

        textView.setText(sb);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), App.FONT_NAME);
        textView.setTypeface(custom_font);

        return rowView;
    }
}


