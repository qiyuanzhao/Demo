package boot.project;


import boot.pojo.Weixin;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class RequestDemo {

    private Logger logger = LoggerFactory.getLogger(RequestDemo.class);

    @Autowired
    private RestTemplate restTemplate;

    private static String startUrl = "http://service.lavector.com/api/v1/messages?&sites=weixin&projectId=494&sort=latest&from=20180901&to=20190301&pageNum=";
    private static String endUrl = "&numToLoad=500";

    public void downloder() {
        int code = 1;
        File file = getFile("G:/text/newWeixin/weixin", "sk-II.txt");

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));

            FileInputStream fis = new FileInputStream(file);
            int available = fis.available();
            if (available == 0) {
                String title = "类型,用户,省份,时间,转发数,评论数,点赞数,情感指数,标题,链接,内容";
                printWriter.println(title);
            }
            while (true) {
                String url = startUrl + code + endUrl;
                Weixin[] forObject = restTemplate.getForObject(url, Weixin[].class);
                if (forObject.length <= 0) {
                    break;
                } else {
                    handleData(Arrays.asList(forObject), printWriter);
                    code++;
                }
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleData(List<Weixin> wexinData, PrintWriter printWriter) {
        for (Weixin weixin : wexinData) {
            logger.info("开始写入文件....");
            if (weixin.getType() != null) {
                printWriter.print(handleType(weixin.getType()) + ",");
            }else {
                printWriter.print("" + ",");
            }
            printWriter.print(weixin.getUser().getDisplayName() + ",");
            if (weixin.getProvince() != null) {
                printWriter.print(weixin.getProvince() + ",");
            }else {
                printWriter.print("" + ",");
            }
            printWriter.print(handleTime(weixin.gettC()) + ",");

            if (weixin.getReposts() != null) {
                printWriter.print(weixin.getReposts() + ",");
            }else {
                printWriter.print("" + ",");
            }
            if (weixin.getComments() != null) {
                printWriter.print(weixin.getComments() + ",");
            }else {
                printWriter.print("" + ",");
            }
            if (weixin.getLikes() != null) {
                printWriter.print(weixin.getLikes() + ",");
            }else {
                printWriter.print("" + ",");
            }
            if (weixin.getpScore() != null) {
                printWriter.print(weixin.getpScore() + ",");
            }else {
                printWriter.print("" + ",");
            }
            printWriter.print(weixin.getTitle() + ",");
            printWriter.print(weixin.getUrl() + ",");
            printWriter.println(handleContent(weixin.getContent()));
            logger.info("完成一条的写入....");
        }
    }

    private String handleContent(String content) {
        String replace = content.replace("\n", "");
        return replace;
    }

    private String handleType(String type) {
        if ("Post".equals(type)) {
            return "原贴";
        } else if ("RePost".equals(type)) {
            return "转发";
        }
        return "";
    }

    private String handleTime(Long aLong) {
        Date date = new Date(aLong);
        String formatDate = DateUtils.formatDate(date, "yyyy-MM-dd");
        return formatDate;
    }


    //检查是否存在
    private File getFile(String path, String name) {
        File file = new File(path, name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


}
