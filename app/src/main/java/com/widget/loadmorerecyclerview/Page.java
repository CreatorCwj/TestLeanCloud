package com.widget.loadmorerecyclerview;

/**
 * Created by cwj on 16/1/19.
 * 分页类,有待完善
 */
public class Page {

    public static final int FIRST_NO = 0;
    public static final int DEFAULT_SIZE = 15;

    private int pageNo;
    private int pageSize;

    public Page() {
        this.pageNo = FIRST_NO;
        this.pageSize = DEFAULT_SIZE;
    }

    /**
     * 重置为首页
     */
    public Page reset() {
        this.pageNo = FIRST_NO;
        return this;
    }

    /**
     * 后一页
     *
     * @return
     */
    public Page nextPage() {
        ++this.pageNo;
        return this;
    }

    /**
     * 有效的前一页
     *
     * @return
     */
    public Page prePage() {
        if (this.pageNo > FIRST_NO)
            --this.pageNo;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
