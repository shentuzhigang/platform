package club.zstuca.platform.controller;


import club.zstuca.platform.dto.CommentDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody CommentDTO commentDTO){
        return null;
    }
}
