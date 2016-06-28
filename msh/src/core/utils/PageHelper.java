package core.utils;

/**
 * @author Tang Yong Di
 * @date 2015/5/12
 */
public class PageHelper {
    public static int pageSize = 12;

    public static int getPageCount(int recordCount) {

        int pageCount = recordCount / PageHelper.pageSize;
        if (recordCount % PageHelper.pageSize > 0) {
            pageCount += 1;
        }

        return pageCount;

    }

    public static int getPageCount(int recordCount, int size) {
        if(size == 0) {
            return 0;
        }
        int pageCount = recordCount / size;
        if (recordCount % size > 0) {
            pageCount += 1;
        }

        return pageCount;

    }

    public static int getStart(int pageNum) {
        int startOffset = (pageNum - 1) * PageHelper.pageSize;
        return startOffset;
    }

    public static int checkCurrentPage(int currentPage, int pageCount) {

        if (currentPage < 1)
            currentPage = 1;

        if (currentPage > pageCount && pageCount > 0)
            currentPage = pageCount;

        return currentPage;
    }
}
