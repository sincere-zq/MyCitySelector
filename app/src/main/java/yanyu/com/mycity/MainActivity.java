package yanyu.com.mycity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import yanyu.com.mycity.wheel.OnWheelChangedListener;
import yanyu.com.mycity.wheel.WheelView;
import yanyu.com.mycity.wheel.adapter.AreaWheelViewAdapter;
import yanyu.com.mycity.wheel.adapter.CityWheelViewAdapter;
import yanyu.com.mycity.wheel.adapter.ProvinceWheelViewAdapter;

public class MainActivity extends AppCompatActivity {
    TextView tv_text;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_text = (TextView) findViewById(R.id.tv_text);
        provinces = AssetsUtil.getProvince(this, "city.json");
        tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProvinceAndCityDialog(provinces);
            }
        });

    }

    /**
     * 选择城市
     */
    private void showProvinceAndCityDialog(final List<Province> provinceList) {
        final View view = View.inflate(this, R.layout.dialog_province_and_city, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(tv_text, Gravity.BOTTOM, 0, 0);
        final WheelView provinceWheelView = (WheelView) view.findViewById(R.id.wheelView_province);
        final WheelView cityWheelView = (WheelView) view.findViewById(R.id.wheelView_city);
        final WheelView areaWheelView = (WheelView) view.findViewById(R.id.wheelView_area);

        final Province defaultProvince = provinceList.get(provincePosition);
        final City defaultCity = defaultProvince.city.get(cityPosition);
        final String defaultArea = defaultCity.area.get(areaPosition);
        cities = defaultProvince.city;
        final AreaWheelViewAdapter areaWheelViewAdapter = new AreaWheelViewAdapter(this, defaultCity.area);
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

        final CityWheelViewAdapter cityWheelViewAdapter = new CityWheelViewAdapter(this, defaultProvince.city);
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

        final ProvinceWheelViewAdapter provinceWheelViewAdapter = new ProvinceWheelViewAdapter(this, provinceList);
        provinceWheelView.setViewAdapter(provinceWheelViewAdapter);
        provinceWheelView.setCurrentItem(provincePosition);
        provinceWheelViewAdapter.setSelectPosition(provincePosition);
        selectedProvince = defaultProvince;
        provinceWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                provinceWheelViewAdapter.setSelectPosition(newValue);
                cities = provinceList.get(newValue).city;
                cityWheelViewAdapter.setCityList(cities);
                cityWheelView.setViewAdapter(cityWheelViewAdapter);
                cityWheelView.setCurrentItem(0);
                areaWheelViewAdapter.setAreaList(cities.get(0).area);
                areaWheelView.setViewAdapter(areaWheelViewAdapter);
                areaWheelView.setCurrentItem(0);
            }
        });
        view.findViewById(R.id.btn_select_city_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedArea = null;
                selectedCity = null;
                selectedProvince = null;
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.btn_select_city_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                areaPosition = areaWheelViewAdapter.getSelectPosition();
                cityPosition = cityWheelViewAdapter.getSelectPosition();
                provincePosition = provinceWheelViewAdapter.getSelectPosition();
                selectedProvince = provinceList.get(provincePosition);
                if (selectedProvince.city != null && !selectedProvince.city.isEmpty()) {
                    selectedCity = selectedProvince.city.get(cityPosition);
                    if (selectedCity.area != null && !selectedCity.area.isEmpty()) {
                        selectedArea = selectedCity.area.get(areaPosition);
                    }
                }
                setCityName(defaultProvince, defaultCity, defaultArea);
                popupWindow.dismiss();
            }
        });

        view.findViewById(R.id.ll_pop).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
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
        tv_text.setText(selectProvince + " " + selectCity + " " + selectArea);
    }
}
