package com.ljr.blog.web;

import com.ljr.blog.po.Letter;
import com.ljr.blog.service.LetterService;
import com.ljr.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class LetterShowController {
    @Autowired
    LetterService letterService;

    @GetMapping("/letter")
    public String letter()
    {
        return "letter";
    }

    @RequestMapping(value="/letter",method = RequestMethod.POST)
    @ResponseBody
    public void letter(String email,String nickname,String content)
    {
        Letter letter=new Letter(nickname,email,content);
        letter.setViewed(false);
        letter.setCreateTime(new Date());
        letterService.saveLetter(letter);
    }
}
