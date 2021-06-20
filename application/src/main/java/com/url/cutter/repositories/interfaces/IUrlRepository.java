package com.url.cutter.repositories.interfaces;

import com.url.cutter.entities.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IUrlRepository extends JpaRepository<ShortUrl, Integer> {

    ShortUrl getShortUrlByCutUrl(@Param(value = "cut") String cut);

    ShortUrl getShortUrlBySrcUrl(@Param(value = "src") String src);

    @Query(value = "SELECT last_value FROM sequence_generator", nativeQuery = true)
    BigInteger getLastInsertEntityId();

}
