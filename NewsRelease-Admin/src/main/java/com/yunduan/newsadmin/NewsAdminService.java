package com.yunduan.newsadmin;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.yunduan.common.model.News;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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

        String sql="from news where state ='"+state+"'";

        if(title!=null && title!=""){
           sql+=" or title like '%"+title+"%'";
        }
        if(author!=null && author!=""){
            sql+=" or author like '%"+author+"%'";
        }
        if(starttime!=null && starttime!=""){
            sql+=" or createtime = '"+starttime+"'";
        }
        if(endtime!=null && endtime!=""){
            sql+=" or createtime = '"+endtime+"'";
        }

        System.out.println(sql);
//        String sql="from news where title like '%"+title+"%' or author like '%"+author+"%'or createtime between '"+starttime+"' and '"+endtime+"' or state ='"+state+"'";
        return dao.paginate(pageNumber, pageSize, "select *", sql);
    }

    /**
     * 根据id 获取新闻详情
     */
    public News findById(int id) {
        return dao.findById(id);
    }


    /**
     * 文件拷贝
     */
    public void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;//文件输入流
        FileOutputStream fo = null;//文件输出流
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//获取读入的文件通道
            out = fo.getChannel();//获取写出的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，从文件输入流读取数据到文件输出流
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}