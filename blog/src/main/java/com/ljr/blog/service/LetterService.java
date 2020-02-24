package com.ljr.blog.service;

import com.ljr.blog.po.Letter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LetterService {
    Letter saveLetter(Letter letter);
    Page<Letter> listLetter(Pageable pageable);
    Letter findLetter(Long id);
    Letter updateTag(Long id, Letter letter);
    void deleteLetter(Long id);
}
