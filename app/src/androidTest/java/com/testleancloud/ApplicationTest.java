package com.testleancloud;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test() {
        double progress = 1.267;
        progress = Double.parseDouble(String.format("%.0f", progress));
        System.out.println(progress + "");
    }
}