package com.yunduan.newsweb;

import com.jfinal.core.Controller;

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

    public void index(){
        setAttr("NewsPage", service.paginate(getParaToInt(0, 1), 10));
        render("/newsweb/news.html");
    }

    /**
     * 根据新闻编号 获取新闻详情
     */
    public void detail(){

        setAttr("newDetail", service.findById(getParaToInt()));
        render("/newsweb/detail.html");
    }

}