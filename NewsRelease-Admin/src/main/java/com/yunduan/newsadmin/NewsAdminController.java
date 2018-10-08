package com.yunduan.newsadmin;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.yunduan.common.model.News;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
        UploadFile file = getFile();
        News news = getBean(News.class);
        if (null!= file){  //图片不为空时，进行文件上传
            String url = uploadFile();
            news.setPicture(url);
        }

        Date currentTime = new Date();
//        news.setCreatetime(new Date());  //创建时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        news.setCreatetime(formatter.format(currentTime));

        //更新内容
        news.update();

        redirect("/newsAdmin");
    }

    /**
     * 文件上传
     */
    public String uploadFile(){

//        UploadFile file1 = getFile("file");
        //文件上传
        UploadFile uploadFile = this.getFile();//获取前台上传文件对象
        String fileName = uploadFile.getOriginalFileName();//获取文件名
        File file = uploadFile.getFile();//获取文件对象
        String s = UUID.randomUUID().toString()+ file.getName().substring(file.getName().lastIndexOf("."));
        String url = "http://localhost:8083/res/" + s;
        File t = new File("E:\\imgupload\\" + s);//设置本地上传文件对象（并重命名）
        try {
            t.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        service.fileChannelCopy(file, t);//将上传的文件的数据拷贝到本地新建的文件
        file.delete();
        this.renderHtml("success,<a href=\"./\">back</a>");
        return url;
    }

    /**
     * 添加新闻
     */
    public void save(){

        UploadFile file = getFile();
        News news = getBean(News.class);
        if (null!= file){  //图片不为空时，进行文件上传
            String url = uploadFile();
            news.setPicture(url);
        }

//        news.setCreatetime(new Date());  //创建时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        news.setCreatetime(formatter.format(currentTime));
        //新增新闻
        news.save();
//        getBean(News.class).save();
        redirect("/newsAdmin");
    }
}