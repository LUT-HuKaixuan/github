package cn.lut.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis保存验证码逻辑
 */
@RestController
public class CodeController {
    /**
     * 将第一次生成的验证码配合手机号存储在一个容器(redis)
     * 对同一个手机号做发送验证码的限制,5分钟最多发送3次,超过3次,锁定10分钟
     */
    @Autowired
    private StringRedisTemplate redisTemplate;
    @RequestMapping("/sendCode")
    public String sendCode(String phone){
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        ListOperations<String, String> opsForList = redisTemplate.opsForList();

        String lock=phone+".lock";
        String list=phone+".list";
        String code=phone+".code";
        //如果lock存在
        if (redisTemplate.hasKey(lock)){
            return "您的手机号已经被锁住了,等10分钟";
        }
        //当前系统时间
        Long nowTime=new Date().getTime();
        //如果list元素个数等于3
        if (opsForList.size(list)==3){
            //移除第一个元素，和当前系统时间对比
            String firstTimeStr = opsForList.rightPop(list);
            Long firstTime=Long.parseLong(firstTimeStr);

            Long timeGap=nowTime-firstTime;
            //如果小于5分钟
            if(timeGap<1000*60*5){
                //生成锁，将其他数据删除
                redisTemplate.delete(list);
                redisTemplate.delete(code);
                opsForValue.set(lock,"",10,TimeUnit.MINUTES);
                return "您的手机号,本次发送违反规定,锁住10分钟";
            }
        }
        //上述判断都结束,正常生成code
        //随机生成4位数字
        int codeR = (int) Math.ceil(Math.random() * 9000 + 1000);
        redisTemplate.opsForValue().set(code,codeR+"",15, TimeUnit.MINUTES);
        //在list将当前时间lpush进入
        opsForList.leftPush(list,nowTime+"");
        return "您的手机号成功生成验证码";
    }
    /**
     * 验证验证码
     */
    @RequestMapping("/verify/code")
    public String verifyCode(String phone,String inputCode){
        String codeKey=phone+".code";

        if (!redisTemplate.hasKey(codeKey)){
            return "您的验证码根本不存在,你对比什么呀";
        }
        String codeJson = redisTemplate.opsForValue().get(codeKey);
        if (inputCode.equals(codeJson)){
            return "您的验证成功,请继续享受网站其他功能";
        }else{
            return "验证码是错误的,是不是填了别人的手机号";
        }

    }

}
