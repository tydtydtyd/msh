package core.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Tang Yong Di
 * @date 2016/3/3
 */
public class ChineseFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (request.getMethod().equalsIgnoreCase("get")) {
			request = new GetHttpServletRequestWrapper(request, "UTF-8");
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
