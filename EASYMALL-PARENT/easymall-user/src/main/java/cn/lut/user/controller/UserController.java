package cn.lut.user.controller;

import cn.lut.user.service.UserService;
import com.alibaba.druid.util.StringUtils;
import com.jt.common.pojo.User;
import com.jt.common.utils.CookieUtils;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/manage")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 注册表单中用户名重复校验
     *  查询一个用户名是否存在
     *  /user/manage/checkUserName
     *  Post
     *  String userName
     *  返回SysResult对象的json
     */
    @RequestMapping("checkUserName")
    public SysResult checkUsername(String userName){
        boolean avilible=userService.checkUsername(userName);
        //true可用 false不可用
        if (avilible){
            return SysResult.ok();
        }else {
            //不可用
            return SysResult.build(201,"用户名已存在",null);
        }
    }

    /**
     * 注册表单提交
     *  /user/manage/save
     *  Post
     *  User user 缺少用户userId,password需要加密
     *  返回SysResult对象的json
     */
    @RequestMapping("/save")
    public SysResult doRegister(User user){
        try{
            //调用业务层进行注册
            userService.doRegister(user);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(201,"注册用户失败",null);
        }
    }

    //登录接口
    @RequestMapping("/login")
    public SysResult doLogin(User user, HttpServletRequest request,
                             HttpServletResponse response){
        //从业务层拿到key值 ticket
        String ticket=userService.doLogin(user);
        if (!StringUtils.isEmpty(ticket)){
            //ticket不为空,登录成功,写入response一个cookie值
            //cookie的值必须是 "EM_TICKET" 原因js解析的就是这个名字
            CookieUtils.setCookie(request,response,"EM_TICKET",ticket);
            return SysResult.ok();
        }else {
            //ticket为null或"" 进入if 相当于没有存储redis 登录失败
            return SysResult.build(201,"用户名密码错误",null);
        }

    }
    //登录状态获取
    //返回SysResult对象的json,其结构:
    //Object data;封装从redis获取的userJson
    @RequestMapping("/query/{ticket}")

    public SysResult getUserJson(@PathVariable String ticket, HttpServletRequest request,
                                 HttpServletResponse response) {
        String userJson=userService.getUserJson(ticket);
        //如果登录状态是超时的 userJson是空 ticket从cookie删掉
        if(StringUtils.isEmpty(userJson)) {
            CookieUtils.deleteCookie(request,response,"EM_TICKET");
            return SysResult.build(201,"登录超时",null);
        }else{
            //没超时登录状态正在使用
            return SysResult.build(200,"登录状态正常",userJson);

        }


    }
    /**
     * 登出
     * /user/manage/logout
     * 返回SysResult对象的json
     */
    @RequestMapping("/logout")
    public SysResult doLogout(HttpServletRequest request,
                              HttpServletResponse response){
        //从cookie中删除ticket
        CookieUtils.deleteCookie(request,response,"EM_TICKET");

        //redis里删除两个key-value
        String ticket = CookieUtils.getCookieValue(request, "EM_TICKET");
        userService.doLogout(ticket);
        return SysResult.ok();
    }


}
