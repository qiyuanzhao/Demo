package boot.pojo;

public class Weixin {

    private String type; //类型
    private String url; //链接
    private Long tC; //时间
    private Integer videoCount; //
    private String content; //内容
    private User user;  //用户
    private String context; //标题
    private Integer likes; //点赞数
    private Integer reposts;//转发数
    private Integer comments; //评论数
    private String province; //省份
    private String pScore; //情感指数
    private String title;
    private String client;//发布来源
    private String quotedMessage;



    public String getUrl() {
        return url;
    }

    public Long gettC() {
        return tC;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public User getUser() {
        return user;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void settC(Long tC) {
        this.tC = tC;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getReposts() {
        return reposts;
    }

    public Integer getComments() {
        return comments;
    }

    public String getProvince() {
        return province;
    }

    public String getpScore() {
        return pScore;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public void setReposts(Integer reposts) {
        this.reposts = reposts;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setpScore(String pScore) {
        this.pScore = pScore;
    }

    public String getContent() {
        return content;
    }

    public String getContext() {
        return context;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }

    public String getQuotedMessage() {
        return quotedMessage;
    }

    public void setQuotedMessage(String quotedMessage) {
        this.quotedMessage = quotedMessage;
    }
}
