package core.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URL;

/**
 * ehcache缓存工具类
 * @author Tang Yong Di
 * @date 2016/4/7
 */
public class EhcacheUtils {

    private static CacheManager cacheManager;
    private static Cache systemSample;

    private static CacheManager getCacheManager() {
        try {
            URL url = new ClassPathResource("spring/ehcache.xml").getURL();
            if (cacheManager == null) cacheManager = CacheManager.create(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacheManager;
    }

    private static Cache getSystemSample() {
        if (systemSample == null) systemSample = getCacheManager().getCache("systemCache");
        return systemSample;
    }

    public static Object getCacheObject(String key) {
        Element element = getSystemSample().get(key);
        return element == null ? null : element.getObjectValue();
    }

    public static void putCacheObject(String key, Object value) {
        Element element = new Element(key, value);
        getSystemSample().put(element);
    }

    public static void removeCacheObject(String key) {
        getSystemSample().remove(key);
    }

    public static void removeAll() {
        getSystemSample().removeAll();
    }

    public static void destroy() {
        getSystemSample().dispose();
        getCacheManager().shutdown();
    }

    public static void main(String[] args) {
        CacheManager cacheManager = CacheManager.create("d://ehcache.xml");
        Cache sample = cacheManager.getCache("systemCache");

        for (int i = 0; i < 10; i++) {
            Element result = sample.get("key");
            if (result == null) {
                Element element = new Element("key", "val");
                sample.put(element);
                System.out.println("没有缓存");
            } else {
                System.out.println("有缓存");
            }
        }
        //sample.remove("key");
        //sample.removeAll();
    }
}
