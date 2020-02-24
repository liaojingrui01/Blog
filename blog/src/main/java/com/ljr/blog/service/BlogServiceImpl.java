package com.ljr.blog.service;

import com.ljr.blog.NotFoundException;
import com.ljr.blog.dao.BlogRepository;
import com.ljr.blog.dao.TagRepository;
import com.ljr.blog.po.Blog;
import com.ljr.blog.po.Tag;
import com.ljr.blog.util.MarkdownUtils;
import com.ljr.blog.util.MyBeanUtils;
import lombok.experimental.var;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagService tagService;
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }

    public Blog getAndConvert(Long id){
        Blog b=blogRepository.findOne(id);
        if(b==null){
            throw new NotFoundException("该博客不存在");
        }
        String content=b.getContent();
        Blog b2=new Blog();
        BeanUtils.copyProperties(b,b2);
        b2.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b2;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Blog blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (!"".equals(blog.getTagIds()) && blog.getTagIds() != null) {
                    //predicates.add(cb.in(root.<List<Tag>>get("tags")).value(tagService.getTag(Long.parseLong(blog.getTagIds()))));

                    // tag=tagService.getTag(Long.parseLong(blog.getTagIds()));
                   // List<Tag> tags= new ArrayList<Tag>();
                    //tags.add(tag);
                   // Path<List <Tag>> listTag=root.get("tags");
                    Join join = root.join("tags");
                    Long tagId=Long.parseLong(blog.getTagIds());
                    predicates.add(cb.equal(join.get("id"),tagId));


                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }
    @Override
    public Page<Blog> listBlog(Pageable pageable,String query) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setLikeNum(0);
            blog.setCommentNum(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }

    @Transactional
    @Override
    public Map<String,List<Blog>> archiveBlog()
    {
        List<String> yms=blogRepository.findByGroupYearAndMonth();
        Map<String,List<Blog>> map=new HashMap<>();
        for(String ym : yms)
        {
            List<Blog>blogs=blogRepository.findByYearAndMonth(ym);
            map.put(ym,blogs);
        }
        return map;
    }

}
