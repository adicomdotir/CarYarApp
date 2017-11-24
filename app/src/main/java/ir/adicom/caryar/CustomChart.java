package ir.adicom.caryar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by adicom on 11/24/17.
 */

public class CustomChart extends ImageView {
    private int bgColor;
    private int[] colorArray;
    private float[] valueArray;

    public CustomChart(Context context) {
        super(context);
    }

    public CustomChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomChart, 0, 0);
        bgColor = ta.getColor(R.styleable.CustomChart_bgColor, Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;
        int radius = centerY;

        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        float angle = 0;
        for (int i = 0; i < valueArray.length; i++) {
            paint.setColor(colorArray[i]);
            canvas.drawArc(rectF, angle, valueArray[i] * 3.6F, true, paint);
            angle += valueArray[i] * 3.6F;
        }

        Paint bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bgColor);
        canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, canvas.getHeight()/2 - canvas.getHeight()/2*50/100, bgPaint);
    }

    public void setChartValue(int[] colorArray, float[] valueArray) {
        this.colorArray = colorArray;
        this.valueArray = valueArray;
        convertToPercent();
    }

    private void convertToPercent() {
        float sum = 0;
        for (int i = 0; i < valueArray.length; i++) {
            sum += valueArray[i];
        }
        for (int i = 0; i < valueArray.length; i++) {
            valueArray[i] = valueArray[i] / sum * 100;
        }
    }
}
