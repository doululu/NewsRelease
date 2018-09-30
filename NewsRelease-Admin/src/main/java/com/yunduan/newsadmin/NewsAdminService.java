package com.yunduan.newsadmin;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.yunduan.common.model.News;

import java.util.List;

/**
 * @author 豆璐璐
 * @description
 * @company 易单科技
 * @date 2018/9/28 0028 17:58
 * @Version 1.0
 */
public class NewsAdminService {

    /**
     * 线程安全的 Service 可以开放一个 static me 变量，方便随处使用
     */
    public static final NewsAdminService me = new NewsAdminService();

    /**
     * 所有的 dao 对象也放在 Service 中，并且声明为 private，避免 sql 满天飞
     * sql 只放在业务层，或者放在外部 sql 模板，用模板引擎管理：
     * 			http://www.jfinal.com/doc/5-13
     */
    private News dao = new News().dao();

    /**
     * 后台新闻展示
     */
    public Page<News> paginate(int pageNumber, int pageSize) {
        return dao.paginate(pageNumber, pageSize, "select *", "from news order by id asc");
    }

    /**
     * 搜索功能
     */
    public Page<News> search(int pageNumber, int pageSize,String title,String author,String starttime,String endtime,Integer state){

        String sql="from news where title like '%"+title+"%' and author like '%"+author+"%'and createtime between '"+starttime+"' and '"+endtime+"' and state ='"+state+"'";
        return dao.paginate(pageNumber, pageSize, "select *", sql);
    }

    /**
     * 根据id 获取新闻详情
     */
    public News findById(int id) {
        return dao.findById(id);
    }

}