package com.ljr.blog.web;

import com.ljr.blog.service.BlogService;
import com.ljr.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ArchiveShowController {

    @Autowired
    BlogService blogService;

    @Autowired
    TagService tagService;

    @GetMapping("/archive")
    public String archives(Model model)
    {
        model.addAttribute("tags", tagService.listTagTop(3));
        model.addAttribute("archiveMap", blogService.archiveBlog());
        return "archive";
    }

}
