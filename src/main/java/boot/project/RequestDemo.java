package boot.project;


import boot.pojo.Weixin;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RequestDemo {

    private Logger logger = LoggerFactory.getLogger(RequestDemo.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    //资生堂精华 752    SK-II精华 755   海蓝之谜精华 756   莱珀妮精华 757  肌肤之钥精华 758  娇兰精华 759  科颜氏精华 760  兰蔻精华 770
    private String[] ids = {"752"};//, "", "", "", "", "", "", "", "", ""
    private String[] keywords = {"weixin","redbook", "tmall","weibo"};//,"weixin","redbook", "tmall","weibo"

    @Autowired
    private RestTemplate restTemplate;

    private static String startUrl = "http://service.lavector.com/api/v1/messages?&sites=";
    private static String endUrl = "&numToLoad=50";

    public void downloder() {
        PrintWriter printWriter = null;

        Map<String, String> map = new HashMap<>();
//        map.put("752", "资生堂精华");//20180506 weibo
//        map.put("755","SK-II精华");
//        map.put("770","兰蔻精华");

        map.put("756","海蓝之谜精华");
        map.put("757","莱珀妮精华");
        map.put("758","肌肤之钥精华");
        map.put("759","娇兰精华");
        map.put("760","科颜氏精华");
        map.put("761","git1");
        map.put("761","gittttt222");


        try {
            for (String id : map.keySet()) {
                for (String keyword : keywords) {
                    File file = getFile("G:/text/newWeixin/weixin", map.get(id) + "_" + keyword + ".txt");
                    printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));

                    FileInputStream fis = new FileInputStream(file);
                    int available = fis.available();
                    if (available == 0) {
                        String title = "类型,用户,省份,时间,转发数,评论数,点赞数,情感指数,发布来源,标题,链接,原文内容,内容";
                        printWriter.println(title);
                    }

                    Date start = simpleDateFormat.parse("20180301");
                    Date end = simpleDateFormat.parse("20180901");

                    LocalDate startLocalDate = LocalDate.from(start.toInstant().atZone(ZoneId.systemDefault()));
                    LocalDate endLocalDate = LocalDate.from(end.toInstant().atZone(ZoneId.systemDefault()));


                    while (startLocalDate.isBefore(endLocalDate)) {
                        int code = 1;
                        while (true) {
                            String url = startUrl + keyword + "&projectId=" + id + "&sort=latest&from=" + startLocalDate.format(DateTimeFormatter.BASIC_ISO_DATE) + "&to=" + startLocalDate.format(DateTimeFormatter.BASIC_ISO_DATE) + "&pageNum=" + code + endUrl;
                            logger.info("url:{}", url);
                            Weixin[] forObject = restTemplate.getForObject(url, Weixin[].class);
                            logger.info(" length:{}", forObject.length);
                            if (forObject.length <= 0) {
                                break;
                            } else {
                                handleData(Arrays.asList(forObject), printWriter);
                                printWriter.flush();
                                code++;
                            }
                        }
                        startLocalDate = startLocalDate.plusDays(1);
                    }
                }

            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void handleData(List<Weixin> wexinData, PrintWriter printWriter) {
        for (Weixin weixin : wexinData) {
            logger.info("开始写入文件....");
            if (weixin.getType() != null) {
                printWriter.print(handleType(weixin.getType()) + ",");
            } else {
                printWriter.print("" + ",");
            }
            printWriter.print(weixin.getUser().getDisplayName() + ",");
            if (weixin.getProvince() != null) {
                printWriter.print(weixin.getProvince() + ",");
            } else {
                printWriter.print("" + ",");
            }
            printWriter.print(handleTime(weixin.gettC()) + ",");

            if (weixin.getReposts() != null) {
                printWriter.print(weixin.getReposts() + ",");
            } else {
                printWriter.print("" + ",");
            }
            if (weixin.getComments() != null) {
                printWriter.print(weixin.getComments() + ",");
            } else {
                printWriter.print("" + ",");
            }
            if (weixin.getLikes() != null) {
                printWriter.print(weixin.getLikes() + ",");
            } else {
                printWriter.print("" + ",");
            }
            if (weixin.getpScore() != null) {
                printWriter.print(weixin.getpScore() + ",");
            } else {
                printWriter.print("" + ",");
            }
            if (!StringUtils.isEmpty(weixin.getClient())&&weixin.getClient().contains("<a")) {
                logger.info("发布来源:" + weixin.getClient());
                String substring = weixin.getClient().substring(weixin.getClient().indexOf(">", 1) + 1, weixin.getClient().indexOf("<", 2));
                printWriter.print(substring + ",");
            } else {
                printWriter.print("" + ",");
            }

            if (!StringUtils.isEmpty(weixin.getTitle())) {
                printWriter.print(weixin.getTitle() + ",");
            } else if (!StringUtils.isEmpty(weixin.getContext())) {
                printWriter.print(weixin.getContext() + ",");
            } else {
                printWriter.print("" + ",");
            }
            if (weixin.getUrl() != null) {
                printWriter.print(weixin.getUrl() + ",");
            } else {
                printWriter.print("" + ",");
            }
            if (weixin.getQuotedMessage() != null) {
                printWriter.print(handleContent(weixin.getQuotedMessage()));
            } else {
                printWriter.print("");
            }
            if (weixin.getContent() != null) {
                printWriter.println(handleContent(weixin.getContent()));
            } else {
                printWriter.println("");
            }


            logger.info("完成一条的写入....");
            logger.info(handleTime(weixin.gettC()));
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


    public static void main(String[] args) {
        String str = "598";
        String[] split = str.split(",");
        System.out.println(split);
    }


    public static void get() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");


        Date end = null;
        Date start = null;
        try {
            end = simpleDateFormat.parse("20190301");
            start = simpleDateFormat.parse("20180301");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalDate startLocalDate = LocalDate.from(start.toInstant().atZone(ZoneId.systemDefault()));
        LocalDate endLocalDate = LocalDate.from(end.toInstant().atZone(ZoneId.systemDefault()));

        while (startLocalDate.isBefore(endLocalDate)) {
            System.out.println(startLocalDate.toString());
            startLocalDate = startLocalDate.plusDays(1);
        }
    }


}
