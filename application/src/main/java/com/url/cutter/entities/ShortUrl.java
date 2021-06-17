package com.url.cutter.entities;

import javax.persistence.*;

@Entity(name = "ShortUrl")
@Table(name = "url")
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint")
    private Integer id;
    @Column(name = "src_url", columnDefinition = "varchar", unique = true)
    private String srcUrl;
    @Column(name = "cut_url", columnDefinition = "varchar")
    private String cutUrl;
    @Column(name = "time_stamp", columnDefinition = "varchar")
    private Long timeStamp;

    public ShortUrl() {
    }

    public ShortUrl(Integer id, String srcUrl, String cutUrl, Long savedTime) {
        this.id = id;
        this.srcUrl = srcUrl;
        this.cutUrl = cutUrl;
        this.timeStamp = savedTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public String getCutUrl() {
        return cutUrl;
    }

    public void setCutUrl(String cutUrl) {
        this.cutUrl = cutUrl;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
