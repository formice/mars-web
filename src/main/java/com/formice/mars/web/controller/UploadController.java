package com.formice.mars.web.controller;


import com.formice.mars.web.service.FlowService;
import com.formice.mars.web.service.PanService;
import com.formice.mars.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    @Autowired
    private TaskService taskService;

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


    @GetMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response,String folder) {
        String fileName = folder.split(File.separator)[folder.split(File.separator).length-1];
        Long taskId = Long.valueOf(folder.split(File.separator)[folder.split(File.separator).length-2]);
        Long flowId = Long.valueOf(folder.split(File.separator)[folder.split(File.separator).length-3]);
        //1.下载OSS文件到本地
        String filePath = panService.download(folder,taskService.getTaskPath(flowId,taskId));
        //String fileName = "lombok-plugin-0.30-2020.1.jar";
        //String filePath = System.getProperty("user.dir") + File.separator+"download" + File.separator+fileName;
        //System.out.println("ddd1:"+filePath);
        //String fileName = "2019092715213111122.png";// 文件名
        if (filePath != null) {
            //设置文件路径
            File file = new File(filePath);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        //os.write(buffer, 0, i);
                        os.write(buffer, 0, buffer.length);
                        os.flush();

                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }
}
