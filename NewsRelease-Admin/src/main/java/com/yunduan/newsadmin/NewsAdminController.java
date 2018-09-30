package com.yunduan.newsadmin;

import com.jfinal.core.Controller;
import com.yunduan.common.model.News;

/**
 * @author 豆璐璐
 * @description
 * @company 易单科技
 * @date 2018/9/28 0028 17:57
 * @Version 1.0
 */
public class NewsAdminController extends Controller {

    NewsAdminService service = NewsAdminService.me;

    /**
     * 后台新闻管理首页  默认方法index方法
     */
    public void index(){

        setAttr("NewsPage", service.paginate(getParaToInt(0, 1), 5));
        render("/newsadmin/newsAdmin.html");
    }

    /**
     * 搜索功能
     */
    public void search() throws Exception {

        setAttr("NewsPage",
                service.search(
                            getParaToInt(0, 1), 5,
                            getPara("title"),
                            getPara("author"),
                            getPara("starttime"),
                            getPara("endtime"),
                            getParaToInt("state")
                        )
                );

        render("/newsadmin/newsAdmin.html");
    }

    /**
     * 跳转到 添加新闻功能页面
     */

    public void add(){

        render("/newsadmin/add.html");
    }

    /**
     * 跳转到 修改功能页面
     */
    public void edit(){

        setAttr("news", service.findById(getParaToInt()));
        render("/newsadmin/edit.html");
    }

    /**
     * 修改新闻
     */
    public void update(){

        getBean(News.class).update();
        redirect("/newsAdmin");
    }

    /**
     * 添加新闻
     */
    public void save(){

        getBean(News.class).save();
        redirect("/newsAdmin");
    }
}