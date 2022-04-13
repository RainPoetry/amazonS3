package com.rainpoetry.fileservice.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @description: 获取私有链接事件
 * @author: Jim.Chen
 * @create: 2022-03-30 22:06
 */
@Setter
@Getter
public class FilePrivateUrlRequest {

    @NotBlank
    private String bucketName;
    @NotBlank
    private String fileId;
    private LocalDateTime expiredDateTime;
}
