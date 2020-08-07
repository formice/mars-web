package com.formice.mars.web.controller;


import com.formice.mars.web.service.FlowService;
import com.formice.mars.web.service.PanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

//import javax.servlet.http.HttpServletRequest;

//import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private FlowService flowService;
    @Autowired
    private PanService panService;

    /**
     * @Title: uploadSource
     * @Description: 文件上传，返回文件的存储路径
     * @param file
     * @return Object
     * @author hzp
     * @date 2019年5月6日下午9:51:16
     */
    @RequestMapping(value="/file" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadSource(@RequestParam("file") MultipartFile file , HttpServletRequest request,String folder) {
        System.out.println(folder);
        String pathString = null;
        if(file!=null) {
            //pathString = "D:/upload/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +file.getOriginalFilename();
            //pathString = request.getContextPath();
            //pathString = pathString.substring(0, pathString.length() - 1);
            //pathString = pathString.substring(0, pathString.lastIndexOf("\\"));
            //pathString=request.getSession().getServletContext().getRealPath("/");
            //System.out.println(request.getSession().getServletContext().getRealPath("mars-web"));
            System.out.println(System.getProperty("user.dir") + File.separator+"upload");
            //pathString = "E:\\workspace\\workspace_mars\\mars-web\\upload\\"+file.getOriginalFilename();
            //pathString = "upload";
            pathString = System.getProperty("user.dir") + File.separator+"upload" + File.separator + file.getOriginalFilename();
        }

        try {
            File files=new File(pathString);
            //打印查看上传路径
            /*if(!files.exists()){
                files.mkdirs();
            }*/
            file.transferTo(files);

            //上传到oss
            System.out.println(file.getOriginalFilename()+","+folder);
            panService.upload(file.getOriginalFilename(),folder);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Map map = new HashMap<String,Object>();
        map.put("msg","ok");
        map.put("code",200);
        map.put("data",pathString);


        return map;
    }
}
