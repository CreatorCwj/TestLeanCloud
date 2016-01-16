package com.model;

import android.text.TextUtils;

/**
 * Created by cwj on 16/1/13.
 */
public class NowWeather {

    private String showapi_res_error;

    private Body showapi_res_body;

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public Body getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(Body showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    @Override
    public String toString() {
        return getShowapi_res_body().toString();
    }

    public class Body {

        private Now now;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        public class Now {
            private String temperature;
            private String weather;
            private String wind_direction;
            private String wind_power;
            private String weather_pic;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_power() {
                return wind_power;
            }

            public void setWind_power(String wind_power) {
                this.wind_power = wind_power;
            }

            public String getWeather_pic() {
                return weather_pic;
            }

            public void setWeather_pic(String weather_pic) {
                this.weather_pic = weather_pic;
            }

            @Override
            public String toString() {
                return "天气:" + weather + "\n温度:" + temperature + "\n风向:" + wind_direction + "\n风力:" + wind_power + "\n" + weather_pic;
            }
        }

        @Override
        public String toString() {
            if (!TextUtils.isEmpty(getRemark()))
                return getRemark();
            return getNow().toString();
        }
    }
}
