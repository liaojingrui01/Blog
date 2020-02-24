package com.ljr.blog.service;

import com.ljr.blog.NotFoundException;
import com.ljr.blog.dao.LetterRepository;
import com.ljr.blog.po.Letter;
import com.ljr.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LetterServiceImpl implements LetterService {

    @Autowired
    LetterRepository letterRepository;

    @Transactional
    @Override
    public  Letter saveLetter(Letter letter)
    {
        return letterRepository.save(letter);
    }

    @Transactional
    @Override
    public Page<Letter> listLetter(Pageable pageable) {
        return letterRepository.findAll(pageable);
    }

    @Transactional
    public Letter findLetter(Long id)
    {
        return letterRepository.findById(id);
    }

    @Transactional
    @Override
    public Letter updateTag(Long id, Letter letter) {
        Letter letter1 = letterRepository.findOne(id);
        if (letter1 == null) {
            throw new NotFoundException("不存在该私信");
        }
        BeanUtils.copyProperties(letter,letter1);
        return letterRepository.save(letter);
    }
    @Transactional
    @Override
    public void deleteLetter(Long id) {
        letterRepository.delete(id);
    }

}
