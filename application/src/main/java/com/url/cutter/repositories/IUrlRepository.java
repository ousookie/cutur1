package com.url.cutter.repositories;

import com.url.cutter.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUrlRepository extends JpaRepository<ShortUrl, Integer> {

    ShortUrl getShortUrlByCutUrl(@Param(value = "cut") String cut);

    ShortUrl getShortUrlBySrcUrl(@Param(value = "src") String src);

    @Modifying
    @Query(value = "UPDATE ShortUrl SET cutUrl=:idValue WHERE id=:urlId")
    void setCutUrl(@Param(value = "idValue") String id, @Param(value = "urlId") Integer urlId);
}
