package yanyu.com.mycity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ProvinceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        final TextView tv_pro = (TextView) findViewById(R.id.tv_pro);
        final TextView tv_area = (TextView) findViewById(R.id.tv_area);
        tv_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPickDialog dialog = new CityPickDialog(ProvinceActivity.this);
                dialog.setArea(true);
                dialog.setAreaCallBack(new CityPickDialog.AreaCallBack() {
                    @Override
                    public void sure(String currentProvince, String currentCity, String currentArea) {
                        tv_area.setText(currentProvince + "  " + currentCity + "  " + currentArea);
                    }
                });
            }
        });
        tv_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPickDialog dialog = new CityPickDialog(ProvinceActivity.this);
                dialog.setCallBack(new CityPickDialog.CityPickCallBack() {
                    @Override
                    public void sure(String currentProvince, String currentCity) {
                        tv_pro.setText(currentProvince + "  " + currentCity);
                    }
                });
            }
        });
    }
}
