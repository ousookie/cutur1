package com.url.cutter.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "url")
@Entity(name = "ShortUrl")
@Data
@NoArgsConstructor
public class ShortUrl {

    @Id
    @SequenceGenerator(
            name = "generator",
            sequenceName = "sequence_generator",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "generator")
    @Column(
            name = "id",
            columnDefinition = "bigint")
    private int id;
    @Column(
            name = "src_url",
            columnDefinition = "varchar",
            unique = true)
    private String srcUrl;
    @Column(
            name = "cut_url",
            columnDefinition = "varchar")
    private String cutUrl;
    @Column(
            name = "time_stamp",
            columnDefinition = "varchar")
    private long timeStamp;

    public ShortUrl(int id, String srcUrl, String cutUrl, long timeStamp) {
        this.id = id;
        this.srcUrl = srcUrl;
        this.cutUrl = cutUrl;
        this.timeStamp = timeStamp;
    }

}
