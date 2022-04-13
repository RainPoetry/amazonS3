package com.rainpoetry.fileservice.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @description: 删除文件事件
 * @author: Jim.Chen
 * @create: 2022-03-30 22:11
 */

@Setter
@Getter
public class FileDeleteRequest {

    @NotBlank
    private String bucketName;
    @NotBlank
    private String fileId;
}
