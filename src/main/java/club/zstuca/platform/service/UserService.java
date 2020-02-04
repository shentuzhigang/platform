package club.zstuca.platform.service;

import club.zstuca.platform.mapper.UserMapper;
import club.zstuca.platform.model.User;
import club.zstuca.platform.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        System.out.println(dbUsers);
        if(dbUsers == null){
            userMapper.insert(user);
        }else{
            User dbUser = dbUsers.get(0);
            User updateUser = new User();
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setToken(user.getToken());
            userMapper.updateByExample(updateUser,example);
        }
    }
}
