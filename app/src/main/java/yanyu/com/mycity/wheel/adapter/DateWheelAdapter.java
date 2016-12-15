package yanyu.com.mycity.wheel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yanyu.com.mycity.R;


/**
 * Created by zengqiang on 16/10/19.
 * description: 年月日选择日期的picker
 */
public class DateWheelAdapter extends AbstractWheelTextAdapter {
    private Context context;
    /* 选择的位置 */
    private int selectPosition;

    private int minValue, maxValue;

    private String type;

    public DateWheelAdapter(Context context, int minValue, int maxValue, String type) {
        super(context, R.layout.nation_layout, NO_RESOURCE);
        setItemTextResource(R.id.nation_name);
        this.context = context;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.type = type;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinAndMaxValue(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        View view = super.getItem(index, convertView, parent);
        TextView tv = (TextView) view.findViewById(R.id.nation_name);
        tv.setText("" + (minValue + index) + type);
        if (index == selectPosition) {
            tv.setTextSize(14);
            tv.setTextColor(Color.BLACK);
        } else if (index - selectPosition == 1 || index - selectPosition == -1) {
            tv.setTextSize(14);
            tv.setTextColor(Color.parseColor("#B4B4B4"));
        } else {
            tv.setTextSize(14);
            tv.setTextColor(Color.parseColor("#D8D8D8"));
        }
        return view;
    }

    @Override
    protected CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return Integer.toString(value);
        }
        return null;
    }
}

