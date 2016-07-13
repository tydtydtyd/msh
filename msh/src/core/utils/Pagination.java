package core.utils;

import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2015/5/12
 */
public class Pagination<T> implements Paging {
	/**
	 * 总记录数
	 */
	private long totalCount = 0;

	/**
	 * 每页记录
	 */
	private List<T> list = new ArrayList<T>();

	/**
	 * sql查询出的每页记录:是以 List<Object[]>类型的list,在domain转DTO处理时，要记得将sqlList转换成DTO后赋值给list
	 */
	private List<Object[]> sqlList = new ArrayList<>();

	/**
	 * 当前页码
	 */
	private Integer currentPage = 1;

	/**
	 * 当前记录
	 */
	private Integer pageSize = 10;

	private Integer sumPage;

	private boolean queryAll = false;

	protected String sortName;
	protected String sortType;

	/**
	 * 设置json配置 设置转化为json时候忽略对象
	 *
	 * @param fileds
	 * @return
	 */
	public JsonConfig getJsonConfig(String... fileds) {
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(fileds);
		return config;
	}

	public Pagination(long totalCount, Integer currentPage, Integer pageSize, Integer sumPage, boolean queryAll, String sortName, String sortType) {
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.sumPage = sumPage;
		this.queryAll = queryAll;
		this.sortName = sortName;
		this.sortType = sortType;
	}

	public Pagination() {
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public Pagination<T> setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	/**
	 * 取当前页中的记录.
	 */
	@Override
	public List<T> getList() {
		return list;
	}

	/**
	 * @param list the rows to set
	 */
	public Pagination<T> setList(List<T> list) {
		this.list = list;
		return this;
	}


	/**
	 * 取总页数.
	 */
	@Override
	public int getPageCount() {
		if (pageSize == 0) {
			return 0;
		}
		int totalPage = (int) totalCount / pageSize;

		if (totalCount % pageSize == 0) {
			return totalPage;
		}
		return totalPage + 1;
	}

	public Pagination<T> setSumPage(Integer sumPage) {
		this.sumPage = sumPage;
		return this;
	}

	/**
	 * 取每页数据容量.
	 */
	@Override
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	@Override
	public int getCurrentPage() {
		return currentPage;
	}

	public Integer getSumPage() {
		return sumPage;
	}

	/**
	 * @param currentPage the page to set
	 */
	public Pagination<T> setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
		return this;
	}

	@Override
	public long getTotalCount() {
		return totalCount;
	}


	/**
	 * @param totalCount the total to set
	 */
	public Pagination<T> setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		return this;
	}


	/**
	 * 该页是否有下一页.
	 */
	@Override
	public boolean isHasNextPage() {
		return this.getCurrentPage() < this.getPageCount();
	}

	@Override
	public int getNextPage() {
		return this.getCurrentPage() + 1;
	}

	@Override
	public int getLastPage() {
		return this.getPageCount();
	}

	@Override
	public boolean isQueryAll() {
		return queryAll;
	}

	public void setQueryAll(boolean queryAll) {
		this.queryAll = queryAll;
	}

	/**
	 * 该页是否有上一页.
	 */
	@Override
	public boolean isHasPreviousPage() {
		return this.getCurrentPage() > 1;
	}

	@Override
	public int getPreviousPage() {
		return getCurrentPage() - 1;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public List<Object[]> getSqlList() {
		return sqlList;
	}

	public Pagination<T> setSqlList(List<Object[]> sqlList) {
		this.sqlList = sqlList;
		return this;
	}
}
