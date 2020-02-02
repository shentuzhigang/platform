package club.zstuca.platform.controller;


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
                        Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }


        List<QuestionDTO> questionDTOlist = questionService.list();
        System.out.println(questionDTOlist);
        model.addAttribute("questions",questionDTOlist);
        return "index";
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


        Cookie[] cookies = request.getCookies();
        User user=null;
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question =new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:index";
    }
}
