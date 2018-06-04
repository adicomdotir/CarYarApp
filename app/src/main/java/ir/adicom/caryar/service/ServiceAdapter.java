package ir.adicom.caryar.service;

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

import ir.adicom.caryar.App;
import ir.adicom.caryar.R;
import ir.adicom.caryar.models.EngineOil;
import ir.adicom.caryar.models.Service;

/**
 * Created by adicom on 11/9/17.
 */

public class ServiceAdapter extends ArrayAdapter<Service> {
    private final Context context;
    private final List<Service> services;

    public ServiceAdapter(Context context, List<Service> services) {
        super(context, -1, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        rowView = inflater.inflate(R.layout.listview_service, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        // textView.setText(values[position]);

        StringBuilder sb = new StringBuilder();
        sb.append("عنوان: " + services.get(position).getTitle() + "\n");
        sb.append("تاریخ: " + services.get(position).getDate() + "\n");
        sb.append("هزینه دستمزد: " + NumberFormat.getNumberInstance(Locale.US).format(services.get(position).getExpertPrice()) + " تومان" + "\n");
        sb.append("هزینه قطعه: " + NumberFormat.getNumberInstance(Locale.US).format(services.get(position).getPartPrice()) + " تومان");

        textView.setText(sb);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), App.FONT_NAME);
        textView.setTypeface(custom_font);

        return rowView;
    }
}

