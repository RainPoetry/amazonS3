package com.rainpoetry.fileservice.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 文件元信息
 * @author: Jim.Chen
 * @create: 2022-03-30 09:24
 */
@Setter
@Getter
public class FileUploadRequest {

    /** bucket 名称 - 必填 */
    @NotBlank
    private String bucketName;
    /** 文件 Id - 必填 */
    @NotBlank
    private String fileId;
     /**  文件目录 - 选填 */
    private String dir;
     /**  文件名称 - 选填 */
    private String fileName;
     /**  文件大小 - 最填 */
    private Long fileSize;
     /**  文件类型 - 最好填 */
    private String contentType;
    private String originFileName;
     /**  其他信息 - 选填 */
    private Map<String, String> extra = new HashMap<>();

    private void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    private FileUploadRequest set(String key, String value) {
        this.extra.put(key, value);
        return this;
    }

    public Map<String, String> getExtra() {
        return new HashMap<>(extra);
    }

}
