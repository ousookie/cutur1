package test.test.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.test.entities.Url;

import javax.transaction.Transactional;

public interface UrlsRepo extends JpaRepository<Url, Integer> {
    @Query(value = "SELECT u from Url u WHERE u.srcUrl=:src")
    Url getUrlBySrcUrl(@Param(value = "src") String src);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Url SET cutUrl=:id WHERE id=:urlId")
    void setCutUrl(@Param(value = "id") String id, @Param(value = "urlId") Integer urlId);

    @Query(value = "SELECT u from Url u WHERE u.cutUrl=:cut")
    Url getUrlByCutUrl(@Param(value = "cut") String src);
}
