package yanyu.com.mycity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import yanyu.com.mycity.wheel.OnWheelChangedListener;
import yanyu.com.mycity.wheel.WheelView;
import yanyu.com.mycity.wheel.adapter.AreaWheelViewAdapter;
import yanyu.com.mycity.wheel.adapter.CityWheelViewAdapter;
import yanyu.com.mycity.wheel.adapter.ProvinceWheelViewAdapter;

/**
 * Created by Administrator on 2016/10/19.
 */

public class CityPickDialog extends Dialog {
    private Context context;
    //    private static int theme = R.style.dialog_setting;
    /* 滑动到的省市 */
    private String selectCity = "";

    private String selectProvince = "";

    private String selectArea = "";

    private City selectedCity;

    private Province selectedProvince;

    private String selectedArea;

    /* 选择的省市 */
    private City currentCity;

    private Province currentProvince;

    private String currentArea;

    private int cityPosition = 0;

    private int areaPosition = 0;

    private int provincePosition = 3;

    private List<Province> provinces;

    private List<City> cities;

    private CityPickCallBack callBack;

    private AreaCallBack areaCallBack;

    private boolean area;

    public void setArea(boolean area) {
        this.area = area;
    }

    public void setAreaCallBack(AreaCallBack callBack) {
        this.areaCallBack = callBack;
        dialog();
    }

    public void setCallBack(CityPickCallBack callBack) {
        this.callBack = callBack;
        dialog();
    }

    public CityPickDialog(Context context) {
        super(context);
        this.context = context;
        provinces = AssetsUtil.getProvince(context, "city.json");
        this.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_citypicker);
    }

    public void dialog() {
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = d.getWidth() * 9 / 10;
        lp.height = d.getHeight() * 3 / 5;
        window.setAttributes(lp);
        WheelView provinceWheelView = (WheelView) findViewById(R.id.id_province);
        final WheelView cityWheelView = (WheelView) findViewById(R.id.id_city);
        final WheelView areaWheelView = (WheelView) findViewById(R.id.id_area);
        if (area) {
            areaWheelView.setVisibility(View.VISIBLE);
        }
        TextView btBeDown = (TextView) findViewById(R.id.tv_confirm);
        TextView btCancel = (TextView) findViewById(R.id.tv_cancel);
        final Province defaultProvince = provinces.get(provincePosition);
        final City defaultCity = defaultProvince.city.get(cityPosition);
        final String defaultArea = defaultCity.area.get(areaPosition);
        cities = defaultProvince.city;
        final AreaWheelViewAdapter areaWheelViewAdapter = new AreaWheelViewAdapter(context, defaultCity.area);
        areaWheelView.setViewAdapter(areaWheelViewAdapter);
        areaWheelView.setCurrentItem(areaPosition);
        areaWheelViewAdapter.setSelectPosition(areaPosition);
        selectedArea = defaultCity.area.get(areaPosition);
        areaWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                areaWheelViewAdapter.setSelectPosition(newValue);
            }
        });

        final CityWheelViewAdapter cityWheelViewAdapter = new CityWheelViewAdapter(context, defaultProvince.city);
        cityWheelView.setViewAdapter(cityWheelViewAdapter);
        cityWheelView.setCurrentItem(cityPosition);
        cityWheelViewAdapter.setSelectPosition(cityPosition);
        selectedCity = defaultProvince.city.get(cityPosition);
        cityWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                cityWheelViewAdapter.setSelectPosition(newValue);
                areaWheelViewAdapter.setAreaList(cities.get(newValue).area);
                areaWheelView.setViewAdapter(areaWheelViewAdapter);
                areaWheelView.setCurrentItem(0);
            }
        });

        final ProvinceWheelViewAdapter provinceWheelViewAdapter = new ProvinceWheelViewAdapter(context, provinces);
        provinceWheelView.setViewAdapter(provinceWheelViewAdapter);
        provinceWheelView.setCurrentItem(provincePosition);
        provinceWheelViewAdapter.setSelectPosition(provincePosition);
        selectedProvince = defaultProvince;
        provinceWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                provinceWheelViewAdapter.setSelectPosition(newValue);
                cities = provinces.get(newValue).city;
                cityWheelViewAdapter.setCityList(cities);
                cityWheelView.setViewAdapter(cityWheelViewAdapter);
                cityWheelView.setCurrentItem(0);
                areaWheelViewAdapter.setAreaList(cities.get(0).area);
                areaWheelView.setViewAdapter(areaWheelViewAdapter);
                areaWheelView.setCurrentItem(0);
            }
        });
        btBeDown.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                areaPosition = areaWheelViewAdapter.getSelectPosition();
                cityPosition = cityWheelViewAdapter.getSelectPosition();
                provincePosition = provinceWheelViewAdapter.getSelectPosition();
                selectedProvince = provinces.get(provincePosition);
                if (selectedProvince.city != null && !selectedProvince.city.isEmpty()) {
                    selectedCity = selectedProvince.city.get(cityPosition);
                    if (selectedCity.area != null && !selectedCity.area.isEmpty()) {
                        selectedArea = selectedCity.area.get(areaPosition);
                    }
                }
                setCityName(defaultProvince, defaultCity, defaultArea);
                dismiss();
            }
        });

        btCancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                selectedArea = null;
                selectedCity = null;
                selectedProvince = null;
                dismiss();
            }
        });

    }


    /**
     * 显示城市名称
     *
     * @param defaultProvince
     * @param defaultCity
     */
    public void setCityName(Province defaultProvince, City defaultCity, String defaultArea) {

        if (selectedCity != null && selectedProvince != null && selectedArea != null) {
            currentProvince = selectedProvince;
            currentCity = selectedCity;
            currentArea = selectedArea;
        } else {
            currentProvince = defaultProvince;
            currentCity = defaultCity;
            currentArea = defaultArea;
        }
        selectProvince = currentProvince.name;
        selectCity = currentCity.name;
        selectArea = currentArea;
        if (callBack != null)
            callBack.sure(selectProvince, selectCity);
        if (areaCallBack != null) {
            areaCallBack.sure(selectProvince, selectCity, selectArea);
        }
    }
//
//    /**
//     * 显示城市名称
//     *
//     * @param defaultProvince
//     * @param defaultCity
//     */
//    public void setCityName(TextView textView, Province defaultProvince, City defaultCity) {
//
//        if (selectedCity != null && selectedProvince != null) {
//            currentProvince = selectedProvince;
//            currentCity = selectedCity;
//        } else {
//            currentProvince = defaultProvince;
//            currentCity = defaultCity;
//        }
//
////        if (currentCity != null) {
////            if (currentProvince.name.equals(currentCity.CityName)) {
////                selectProvince = currentProvince.ProName;
////                if (textView != null)
////                    textView.setText(currentProvince.ProName);
////            } else {
////                selectCity = currentCity.CityName;
////                selectProvince = currentProvince.ProName;
////
////                if (textView != null)
////                    textView.setText(selectProvince + "  " + selectCity);
////            }
////        } else {
////            selectProvince = currentProvince.ProName;
////            if (textView != null)
////                textView.setText(selectProvince);
////        }
//        if (callBack != null)
//            callBack.sure(currentProvince.ProName, currentCity.CityName);
//    }

    public interface CityPickCallBack {
        void sure(String currentProvince, String currentCity);
    }

    public interface AreaCallBack {
        void sure(String currentProvince, String currentCity, String currentArea);
    }
}
