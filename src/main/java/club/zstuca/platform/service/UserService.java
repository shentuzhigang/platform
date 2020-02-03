package club.zstuca.platform.service;

import club.zstuca.platform.mapper.UserMapper;
import club.zstuca.platform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        System.out.println(dbUser);
        if(dbUser == null){
            userMapper.insert(user);
        }else{
            userMapper.update(user);
        }
    }
}
