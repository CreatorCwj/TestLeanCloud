package com.testleancloud;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.base.BaseActivity;
import com.util.DateUtils;
import com.util.Utils;

import java.util.Calendar;
import java.util.Date;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_date_picker)
public class DatePickerActivity extends BaseActivity {

    @InjectView(R.id.datePicker)
    private Button datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        datePicker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datePicker:
                openDatePicker();
                break;
        }
    }

    private void openDatePicker() {

        Date today = DateUtils.getDate();

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date date = DateUtils.getDate(year, monthOfYear, dayOfMonth);
                Utils.showToast(DatePickerActivity.this, DateUtils.getDateString(date));
            }
        };

        DatePickerDialog pickerDialog = new DatePickerDialog(this, listener, DateUtils.getValue(today, Calendar.YEAR), DateUtils.getValue(today, Calendar.MONTH), DateUtils.getValue(today, Calendar.DATE));
        pickerDialog.getDatePicker().setMinDate(today.getTime());
        pickerDialog.getDatePicker().setMaxDate(DateUtils.getFutureDate(60).getTime());
        pickerDialog.show();
    }
}
