package com.formice.mars.web.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.formice.mars.web.common.Constant;
import com.formice.mars.web.common.SessionBag;
import com.formice.mars.web.component.oss.OssProvider;
import com.formice.mars.web.dao.CustomerDao;
import com.formice.mars.web.model.dto.PanFileDto;
import com.formice.mars.web.model.entity.Customer;
import com.formice.mars.web.tool.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PanService {
    @Autowired
    private OSS ossClient;
    @Autowired
    private OssProvider ossProvider;
    @Autowired
    private CustomerDao customerDao;


    /**
     * 根目录只显示当前登录用户的目录，在注册的位置需要创建用户oss目录（目录名称取用户的name）
     * @param folder
     * @return
     */
    public Map<String, List<PanFileDto>> getRootDir(String folder){
        Map<String, List<PanFileDto>> map = getFile(folder);
        map.remove("Objects");
        Customer c = customerDao.queryCustomerById(SessionBag.get(Constant.CURRENT_USER_ID,Long.class));
        log.info(c+","+c.getName());
        List<PanFileDto> list = map.get("CommonPrefixes");
        list = list.stream().filter(s->s.getName().equals(c.getName()) || s.getName().equals("public")).collect(Collectors.toList());
        map.put("CommonPrefixes",list);
        return map;
    }

    /**
     * 文件：文件+文件夹（在阿里oss文件夹是特殊的文件）
     * @param folder
     */
    public Map<String, List<PanFileDto>> getFile(String folder){
        Map<String, List<PanFileDto>> result = Maps.newHashMap();
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(ossProvider.getBucketName());

        // 设置正斜线（/）为文件夹的分隔符。设置"/",表示只展示文件夹
        listObjectsRequest.setDelimiter("/");

        // 列出fun目录下的所有文件和文件夹。
        listObjectsRequest.setPrefix(folder);

        // 列出文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);

        // 遍历所有文件。 文件及当前文件夹
        System.out.println("Objects:");
        List<PanFileDto> list = Lists.newArrayList();
        for (OSSObjectSummary o : listing.getObjectSummaries()) {
            System.out.println(o.getKey());
            String name = o.getKey().replace(folder,"").replace("/","");
            if(!StringUtils.isEmpty(name)) {
                list.add(new PanFileDto(name,"file",(o.getSize() / 1024) + "KB", DateUtil.date2Str(o.getLastModified()),o.getKey()));
            }
        }
        result.put("Objects",list);
        // 遍历所有commonPrefix。  文件夹
        System.out.println("CommonPrefixes:");
        List<PanFileDto> commonPrefixs = Lists.newArrayList();
        for (String commonPrefix : listing.getCommonPrefixes()) {
            System.out.println(commonPrefix);
            commonPrefixs.add(new PanFileDto(commonPrefix.replace(folder,"").replace("/",""),commonPrefix,"folder"));
        }
        result.put("CommonPrefixes",commonPrefixs);
        return result;

        // 关闭OSSClient。
        //ossClient.shutdown();
    }

    public void createFolder(String folder){
        // 要创建的文件夹名称,在满足Object命名规则的情况下以"/"结尾
        //String objectName = "jyltest/aa/";
        ObjectMetadata objectMeta = new ObjectMetadata();
        /*
         * 这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据
         * 照样可以上传下载,只是控制台会对以"/"结尾的Object以文件夹的方式展示,用户可以利用这种方式来实现文件夹模拟功能,创建形式上的文件夹
         */
        byte[] buffer = new byte[0];
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        objectMeta.setContentLength(0);
        try {
            ossClient.putObject(ossProvider.getBucketName(), folder, in, objectMeta);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delFile(String file) {
        //String objectName = "jyltest/aa/";
        boolean isObjectExist = ossClient.doesObjectExist(ossProvider.getBucketName(), file);
        System.out.println("rs:"+isObjectExist);
        if (isObjectExist) {
            System.out.println("存在,可以删除");
            ossClient.deleteObject(ossProvider.getBucketName(), file);
        } else {
            System.out.println("文件夹不存在，无法删除！");
        }

    }

    public void upload(String fileName,String folder)  {
        try {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(ossProvider.getBucketName(), folder+fileName);
            // The local file to upload---it must exist.
            uploadFileRequest.setUploadFile(System.getProperty("user.dir") + File.separator+"upload" + File.separator + fileName);
            // Sets the concurrent upload task number to 5.
            uploadFileRequest.setTaskNum(1);//5
            // Sets the part size to 1MB.
            uploadFileRequest.setPartSize(1024);//1024 * 1024 * 1
            // Enables the checkpoint file. By default it's off.
            uploadFileRequest.setEnableCheckpoint(true);

            UploadFileResult uploadResult = ossClient.uploadFile(uploadFileRequest);

            CompleteMultipartUploadResult multipartUploadResult =
                    uploadResult.getMultipartUploadResult();
            System.out.println(multipartUploadResult.getETag());

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            //ossClient.shutdown();
        }
    }

    public void upload(String fileName,String localFolder,String ossFolder)  {
        try {
            UploadFileRequest uploadFileRequest = new UploadFileRequest(ossProvider.getBucketName(), ossFolder+fileName);
            // The local file to upload---it must exist.
            uploadFileRequest.setUploadFile(localFolder+fileName);
            // Sets the concurrent upload task number to 5.
            uploadFileRequest.setTaskNum(1);//5
            // Sets the part size to 1MB.
            uploadFileRequest.setPartSize(1024);//1024 * 1024 * 1
            // Enables the checkpoint file. By default it's off.
            uploadFileRequest.setEnableCheckpoint(true);

            UploadFileResult uploadResult = ossClient.uploadFile(uploadFileRequest);

            CompleteMultipartUploadResult multipartUploadResult =
                    uploadResult.getMultipartUploadResult();
            System.out.println(multipartUploadResult.getETag());

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            //ossClient.shutdown();
        }
    }

    public String download(String ossFile,String savePath){
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        String fileName = ossFile.split(File.separator)[ossFile.split(File.separator).length-1];
        //sString localFile = System.getProperty("user.dir") + File.separator+"download" + File.separator + fileName;
        String localFile = savePath+fileName;
        log.info("开始下载文件["+ossFile+"] --> ["+localFile+"]");
        ossClient.getObject(new GetObjectRequest(ossProvider.getBucketName(), ossFile), new File(localFile));
        log.info("下载完成文件");
        return localFile;
        // 关闭OSSClient。
        //ossClient.shutdown();
    }

    public void main(String [] args){
        System.out.println("ddfd");
        SimplifiedObjectMeta fff = new SimplifiedObjectMeta();

    }
}
