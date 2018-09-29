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

    public void index(){

        render("/newsadmin/test.html");
    }
}