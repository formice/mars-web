package com.formice.mars.web.component.oss;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProvider {
    //@Value("${aliyun.oss.endpoint}")
    private String endpoint;
    //@Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    //@Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    private String bucketName;


    @Bean
    public OSS ossClient(){
        // 创建ClientConfiguration实例，按照您的需要修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 开启支持CNAME。CNAME是指将自定义域名绑定到存储空间上。
        conf.setSupportCname(true);
        //return new OSSClient(ALIYUN_OSS_ENDPOINT,ALIYUN_OSS_ACCESSKEYID,ALIYUN_OSS_ACCESSKEYSECRET);
        // 创建OSSClient实例。
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, conf);
    }

    /*@Bean
    public void shutdown(){
        ossClient.shutdown();
    }*/
}