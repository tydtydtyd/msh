package core.utils;

import com.msh.model.entity.Domain;

/**
 * @author Tang Yong Di
 * @date 2015/5/12
 */
public class BaseForm {
	/**
	 * 总记录数
	 */
	private long totalCount = 0;

	/**
	 * 当前页码
	 */
	private Integer currentPage = 1;

	/**
	 * 当前记录
	 */
	private Integer pageSize = 10;

	private Integer sumPage;

	private boolean queryAll;

	private String sortName;
	private String sortType;

	//DTO分页转实体分页
	public static <T extends Domain, E> Pagination<T> toPageDomain(Pagination<E> paginationDTO) {
		Pagination<T> pagination = new Pagination<>(paginationDTO.getTotalCount(), paginationDTO.getCurrentPage(), paginationDTO.getPageSize(),
				paginationDTO.getSumPage(), paginationDTO.isQueryAll(), paginationDTO.getSortName(), paginationDTO.getSortType());
		return pagination;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getSumPage() {
		return sumPage;
	}

	public void setSumPage(Integer sumPage) {
		this.sumPage = sumPage;
	}

	public boolean isQueryAll() {
		return queryAll;
	}

	public void setQueryAll(boolean queryAll) {
		this.queryAll = queryAll;
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
}
