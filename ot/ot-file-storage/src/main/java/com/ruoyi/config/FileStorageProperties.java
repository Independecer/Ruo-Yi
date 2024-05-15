package com.ruoyi.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
@Component
@Data
@ConfigurationProperties(prefix = "ruoyi.file-storage")
public class FileStorageProperties {
    /**
     * 默认存储平台
     */
    private String defaultPlatform;

    /**
     * 缩略图后缀，例如【.min.jpg】【.png】
     */
    private String thumbnailSuffix;

    /**
     * 允许的文件类型
     */
    private List<String> allowMime;

    /**
     * The number of milliseconds after which an upload expires and can be removed
     */
    private Duration uploadExpirationPeriod;
    /**
     * 缩略图配置
     */
    private Thumbnail thumbnail;
    /**
     * 水印配置
     */
    private WaterMark waterMark;


    private LocalConfig local;

    private TencentCosConfig tencentCos;

    private QiNiuKodoConfig qiNiuKodo;

    private BaiduBosConfig baiduBos;

    private AliYunOssConfig aliyunOss;

    /**
     * 基本的存储平台配置
     */
    @Data
    @Accessors(chain = true)
    public static class BaseConfig {

        /**
         * 存储平台
         */
        private String platform;

        /**
         * 基础路径
         */
        private String basePath;

        /**
         * 允许的文件类型
         */
        private List<String> allowMime;

        /**
         * 缩略图配置
         */
        private Thumbnail thumbnail;
        /**
         * 水印配置
         */
        private WaterMark waterMark;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class LocalConfig extends BaseConfig {

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class AliYunOssConfig extends BaseConfig {
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class QiNiuKodoConfig extends BaseConfig {
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class TencentCosConfig extends BaseConfig {
        private String enableStorage;
        private String secretId;
        private String secretKey;
        private String region;
        private String bucketName;
        private String domain;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class BaiduBosConfig extends BaseConfig {
    }


    @Data
    public static class Thumbnail {
        private int width;
        private int height;
        private float quality;
        private String walterMark;
    }


    @Data
    public static class WaterMark {
        private int minWidth;
        private int minHeight;
        private boolean enabled;
    }


}




