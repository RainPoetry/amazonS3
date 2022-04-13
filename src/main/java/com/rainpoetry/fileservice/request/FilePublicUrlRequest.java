package com.rainpoetry.fileservice.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 获取文件公链事件
 * @author: Jim.Chen
 * @create: 2022-03-30 22:08
 */

@Setter
@Getter
public class FilePublicUrlRequest {

    @NotBlank
    private String bucketName;
    @NotBlank
    private String fileId;
}
