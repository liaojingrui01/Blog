package com.ljr.blog.dao;

import com.ljr.blog.po.Letter;
import com.ljr.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter,Long> {
    @Query("select t from Letter t")
    List<Letter> findTop(Pageable pageable);

    Letter findById(Long id);
}
