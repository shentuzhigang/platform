package club.zstuca.platform.controller;


import club.zstuca.platform.dto.PaginationDTO;
import club.zstuca.platform.dto.QuestionDTO;
import club.zstuca.platform.mapper.QuestionMapper;
import club.zstuca.platform.mapper.UserMapper;
import club.zstuca.platform.model.Question;
import club.zstuca.platform.model.User;
import club.zstuca.platform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String root(){
        return "redirect:index";
    }
    @GetMapping("/index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size,
                        Model model){
        User user = (User)request.getSession().getAttribute("user");


        PaginationDTO paginationDTO = questionService.listAndLimit(page,size);
        model.addAttribute("pagination",paginationDTO);
        return "index";
    }
    @GetMapping("/publish/{id}")
    public String publish(@PathVariable("id") Integer id,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model){
        User user = (User)request.getSession().getAttribute("user");
        Question question = questionMapper.selectByPrimaryKey(id);
        if(user==null){
            return "redirect:publish";
        }else if(user.getId()!=question.getCreator()){
            return "redirect:index";
        }
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String dopublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        if(title==null || title.equals("")){
            model.addAttribute("error","标题NULL");
            return "publish";
        }
        if(description==null || description.equals("")){
            model.addAttribute("error","问题NULL");
            return "publish";
        }
        if(tag==null || tag.equals("")){
            model.addAttribute("error","标签NULL");
            return "publish";
        }
        Question question =new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insert(question);
        return "redirect:index";
    }

    @GetMapping("/profile")
    public String profile(){
        return "redirect:profile/questions";
    }
}
