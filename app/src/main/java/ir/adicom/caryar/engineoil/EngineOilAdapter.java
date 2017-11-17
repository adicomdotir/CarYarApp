package ir.adicom.caryar.engineoil;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import ir.adicom.caryar.R;
import ir.adicom.caryar.models.EngineOil;

/**
 * Created by adicom on 11/9/17.
 */

public class EngineOilAdapter extends ArrayAdapter<EngineOil> {
    private final Context context;
    private final List<EngineOil> engineOils;

    public EngineOilAdapter(Context context, List<EngineOil> engineOils) {
        super(context, -1, engineOils);
        this.context = context;
        this.engineOils = engineOils;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        rowView = inflater.inflate(R.layout.listview_oil, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        // textView.setText(values[position]);

        StringBuilder sb = new StringBuilder();
        sb.append("نام روغن: " + engineOils.get(position).getName() + "\n");
        sb.append("تاریخ: " + engineOils.get(position).getDate() + "\n");
        sb.append("حداکثر کارکرد: " + engineOils.get(position).getMaxKilometer() + " کیلومتر\n");
        sb.append("کیلومتر: " + engineOils.get(position).getNowKilometer() + " کیلومتر\n");
        sb.append("هزینه: " + NumberFormat.getNumberInstance(Locale.US).format(engineOils.get(position).getPrice()) + " تومان");

        textView.setText(sb);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "Vazir_Light.ttf");
        textView.setTypeface(custom_font);

        return rowView;
    }
}

