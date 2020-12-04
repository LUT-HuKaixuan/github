package cn.lut.pic.controller;

import cn.lut.pic.service.PicService;
import com.jt.common.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PicController {
    @Autowired
    private PicService picService;

    /**
     * 地址：/pic/uploadImg
     * 请求方式：Post
     * 请求参数：MultipartFile pic
     *      ---MultipartFile是springmvc专门做文件上传接收数据的对象
     * 返回：PicUploadResult对象的json
     */
    @RequestMapping("/pic/uploadImg")
    public PicUploadResult picUpload(MultipartFile pic){
        return picService.picUpload(pic);
    }
}
