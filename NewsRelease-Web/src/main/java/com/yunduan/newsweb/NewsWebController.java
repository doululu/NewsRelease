package com.yunduan.newsweb;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

/**
 * @author 豆璐璐
 * @description
 * @company 易单科技
 * @date 2018/9/27 0027 11:36
 * @Version 1.0
 *
 * NewsWebController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class NewsWebController extends Controller {

    NewsWebService service = NewsWebService.me;

    /**
     * 查询所有新闻信息，列表展示
     * 默认走index方法
     */
    public void index(){

        setAttr("NewsPage", service.paginate(getParaToInt(0, 1), 10));
        render("/newsweb/news.html");
    }

    /**
     * 点我阅读 功能
     * 根据新闻编号 获取新闻详情
     */
    public void detail(){

        //获取数据库中 最初阅读数
        int readnum=service.findById(getParaToInt()).getReadnumber();
        boolean flag=false;  //判断session   false:没有阅读  true:已经阅读
        setSessionAttr("isRead",flag);

        if("ture".equals(getSessionAttr("isRead"))){ //已近阅读

        }else{ //没有阅读 阅读量+1
            readnum+=1;
            flag=true;
            //根据编号 更新阅读数
            Db.update("update news set readnumber="+readnum+" where id="+getParaToInt()+"");
        }

        //根据编号 显示新闻详情
        setAttr("newDetail", service.findById(getParaToInt()));
        render("/newsweb/detail.html");
    }

}