package com.yunduan.common;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.yunduan.common.model._MappingKit;
import com.yunduan.newsadmin.NewsAdminController;

/**
 * @author 豆璐璐
 * @description
 * @company 易单科技
 * @date 2018/9/28 0028 18:00
 * @Version 1.0
 */
public class NewsAdminConfig extends JFinalConfig {
    /**
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     *
     * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
     * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
     * -XX:PermSize=64M -XX:MaxPermSize=256M
     */
    public static void main(String[] args) {

        /**
         * 特别注意：Eclipse 之下建议的启动方式
         */
        //JFinal.start("src/main/webapp", 80, "/", 5);

        /**
         * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
         */
        JFinal.start("src/main/webapp", 81, "/");
    }

    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants me) {

        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("config.properties");
        me.setDevMode(PropKit.getBoolean("devMode", false));

        //设置开发模式
        //me.setDevMode(true);
    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes me) {
        me.add("/newsAdmin", NewsAdminController.class);
    }

    /**
     * 配置引擎
     */
    @Override
    public void configEngine(Engine me) {
        me.addSharedFunction("/common/_layout.html");
        me.addSharedFunction("/common/_paginate.html");
    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me) {

        // 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        me.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
         me.add(arp);
    }

    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    /**
     * 配置处理器
     */
    @Override
    public void configHandler(Handlers handlers) {

    }
}