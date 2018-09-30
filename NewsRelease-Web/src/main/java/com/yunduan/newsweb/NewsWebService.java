package com.yunduan.newsweb;

import com.jfinal.plugin.activerecord.Page;
import com.yunduan.common.model.News;

/**
 * @author 豆璐璐
 * @description
 * @company 易单科技
 * @date 2018/9/27 0027 15:45
 * @Version 1.0
 */
public class NewsWebService {

    /**
     * 线程安全的 Service 可以开放一个 static me 变量，方便随处使用
     */
    public static final NewsWebService me = new NewsWebService();

    /**
     * 所有的 dao 对象也放在 Service 中，并且声明为 private，避免 sql 满天飞
     * sql 只放在业务层，或者放在外部 sql 模板，用模板引擎管理：
     * 			http://www.jfinal.com/doc/5-13
     */
    private News dao = new News().dao();

    /***
     * 查询所有新闻信息
     * @param pageNumber  当前页数
     * @param pageSize 每页条数
     * @return
     */
    public Page<News> paginate(int pageNumber, int pageSize) {
        return dao.paginate(pageNumber, pageSize, "select *", "from news where state='1' order by id asc");
    }

    /**
     * 根据新闻编号获取新闻详情
     * @param id 新闻编号
     * @return
     */
    public News findById(int id) {
        return dao.findById(id);
    }

}