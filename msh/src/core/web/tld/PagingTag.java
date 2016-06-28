package core.web.tld;

import core.utils.Paging;
import core.utils.ValidationUtils;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
public class PagingTag extends SimpleTagSupport {

	final static String FIRST = "首页";
	final static String PREVIOUS = "上页";
	final static String NEXT = "下页";
	final static String LAST = "末页";
	final static String GO = "跳转";

	/**
	 * 分页接口
	 */
	private Paging paging;

	/**
	 * 表单名
	 */
	private String formName;

	/**
	 * baseURL
	 */
	private String baseUrl;

	/**
	 * 如果设置了该属性，会将默认的<i>formName</i>属性替换掉
	 */
	private String url = "";

	/**
	 * targetId，用于窗口中ajax提交
	 */
	private String targetId = "";

	private String cssClass = "text-right";

	private boolean simple;

	private Integer size = 5;

	@Override
	public void doTag() throws JspException, IOException {
		// 如果列表为空 或 页数小于两页时 不生成分页标签
		boolean showBar = true;
		if (paging.getList().isEmpty() || paging.getPageCount() < 2) {
			showBar = false;
		}

		JspContext context = getJspContext();
		JspWriter writer = context.getOut();

		Integer pageCount = paging.getPageCount();
		Integer currentPageNumber = paging.getCurrentPage();

		writer.print("<nav class=\"" + cssClass + "\"><ul class=\"pagination\">");

		if (showBar) {
			if (!simple) {
				if (currentPageNumber == 1) {
					writer.print(disableButton(FIRST));
				} else {
					writer.print(pageButton(FIRST, 1));
				}
			}

			if (paging.isHasPreviousPage()) {
				writer.print(pageButton(PREVIOUS, paging.getPreviousPage()));
			} else {
				writer.print(disableButton(PREVIOUS));
			}

			if (!simple) {
				int start = 1, end = pageCount;
				int left = size / 2, right = size % 2 == 0 ? left - 1 : left;
				if (pageCount > size) {
					if (currentPageNumber > left && pageCount - right > currentPageNumber) {
						start = currentPageNumber - left;
						end = currentPageNumber + right;
					} else {
						if (currentPageNumber > left) {
							start = pageCount > size ? pageCount - size + 1 : currentPageNumber - left;
							end = pageCount;
						} else if (pageCount - right > currentPageNumber) {
							start = 1;
							end = pageCount > size ? size : currentPageNumber + right;
						}
					}
				}

				for (int i = start; i <= end; i++) {
					if (i == currentPageNumber) {
						writer.print(currentButton(i));
					} else {
						writer.print(pageButton(String.valueOf(i), i));
					}
				}
			}

			if (paging.isHasNextPage()) {
				writer.print(pageButton(NEXT, paging.getNextPage()));
			} else {
				writer.print(disableButton(NEXT));
			}

			if (!simple) {
				if (currentPageNumber.equals(paging.getLastPage())) {
					writer.print(disableButton(LAST));
				} else {
					writer.print(pageButton(LAST, paging.getLastPage()));
				}
			}
		}

		if (!simple) {
			writer.print("<li><span class=\"pagination-total\">共查询到 " + paging.getTotalCount() + " 条记录</span></li>");
		}

		writer.print("</ul></nav>");
	}

	private String currentButton(int page) {
		return "<li class=\"active\"><a href=\"javascript:void(0)\">" + page + "</a></li>";
	}

	private String pageButton(String name, int page) {
		if (!ValidationUtils.isEmpty(formName)) {
			return "<li><a href=\"javascript:searchByPageNumber($('#" +
					formName + "'), '" + page + "','" + url + "','" + targetId + "')\" title=" + "\"" + name + "\"" + ">" + name + "</a></li>";
		}
		return "";
	}

	private String disableButton(String name) {
		return "<li class=\"disabled\"><a href=\"javascript:void(0)\" title=" + "\"" + name + "\"" + ">" + name + "</a></li>";
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
