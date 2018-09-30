package com.yunduan.newsadmin;

import com.jfinal.core.Controller;

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
//        render("/newsadmin/index666.html");
    }

    /**
     * 搜索功能
     */
    public void search(){

        String str="农";
        setAttr("NewsPage", service.search(getParaToInt(0, 1), 5,str));
        render("/newsadmin/newsAdmin.html");
    }

    /**
     * 新增功能
     */

    public  void add(){

        renderText("666666666");
    }
    /**
     * 修改功能
     */
}