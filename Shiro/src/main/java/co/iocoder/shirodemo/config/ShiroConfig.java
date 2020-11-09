package co.iocoder.shirodemo.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm() {
        //创建SimpleAccountRealm对象
        SimpleAccountRealm realm = new SimpleAccountRealm();
        //添加两个用户.参数分别是username,password,roles.
        realm.addAccount("admin", "admin", "ADMIN");
        realm.addAccount("normal", "normal", "NORMAL");
        return realm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        //创建DefaultWebSecurityManager对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置其使用Realm
        securityManager.setRealm(this.realm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        //创建ShiroFilterFactoryBean对象,用于创建ShiroFilter过滤器
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        //设置securityManager
        filterFactoryBean.setSecurityManager(this.securityManager());

        //设置RUL们
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/login_success");
        filterFactoryBean.setUnauthorizedUrl("unauthorized");

        //设置RUL的权限配置
        filterFactoryBean.setFilterChainDefinitionMap(this.filterChainDefinitionMap());

        return filterFactoryBean;
    }

    private Map<String, String> filterChainDefinitionMap() {
        Map<String, String> filterMap = new LinkedHashMap<>();//使用有序的LinkedHashMap 顺序匹配
        filterMap.put("/test/echo", "anon"); //允许匿名访问
        filterMap.put("/test/admin", "roles[ADMIN]");//需要ADMIN角色
        filterMap.put("/test/normal", "roles[NORMAL]");
        filterMap.put("/logout", "logout");
        filterMap.put("/**", "authc"); //其它URL,需要认证(authentication)
        return filterMap;
    }

}
