package com.rainpoetry.fileservice;


import com.rainpoetry.fileservice.request.FileUploadRequest;
import com.rainpoetry.fileservice.request.FileDeleteRequest;
import com.rainpoetry.fileservice.request.FilePrivateUrlRequest;
import com.rainpoetry.fileservice.request.FilePublicUrlRequest;

import java.io.InputStream;

/**
 * @description: 文件服务引擎
 * @author: Jim.Chen
 * @create: 2022-03-30 11:07
 */
public interface IFileServiceClient {

    /**
     * 创建分类桶
     *
     * @param bucketName 桶名称
     * @return
     */
    boolean createBucket(String bucketName);

    /**
     * 删除分类桶
     *
     * @param bucketName 桶名称
     * @return
     */
    boolean deleteBucket(String bucketName);


    /**
     *  分类桶是否存在
     * @param bucketName
     * @return
     */
    boolean existBucket(String bucketName);


    /**
     * 文件上传
     *
     * @param metaData 文件元信息
     * @param in       文件字节流
     * @return
     */
    boolean uploadFile(FileUploadRequest metaData, InputStream in);

    /**
     * 获取文件的私有链接，可以直接访问，最大7天有限期
     *
     * @param privateUrlEvent
     * @return
     */
    String getPrivateUrl(FilePrivateUrlRequest privateUrlEvent);

    /**
     * 获取文件的公有链接，无法直接访问
     *
     * @param publicUrlRequest
     * @return
     */
    String getPublicUrl(FilePublicUrlRequest publicUrlRequest);

    /**
     * 删除文件
     *
     * @param deleteRequest
     * @return
     */
    boolean deleteFile(FileDeleteRequest deleteRequest);
}
