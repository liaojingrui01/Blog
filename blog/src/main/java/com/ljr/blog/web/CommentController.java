package com.ljr.blog.web;

import com.ljr.blog.po.Blog;
import com.ljr.blog.po.Comment;
import com.ljr.blog.po.User;
import com.ljr.blog.service.BlogService;
import com.ljr.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller

public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAdminComment(true);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

    @GetMapping("/comment/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes)
    {
        Long blogId=commentService.findBlogId(id);
        commentService.deleteComment(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/blog/"+blogId;
    }
}
