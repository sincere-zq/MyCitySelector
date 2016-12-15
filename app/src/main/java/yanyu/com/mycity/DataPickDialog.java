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

import java.util.Calendar;

import yanyu.com.mycity.wheel.DateWheelPicker;

/**
 * Created by Administrator on 2016/10/19.
 */

public class DataPickDialog extends Dialog {
    private Context context;
    private int currentYear, currentMonth, currentDay;
    private int selectYear, selectMonth, selectDay;


    public DataPickDialog(Context context) {
        super(context);
        this.context = context;
        Calendar calendar = Calendar.getInstance();
        selectYear = calendar.get(Calendar.YEAR);
        selectMonth = calendar.get(Calendar.MONTH) + 1;
        selectDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_data_pick);
    }

    public void dialog(final TextView textView) {
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        this.show();
        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = d.getWidth() * 5 / 6;
        lp.height = d.getHeight() * 3 / 5;
        window.setAttributes(lp);
        DateWheelPicker dpicker = (DateWheelPicker) findViewById(R.id.datepicker_layout);
        TextView btBeDown = (TextView) findViewById(R.id.datepicker_btsure);
        TextView btCancel = (TextView) findViewById(R.id.datepicker_btcancel);
        dpicker.setOnChangeListener(new DateWheelPicker.OnChangeListener() {
            @Override
            public void onChange(int year, int month, int day, int day_of_week) {
                currentYear = year;
                currentMonth = month;
                currentDay = day;
            }
        });

        dpicker.setDefaultDate(selectYear, selectMonth, selectDay);

        btBeDown.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                selectYear = currentYear;
                selectMonth = currentMonth;
                selectDay = currentDay;
                String data = "";
                if (selectMonth < 10 && selectDay < 10) {
                    data = selectYear + "-0" + selectMonth + "-0" + selectDay;
                } else if (selectMonth >= 10 && selectDay < 10) {
                    data = selectYear + "-" + selectMonth + "-0" + selectDay;
                } else if (selectMonth < 10 && selectDay >= 10) {
                    data = selectYear + "-0" + selectMonth + "-" + selectDay;
                } else {
                    data = selectYear + "-" + selectMonth + "-" + selectDay;
                }
                textView.setText(data);
                dismiss();
            }
        });

        btCancel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
