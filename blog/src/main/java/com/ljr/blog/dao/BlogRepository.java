package com.ljr.blog.dao;

import com.ljr.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> ,JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.title like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Query("select function('date_format',b.createTime,'%y---%M') as YMdate from Blog b group by function('date_format',b.createTime,'%y---%M') order by YMdate desc")
    List<String> findByGroupYearAndMonth();

    @Query("select b from Blog b where function('date_format',b.createTime,'%y---%M')=?1 ")
    List<Blog> findByYearAndMonth(String ym);
}
