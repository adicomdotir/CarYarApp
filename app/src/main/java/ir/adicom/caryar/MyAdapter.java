package ir.adicom.caryar;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by adicom on 12/7/16.
 */

class MyAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    MyAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        rowView = inflater.inflate(R.layout.listview, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(values[position]);

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "Yekan.ttf");
        textView.setTypeface(custom_font);

        return rowView;
    }
}
