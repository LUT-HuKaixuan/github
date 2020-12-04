package cn.lut.pic.service;

import com.jt.common.utils.UploadUtil;
import com.jt.common.vo.PicUploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PicService {

    /*
        1. 校验图片是否正确（后缀验证 gif、jpg、png、jpeg）
        2. 生成该图片的多级路径 /upload/a/v/3/d/f/6/h/d
        3. 重命名图片文件，避免考虑覆盖的问题
        4. 将流数据输出到磁盘文件，图片上传保存完成
        5. 利用之前生成的变量，拼接url地址，存储到PicUploadResult中返回给ajax返回
        PicUploadResult对象的json,结构是:
            String url 生成的访问图片路径
            Integer error 上传成功和失败的标志 0表示无错误 1表示有错误
     */
    public PicUploadResult picUpload(MultipartFile pic){
        PicUploadResult result=new PicUploadResult();
        //图片的名称
        String oName = pic.getOriginalFilename();
        //截取后缀
        String extName = oName.substring(oName.
                lastIndexOf("."));
        //解析后缀
        boolean b = extName.matches(".(gif|jpg|png|jpeg)$");
        //后缀不正确
        if (!b){
            result.setError(1);
            return result;
        }
        //后缀正确
        //生成该图片的多级路径 path=/easymall/a/b/c/d/e/f/g/h
        String path = UploadUtil.getUploadPath
                (UUID.randomUUID().toString(), "/easymall");
        //重命名图片文件，避免考虑覆盖的问题
        String newOName=System.currentTimeMillis()+oName;
        //将流数据输出到磁盘文件，图片上传保存完成
        File dir=new File("d:/img"+path);
        //多级路径不存在
        if (!dir.exists()){
            //生成多级路径
            dir.mkdirs();
        }
        try {
            //输出图片文件到磁盘中
            pic.transferTo(new File(dir+"/"+newOName));
            //利用之前生成的变量，拼接url地址，存储到PicUploadResult中返回给ajax返回
            String url="http://image.jt.com"+path+"/"+newOName;
            result.setUrl(url);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            //
            result.setError(1);
            return result;
        }


    }
}
