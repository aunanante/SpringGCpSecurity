package com.aunanant.SpringGCpSecurity.repository;

import com.aunanant.SpringGCpSecurity.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin("http://localhost:4200")
public interface DetailRepository extends JpaRepository<Detail, Long> {
    List<Detail> findByProductId(Long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Detail d WHERE d.product.id = ?1")
    void deleteByProductId(Long productId);

    List<Detail> findByDetailNameContaining (String name);

}


