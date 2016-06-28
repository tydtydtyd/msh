package core.spring;

import core.spring.instance.InstanceProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Tang Yong Di
 * @date 2016/3/8
 */
public class SpringProvider implements InstanceProvider {

    private ApplicationContext applicationContext;

    private SpringProvider(String... configLocations) {
        this.applicationContext = new ClassPathXmlApplicationContext(configLocations);
    }

    public SpringProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> beanClass) {
        String[] beanNames = applicationContext.getBeanNamesForType(beanClass);
        if (beanNames.length == 0) {
            return null;
        }
        return (T) applicationContext.getBean(beanNames[0]);
    }

    @Override
    public <T> T getInstance(Class<T> beanClass, String beanName) {
        return applicationContext.getBean(beanName, beanClass);
    }

    @Override
    public Object getInstance(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
