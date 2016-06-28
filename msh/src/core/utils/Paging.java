package core.utils;

import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2015/5/12
 */
public interface Paging<T> {

    public int getPageSize();

    public int getCurrentPage();

    public long getTotalCount();

    public int getPageCount();

    public List<T> getList();

    public boolean isHasPreviousPage();

    public boolean isHasNextPage();

    public int getPreviousPage();

    public int getNextPage();

    public int getLastPage();

    public boolean isQueryAll();
}
