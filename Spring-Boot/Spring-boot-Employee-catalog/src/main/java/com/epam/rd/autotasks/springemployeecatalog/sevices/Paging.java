package com.epam.rd.autotasks.springemployeecatalog.sevices;

public class Paging {
    public final int page;
    public final int itemPerPage;

    public Paging(final int page, final int itemPerPage) {
        this.page = page;
        this.itemPerPage = itemPerPage;
    }

}
