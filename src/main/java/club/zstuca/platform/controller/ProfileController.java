package club.zstuca.platform.controller;

import club.zstuca.platform.dto.PaginationDTO;
import club.zstuca.platform.mapper.UserMapper;
import club.zstuca.platform.model.User;
import club.zstuca.platform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5") Integer size,
                          Model model){
        if(action.equals("")){
            model.addAttribute("section","questions");
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:index";
        }

        PaginationDTO paginationDTO = questionService.listAndLimitByCreator(user.getId(), page, size);
        System.out.println(paginationDTO);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
