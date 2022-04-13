package com.rainpoetry.fileservice.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: S3 文件服务配置
 * @author: Jim.Chen
 * @create: 2022-03-29 20:53
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "kucoin.amazon.s3")
@Slf4j
public class AmazonS3Config {

    private String region = Regions.AP_NORTHEAST_1.getName();

    private String accessKey;

    private String secretKey;

    @Bean
    public AmazonS3 getAmazonS3() {

        AWSCredentialsProvider provider = DefaultAWSCredentialsProviderChain.getInstance();

        if (StringUtils.isNoneBlank(accessKey, secretKey)) {
            log.info("init s3 with accessKey");
            provider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
        } else {
            log.info("init s3 with provdier");
        }

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(provider)
                .withRegion(region)
                .enablePathStyleAccess()
                .build();
        return s3;
    }
}
