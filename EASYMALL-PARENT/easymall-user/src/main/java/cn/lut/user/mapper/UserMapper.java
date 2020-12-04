package cn.lut.user.mapper;

import com.jt.common.pojo.User;

public interface UserMapper {
    int selectCountByUsername(String userName);

    void insertUser(User user);

    User selectExistByUsernameAndPassword(User user);
}
