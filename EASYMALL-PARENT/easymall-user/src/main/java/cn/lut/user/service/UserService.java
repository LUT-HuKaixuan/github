package cn.lut.user.service;

import cn.lut.user.mapper.UserMapper;

import com.jt.common.pojo.User;
import com.jt.common.utils.MD5Util;
import com.jt.common.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;



@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    /*
        查询一个用户名是否存在
        用户名存在---false
        用户名可用---true
     */
    public boolean checkUsername(String userName) {
        //查询数量
        int count=userMapper.selectCountByUsername(userName);
        //不是1就是0
        //count==1 说明存在 返回false不可用
        //count==0 说明不存在 返回true
        return count==0;
    }

    /*
        点击注册
     */
    public void doRegister(User user) {
        //补充userId
        user.setUserId(UUID.randomUUID().toString());
        //明文密码进行加密
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        userMapper.insertUser(user);
    }

    /*
        登录
     */
    @Autowired
    private StringRedisTemplate template;

    public String doLogin(User user) {
        ValueOperations<String, String> opsForValue = template.opsForValue();
        try {
            //密码加密
            user.setUserPassword(MD5Util.md5(user.getUserPassword()));
            //
            User exist = userMapper.selectExistByUsernameAndPassword(user);
            //
            String loginKey="LOGIN_"+user.getUserName();
            //如果不存在
            if (exist == null) {
                return "";
            } else {
                //如果前面有人登录过 loginKey就保存了前面登录的ticket
                if (template.hasKey(loginKey)){
                    //顶替loginKey的ticket和ticket做key、userJson做值
                    String oldTicket = opsForValue.get(loginKey);
                    template.delete(oldTicket);
                }
                //key值
                String ticket = "EM_TICKET_" + System.currentTimeMillis() + user.getUserName();
                //
                exist.setUserName(exist.getUserNickname());
                exist.setUserPassword("");
                String userJson = MapperUtil.MP.writeValueAsString(exist);
                //传入redis

                opsForValue.set(ticket, userJson, 2, TimeUnit.HOURS);
                //登陆顶替
                opsForValue.set(loginKey,ticket,2,TimeUnit.HOURS);

                return ticket;
            }
        }catch(Exception e){
                e.printStackTrace();
                return "";
            }

    }

    //登录状态获取
    public String getUserJson(String ticket) {


        //TODO 登录续约
        Long leftTime = template.getExpire(ticket, TimeUnit.MINUTES);
        //从ticket解析userName 调用getUsername()方法
        String userName=getUsername(ticket);
        String loginKey="LOGIN_"+userName;
        if (leftTime<60){
            //达到了续租的条件
            template.expire(ticket,2,TimeUnit.HOURS);
            template.expire(loginKey,2,TimeUnit.HOURS);
        }
        //get一下redis的数据直接返回就可以
        return template.opsForValue().get(ticket);
    }

    /*
        从ticket获得username
     */
    private String getUsername(String ticket) {
        return ticket.substring(23);
    }


    //删除redis中的两个key-value
    public void doLogout(String ticket) {
        //
        template.delete(ticket);
        //
        String username = getUsername(ticket);
        String loginKey="LOGIN_"+username;
        template.delete(loginKey);
    }
}
