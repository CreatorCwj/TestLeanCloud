package com.util;

import com.model.Area;
import com.model.Category;
import com.model.CitySiftModel;

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

    public static List<CitySiftModel> getCities() {
        List<CitySiftModel> list = new ArrayList<>();
        list.add(new CitySiftModel(0, "北京", "beijing"));
        list.add(new CitySiftModel(1, "天津", "tianjin"));
        list.add(new CitySiftModel(2, "上海", "shanghai"));
        list.add(new CitySiftModel(3, "广州", "guangzhou"));
        list.add(new CitySiftModel(4, "石家庄", "shijiazhuang"));
        list.add(new CitySiftModel(5, "河南", "henan"));
        list.add(new CitySiftModel(6, "四川", "sichuan"));
        list.add(new CitySiftModel(7, "湖南", "hunan"));
        list.add(new CitySiftModel(8, "北京", "beijing"));
        list.add(new CitySiftModel(9, "天津", "tianjin"));
        list.add(new CitySiftModel(10, "上海", "shanghai"));
        list.add(new CitySiftModel(11, "广州", "guangzhou"));
        list.add(new CitySiftModel(12, "石家庄", "shijiazhuang"));
        list.add(new CitySiftModel(13, "河南", "henan"));
        list.add(new CitySiftModel(14, "四川", "sichuan"));
        list.add(new CitySiftModel(15, "湖南", "hunan"));
        list.add(new CitySiftModel(16, "北京", "beijing"));
        list.add(new CitySiftModel(17, "天津", "tianjin"));
        list.add(new CitySiftModel(18, "上海", "shanghai"));
        list.add(new CitySiftModel(19, "广州", "guangzhou"));
        list.add(new CitySiftModel(20, "石家庄", "shijiazhuang"));
        list.add(new CitySiftModel(21, "河南", "henan"));
        list.add(new CitySiftModel(22, "四川", "sichuan"));
        list.add(new CitySiftModel(23, "湖南", "hunan"));
        return list;
    }

    public static List<Category> getCategory() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(101, "电影", -1));
        categories.add(new Category(102, "KTV", -1));
        categories.add(new Category(103, "美食", -1));
        categories.add(new Category(104, "游乐场", -1));
        categories.add(new Category(105, "酒店", -1));
        categories.add(new Category(106, "汽车服务", -1));
        categories.add(new Category(107, "摄影", -1));
        categories.add(new Category(108, "美容美发", -1));
        categories.add(new Category(109, "婚庆", -1));
        categories.add(new Category(110, "外卖", -1));
        categories.add(new Category(111, "鲜果", -1));
        categories.add(new Category(112, "摄影", -1));
        categories.add(new Category(113, "美容美发", -1));
        categories.add(new Category(114, "婚庆", -1));
        categories.add(new Category(115, "外卖", -1));
        categories.add(new Category(116, "鲜果", -1));
        return categories;
    }

    public static List<Category> getSubCategory(int parentId) {
        //生成所有的次级品类
        List<Category> origins = new ArrayList<>();
        origins.add(new Category(117, "西餐", 103));
        origins.add(new Category(118, "快餐", 103));
        origins.add(new Category(119, "中餐", 103));
        origins.add(new Category(120, "便捷式", 105));
        origins.add(new Category(121, "高级宾馆", 105));
        origins.add(new Category(122, "洗车服务", 106));
        origins.add(new Category(123, "配件专卖", 106));
        origins.add(new Category(124, "保养服务", 106));
        //获取
        List<Category> subCategories = new ArrayList<>();
        for (Category category : origins) {
            if (category.getParentId() == parentId)
                subCategories.add(category);
        }
        return subCategories;
    }

    public static List<Area> getArea() {
        List<Area> categories = new ArrayList<>();
        categories.add(new Area(101, "北京", -1));
        categories.add(new Area(102, "天津", -1));
        categories.add(new Area(103, "保定", -1));
        categories.add(new Area(104, "上海", -1));
        categories.add(new Area(105, "广州", -1));
        categories.add(new Area(106, "安阳", -1));
        categories.add(new Area(107, "合肥", -1));
        categories.add(new Area(108, "西安", -1));
        categories.add(new Area(109, "杭州", -1));
        categories.add(new Area(110, "广东", -1));
        categories.add(new Area(111, "南京", -1));
        categories.add(new Area(112, "长沙", -1));
        categories.add(new Area(113, "武汉", -1));
        return categories;
    }

    public static List<Area> getSubArea(int parentId) {
        //生成所有的次级品类
        List<Area> origins = new ArrayList<>();
        origins.add(new Area(117, "密云", 101));
        origins.add(new Area(118, "朝阳", 101));
        origins.add(new Area(119, "怀柔", 101));
        origins.add(new Area(120, "西青", 102));
        origins.add(new Area(121, "南开", 102));
        origins.add(new Area(122, "蒲城", 108));
        origins.add(new Area(123, "华阴", 108));
        origins.add(new Area(124, "老城区", 108));
        //获取
        List<Area> subCategories = new ArrayList<>();
        for (Area category : origins) {
            if (category.getParentId() == parentId)
                subCategories.add(category);
        }
        return subCategories;
    }
}
