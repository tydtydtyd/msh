package core.spring.instance;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Tang Yong Di
 * @date 2016/3/8
 */
public class InstanceFactory {

    private static InstanceProvider instanceProvider;

    public static <T> T getInstance(Class<T> beanClass) {
        return getInstanceProvider().getInstance(beanClass);
    }

    public static Object getInstance(String beanName) {
        return getInstanceProvider().getInstance(beanName);
    }

    public static <T> T getInstance(Class<T> beanClass, String beanName) {
        return getInstanceProvider().getInstance(beanClass, beanName);
    }

    /**
     * 获取注解产生的bean实例
     *
     * @param beanClass        bean类
     * @param beanFactoryClass 创建<T>bean的beanFactory类
     * @param <T>
     * @return
     */
    public static <T, E> T getAnnotationInstance(Class<T> beanClass, Class<E> beanFactoryClass) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(beanFactoryClass);
        return context.getBean(beanClass);
    }

    public static InstanceProvider getInstanceProvider() {
        return instanceProvider;
    }

    public static void setInstanceProvider(InstanceProvider instanceProvider) {
        InstanceFactory.instanceProvider = instanceProvider;
    }
}
