package com.ruoyi.onethinker.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author : yangyouqi
 * @date : 2023/10/25 0025 10:32
 */
public class SysFileInfoReqDTO {
    /**
     * 当前上传唯一标识
     */
    private String uid;
    /**
     * 文件名
     */
    private String name;
    /**
     * 分片对象
     */
    private MultipartFile file;
    /**
     * MD5
     */
    private String md5;

    /**
     * 文件归属模块
     *
     */
    private String module;

    /**
     * 文件类型
     * @return
     */
    private Integer fileType;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
