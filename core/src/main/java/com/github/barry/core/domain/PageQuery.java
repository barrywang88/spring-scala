package com.github.barry.core.domain;

import java.io.Serializable;

/**
 * 分页助手
 */
public class PageQuery extends BaseEntity implements Serializable {

    /** 默认每页条数 */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 当前页码
     */
    private int currentPage;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 分页开始位置
     */
    private int startIndex;

    /**
     * 总条数
     */
    private long totalCount;

    /**
     * 总页数
     */
    private int pageCount;

    public PageQuery() {
        this(DEFAULT_PAGE_SIZE);
    }

    public PageQuery(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        this.init();
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 初始化当前页码
     */
    private void initCurrentPage() {
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > pageCount) {
            currentPage = pageCount;
        }
    }


    /**
     * 初始化分类助手
     */
    private void init() {
        pageCount = (int) totalCount / pageSize;
        if (totalCount % pageSize > 0 || pageCount == 0) {
            pageCount++;
        }
        initCurrentPage();
        startIndex = (getCurrentPage() - 1) * pageSize;
    }

}
