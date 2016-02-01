package com.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cwj on 16/1/20.
 */
public class MockData {

    private static List<String> data = getData();

    private static List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("http://img2.3lian.com/2014/f5/158/d/88.jpg");
        data.add("http://img0.imgtn.bdimg.com/it/u=3710103736,733712610&fm=21&gp=0.jpg");
        data.add("http://bizhi.cnanzhi.com/upload/bizhi/2014/1202/14175105546707.jpg");
        data.add("http://img.woyaogexing.com/touxiang/nv/20140212/9ac2117139f1ecd8!200x200.jpg");
        data.add("http://img3.imgtn.bdimg.com/it/u=2903672134,3248845875&fm=21&gp=0.jpg");
        data.add("http://img4.imgtn.bdimg.com/it/u=351229423,2450736669&fm=21&gp=0.jpg");
        data.add("http://img2.3lian.com/2014/f5/158/d/91.jpg");
        data.add("http://img0.imgtn.bdimg.com/it/u=1197684875,2004955903&fm=21&gp=0.jpg");
        data.add("http://wyxgroup-img.stor.appsina.com/42fbfa02665fc4999cc14ae04cae753f.jpg");

        data.add("http://img2.3lian.com/2014/f5/158/d/88.jpg");
        data.add("http://img0.imgtn.bdimg.com/it/u=3710103736,733712610&fm=21&gp=0.jpg");
        data.add("http://img1.imgtn.bdimg.com/it/u=1853916932,391038869&fm=21&gp=0.jpg");
        data.add("http://img3.imgtn.bdimg.com/it/u=2903672134,3248845875&fm=21&gp=0.jpg");
        data.add("http://img4.imgtn.bdimg.com/it/u=351229423,2450736669&fm=21&gp=0.jpg");
        data.add("http://img2.3lian.com/2014/f5/158/d/91.jpg");
        data.add("http://img0.imgtn.bdimg.com/it/u=1197684875,2004955903&fm=21&gp=0.jpg");
        data.add("http://ww1.sinaimg.cn/mw600/658ff6c8gw1e3cfqowo5cj.jpg");
        data.add("http://wyxgroup-img.stor.appsina.com/42fbfa02665fc4999cc14ae04cae753f.jpg");
        return data;
    }

    public static List<String> getImageUrls(int skipCount, int limit) {
        List<String> urls = new ArrayList<>();
        for (int i = skipCount; i < data.size(); ++i) {
            if (urls.size() >= limit)
                break;
            urls.add(data.get(i));
        }
        return urls;
    }
}
