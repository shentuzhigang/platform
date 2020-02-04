package club.zstuca.platform.controller;


import club.zstuca.platform.dto.QuestionDTO;
import club.zstuca.platform.mapper.QuestionMapper;
import club.zstuca.platform.model.Question;
import club.zstuca.platform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.findById(id);
        questionService.incView(id);
        System.out.println(questionDTO);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
