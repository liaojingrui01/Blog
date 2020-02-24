package com.ljr.blog.web.admin;

import com.ljr.blog.po.Letter;
import com.ljr.blog.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LetterController {

    @Autowired
    LetterService letterService;

    @GetMapping("/admin/letters")
    public String letters(@PageableDefault(size = 10, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, Letter letter, Model model)
    {
        model.addAttribute("page",letterService.listLetter(pageable));
        return "admin/letters";
    }

    @RequestMapping(value="/view/{id}",method = RequestMethod.POST)
    @ResponseBody
    public void view(@PathVariable Long id){
        Letter letter=letterService.findLetter(id);
        letter.setViewed(true);
        letterService.updateTag(id,letter);
    }

    @GetMapping("/admin/letters/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        letterService.deleteLetter(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/letters";
    }
}
