package com.ljr.blog.web;

import com.ljr.blog.service.BlogService;
import com.ljr.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentController {

    @Autowired
    private TagService tagService;

    @GetMapping("/_fragment")
    public String left(Model model) {
        model.addAttribute("tags", tagService.listTagTop(3));
        return "_fragment";
    }


}
