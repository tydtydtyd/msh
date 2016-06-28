package core.web;

import core.cache.EhcacheUtils;
import core.spring.SpringProvider;
import core.spring.instance.InstanceFactory;
import core.spring.instance.InstanceProvider;
import core.sql.SqlUpdateHandler;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Tang Yong Di
 * @date 2016/3/3
 */
public class MSHContextLoaderListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(MSHContextLoaderListener.class);
    private final static ContextLoaderListener contextLoaderListener = new ContextLoaderListener();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        contextLoaderListener.contextInitialized(event);
        mshContextLoadInit(event);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        EhcacheUtils.destroy();
        contextLoaderListener.contextDestroyed(event);
    }

    private void mshContextLoadInit(ServletContextEvent event) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        InstanceProvider provider = new SpringProvider(context);
        InstanceFactory.setInstanceProvider(provider);

        sqlSynchronizeInit();
    }

    private void sqlSynchronizeInit() {
        log.info("Sql update: initialization started");
        long startTime = System.currentTimeMillis();
        SqlUpdateHandler handler = new SqlUpdateHandler();
        handler.handle();
        long endTime = System.currentTimeMillis();
        log.info("Sql update: initialization completed in " + (endTime - startTime) + "ms.");
    }
}
