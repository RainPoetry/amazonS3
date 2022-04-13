package com.rainpoetry.fileservice.assembler;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.rainpoetry.fileservice.request.FileUploadRequest;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @description: ObjectMetadata 数据转换
 * @author: Jim.Chen
 * @create: 2022-03-31 08:35
 */
@Component
public class ObjectMetaDataAssembler {

    private static final String CONTENT_DISPOSITION_PREFIX = "attachment;filename=";

    /**
     * FileMetaData -> ObjectMetadata
     *
     * @param fileMetaData 文件元信息
     * @return
     */
    public ObjectMetadata toAmazonS3Meta(FileUploadRequest fileMetaData) {
        ObjectMetadata objectMetadata = new ObjectMetadata();

        // 设置上传文件大小
        if (fileMetaData.getFileSize() != null && fileMetaData.getFileSize() > 0) {
            objectMetadata.setContentLength(fileMetaData.getFileSize());
        }

        // 设置文件类型
        if (StringUtils.isNotBlank(fileMetaData.getContentType())) {
            objectMetadata.setContentType(fileMetaData.getContentType());
        }

        // 用于控制下载的文件名
        objectMetadata.setContentDisposition(CONTENT_DISPOSITION_PREFIX + fileMetaData.getOriginFileName());

        // 其他配置信息
        if (MapUtils.isNotEmpty(fileMetaData.getExtra())) {
            objectMetadata.setUserMetadata(fileMetaData.getExtra());
        }
        return objectMetadata;
    }
}
