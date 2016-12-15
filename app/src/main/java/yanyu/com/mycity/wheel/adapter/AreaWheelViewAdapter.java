package yanyu.com.mycity.wheel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import yanyu.com.mycity.R;

/**
 * Created by lizhangfeng on 16/4/11.
 * description: 省滚轮adapter
 */
public class AreaWheelViewAdapter extends AbstractWheelTextAdapter {
    private List<String> areaList;
    private int selectPosition;

    public AreaWheelViewAdapter(Context context, List<String> areaList) {
        super(context, R.layout.nation_layout, NO_RESOURCE);
        setItemTextResource(R.id.nation_name);
        this.areaList = areaList;
    }
    public List<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<String> areaList) {
        this.areaList = areaList;
    }
    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        this.notifyDataChangedEvent();
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {

        View view = super.getItem(index, convertView, parent);

        if (areaList == null || areaList.get(index) == null)
            return view;

        TextView tv = (TextView) view.findViewById(R.id.nation_name);
        tv.setText(areaList.get(index));
        if (index == selectPosition) {
            tv.setTextColor(Color.BLACK);
        } else if (index - selectPosition == 1 || index - selectPosition == -1) {
            tv.setTextColor(Color.parseColor("#B4B4B4"));
        } else {
            tv.setTextColor(Color.parseColor("#D8D8D8"));
        }
        return view;
    }

    @Override
    public int getItemsCount() {
        return areaList.size();
    }

    @Override
    protected CharSequence getItemText(int index) {
        return areaList.get(index);
    }
}
