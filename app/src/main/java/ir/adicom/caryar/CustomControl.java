package ir.adicom.caryar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by adicom on 12/5/16.
 */

public class CustomControl extends LinearLayout {

    private ImageView btnDown, btnUp;
    private TextView tvText;
    private int number = 1;
    private int max = 0, min = 0;
    private int len = 4;

    public void setLen(int len) {
        this.len = len;
        invalidate();
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }


    public CustomControl(Context context) {
        super(context);
        init(context);
    }

    public CustomControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflate(context, R.layout.custom_layout, null);
        View view = inflater.inflate(R.layout.custom_layout, this, true);

        tvText = (TextView) view.findViewById(R.id.tv_custom);
        btnDown = (ImageView) view.findViewById(R.id.btn_custom_down);
        btnUp = (ImageView) view.findViewById(R.id.btn_custom_up);

//        addView(view);
    }

    public void setUp() {
        tvText.setText(getFormat());

        btnUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number<max)
                    number++;
                tvText.setText(getFormat());
            }
        });

        btnDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number>min)
                    number--;
                tvText.setText(getFormat());
            }
        });
    }

    private String getFormat() {
        String format = "%0" + len + "d";
        String str = String.format(format, number);
        char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<str.length();i++) {
            if(Character.isDigit(str.charAt(i))) {
                builder.append(arabicChars[(int)(str.charAt(i))-48]);
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public String getText() {
        return tvText.getText().toString();
    }
}