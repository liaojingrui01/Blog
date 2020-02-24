package com.ljr.blog.web.admin;

import com.ljr.blog.po.Blog;
import com.ljr.blog.po.Tag;
import com.ljr.blog.po.User;
import com.ljr.blog.service.BlogService;
import com.ljr.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Blog blog, Model model) {
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size=10,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Blog blog, Model model){
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }


    @GetMapping ("/blogs/input")
    public String input(Model model){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    @GetMapping ("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("tags",tagService.listTag());
        Blog blog =blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);

        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setUser((User) session.getAttribute("user"));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
            if(b==null){
                attributes.addFlashAttribute("message","添加失败");
            }else{
                attributes.addFlashAttribute("message","添加成功");
            }
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
            if(b==null){
                attributes.addFlashAttribute("message","更新失败");
            }else{
                attributes.addFlashAttribute("message","更新成功");
            }
        }

        return  REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }



}
