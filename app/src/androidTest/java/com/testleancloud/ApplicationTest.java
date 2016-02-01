package com.testleancloud;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.util.DateUtils;

import java.util.Date;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    String TAG = "DateUtils";

    public ApplicationTest() {
        super(Application.class);
    }

    public void test() {
        Date current = DateUtils.getDate();
        Date future = DateUtils.getDate(2016, 1, 5, 10, 50, 0);
        long diff = DateUtils.getDateDifference(current, future);
        Log.i(TAG, DateUtils.getTimeString(current) + " " + DateUtils.getTimeString(future) + ":" + diff);
    }
}