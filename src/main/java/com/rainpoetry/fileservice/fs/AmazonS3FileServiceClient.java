package com.rainpoetry.fileservice.fs;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rainpoetry.fileservice.IFileServiceClient;
import com.rainpoetry.fileservice.assembler.ObjectMetaDataAssembler;
import com.rainpoetry.fileservice.request.FileUploadRequest;
import com.rainpoetry.fileservice.request.FileDeleteRequest;
import com.rainpoetry.fileservice.request.FilePrivateUrlRequest;
import com.rainpoetry.fileservice.request.FilePublicUrlRequest;
import com.kucoin.risk.fs.common.util.DateTools;
import com.kucoin.risk.fs.common.util.SafetyJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;

/**
 * @description: AWS S3 文件服务
 * @author: Jim.Chen
 * @create: 2022-03-29 19:33
 */
@Component
@Slf4j
public class AmazonS3FileServiceClient implements IFileServiceClient {

    @Resource
    private AmazonS3 s3;

    @Resource
    private ObjectMetaDataAssembler objectMetaDataAssembler;


    @Override
    public boolean createBucket(String bucketName) {

        // pre-check
        if (StringUtils.isBlank(bucketName)) {
            return false;
        }

        try {
            // 创建 bucket
            Bucket s3Bucket = s3.createBucket(bucketName);
            log.info("bucket create success , [{}] ", SafetyJson.toStr(s3Bucket));
            return true;
        } catch (Exception e) {
            log.error("create bucket fail , bucket-name:{} ", bucketName, e);
        }
        return false;
    }

    @Override
    public boolean deleteBucket(String bucketName) {

        // pre-check
        if (StringUtils.isBlank(bucketName)) {
            return false;
        }

        try {
            // 删除 bucket
            s3.deleteBucket(bucketName);
            log.info("delete bucket success, bucket-name [{}] ", bucketName);
            return true;
        } catch (Exception e) {
            log.error("delete bucket fail , bucket-name:{} ", bucketName, e);
        }
        return false;
    }

    @Override
    public boolean existBucket(String bucketName) {
        // pre-check
        if (StringUtils.isBlank(bucketName)) {
            return false;
        }

        try {
            return s3.doesBucketExistV2(bucketName);
        } catch (Exception e) {
            log.error("delete bucket fail , bucket-name:{} ", bucketName, e);
        }
        return false;
    }

    @Override
    public boolean uploadFile(@Valid FileUploadRequest metaData, InputStream in) {
        try {
            //  构造 s3 接口数据
            ObjectMetadata objectMetadata = objectMetaDataAssembler.toAmazonS3Meta(metaData);

            // 上传文件流到 S3
            PutObjectResult result = s3.putObject(metaData.getBucketName(), metaData.getFileId(), in, objectMetadata);

            log.info("upload file success , [{}]", SafetyJson.toStr(result));
            return true;
        } catch (Exception e) {
            log.error("upload file fail , file meta:[{}] ", SafetyJson.toStr(metaData), e);
        }
        return false;
    }

    @Override
    public String getPrivateUrl(@Valid FilePrivateUrlRequest request) {

        LocalDateTime expiredDateTime = request.getExpiredDateTime();
        // 有效时间默认机制
        if (expiredDateTime == null) {
            expiredDateTime = defaultExpireDateTime();
        }

        // 生成文件的私有链接
        try {
            URL url = s3.generatePresignedUrl(request.getBucketName(), request.getFileId(),
                    DateTools.fromLocalDateTime(expiredDateTime));
            log.info(" generate private url success , url: {}", url.toString());
            request.setExpiredDateTime(expiredDateTime);
            return url.toString();
        } catch (Exception e) {
            log.error("generate private url fail |  bucketName: {} | fileId: {} ,expireDateTime : {}",
                    request.getBucketName(), request.getFileId(), expiredDateTime.toString());
        }
        return null;
    }

    @Override
    public String getPublicUrl(@Valid FilePublicUrlRequest request) {

        // 生成文件的公有链接
        try {
            URL url = s3.getUrl(request.getBucketName(), request.getFileId());
            log.info(" generate private url success , url: {}", url.toString());
            return url.toString();
        } catch (Exception e) {
            log.error("generate public url fail |  bucketName: {} | fileId: {} ",
                    request.getBucketName(), request.getFileId());
        }
        return null;
    }

    @Override
    public boolean deleteFile(FileDeleteRequest request) {
        try {
            // 删除文件
            s3.deleteObject(request.getBucketName(), request.getFileId());
            log.info("delete file success , bucket-name: {} |  file-id : {}",
                    request.getBucketName(), request.getFileId());
            return true;
        } catch (Exception e) {
            log.error("delete file fail , bucket-name:{} ,file-id:{} ",
                    request.getBucketName(), request.getFileId(), e);
        }
        return false;
    }


    /**
     * 默认有效时间
     *
     * @return
     */
    private LocalDateTime defaultExpireDateTime() {
        return LocalDateTime.now().plusMinutes(30);
    }


}
