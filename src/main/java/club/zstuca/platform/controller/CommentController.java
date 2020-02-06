package club.zstuca.platform.controller;


import club.zstuca.platform.dto.CommentDTO;
import club.zstuca.platform.dto.ResultDTO;
import club.zstuca.platform.mapper.CommentMapper;
import club.zstuca.platform.model.Comment;
import club.zstuca.platform.model.User;
import club.zstuca.platform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object comment(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request,
                          HttpServletResponse response){
        User user = (User)request.getSession().getAttribute("name");
        if(user == null){
            return new ResultDTO(2002,"未登录");
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1);
        commentMapper.insert(comment);
        Map<Object,Object> objectObjectMap = new HashMap<>();
        objectObjectMap.put("message","success");
        return null;
    }
}
