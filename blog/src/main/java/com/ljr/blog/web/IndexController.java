package com.ljr.blog.web;

import com.ljr.blog.po.Blog;
import com.ljr.blog.service.BlogService;
import com.ljr.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {


    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("tags", tagService.listTagTop(3));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog",blogService. getAndConvert(id));
        model.addAttribute("tags", tagService.listTagTop(3));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page",blogService.listBlog(pageable,"%"+query+"%"));
        model.addAttribute("query",query);
        return "search";
    }

    @RequestMapping(value="/dianzan/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Map likeNum(@PathVariable Long id, Model model){
        Blog b=blogService.getBlog(id);
        Integer likeNum=b.getLikeNum()+1;
        b.setLikeNum(likeNum);
        blogService.updateBlog(id,b);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("likeNum",likeNum);
        return map;
    }
}

