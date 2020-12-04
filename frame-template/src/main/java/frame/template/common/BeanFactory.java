package frame.template.common;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public synchronized void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static Object getBean(String beanName) {
        Object object = null;
        try {
            object = applicationContext.getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            log.debug("��ȡspring�������쳣", e);
        }
        return object;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        Object object = null;
        try {
            object = applicationContext.getBean(clazz);
        } catch (NoSuchBeanDefinitionException e) {
            log.debug("��ȡspring�������쳣", e);
        }
        return (T) object;
    }
}
