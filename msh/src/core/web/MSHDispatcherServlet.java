package core.web;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Tang Yong Di
 * @date 2016/3/3
 */
public class MSHDispatcherServlet extends DispatcherServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.service(req, res);
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
