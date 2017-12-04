package com.lemon.carmonitor.model.bean;

/**
 * 项目名称:  [CarMonitor]
 * 包:        [com.lemon.carmonitor.model.bean]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2016/1/1 17:56]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2016/1/1 17:56]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class NewsItem {

    private String id;
    private String title;
    private String source;
    private String firstImg;
    private String mark;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", firstImg='" + firstImg + '\'' +
                ", mark='" + mark + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

}
