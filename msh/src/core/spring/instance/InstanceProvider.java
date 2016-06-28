package core.spring.instance;

/**
 * @author Tang Yong Di
 * @date 2016/3/8
 */
public interface InstanceProvider {

    Object getInstance(String beanName);

    <T> T getInstance(Class<T> beanClass);

    <T> T getInstance(Class<T> beanClass, String beanName);

}
