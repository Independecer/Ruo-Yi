package com.onethinker.config;

import com.onethinker.platform.FileStorage;
import com.onethinker.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
@Slf4j
@Configuration
public class FileStorageAutoConfiguration {

    private final ConfigurationPropertiesAutoConfiguration configurationPropertiesAutoConfiguration;
    /**
     * 存储平台
     */
    private List<FileStorage> fileStorageList = new ArrayList<>();

    public FileStorageAutoConfiguration(ConfigurationPropertiesAutoConfiguration configurationPropertiesAutoConfiguration) {
        this.configurationPropertiesAutoConfiguration = configurationPropertiesAutoConfiguration;
    }

    /**
     * 初始化各平台实例
     * @return
     */
    @Bean
    public FileStorageService fileStorageService(@Autowired FileStorageProperties properties) {
        if (properties == null) throw new RuntimeException("properties 不能为 null");
        // 初始化各个存储平台
        String pageStr = "com.onethinker.platform.impl.";
        buildFileStorage(properties,pageStr + "LocalFileStorage");
        buildFileStorage(properties,pageStr + "TencentCosFileStorage");
        buildFileStorage(properties,pageStr + "HuaweiObsFileStorage");
        // 本体
        FileStorageService service = new FileStorageService();
        service.setProperties(properties);
        service.setFileStorageList(new CopyOnWriteArrayList<>(fileStorageList));
        return service;
    }

    /**
     * 根据配置文件内容创建对应平台信息
     *
     * @param config             配置内容
     * @param className          实例化类
     */
    public void buildFileStorage(FileStorageProperties config, String className) {
        if (Objects.nonNull(config)) {
            try {
                Class<?> clazz = Class.forName(className);
                Constructor<?> constructor = clazz.getConstructor(FileStorageProperties.class);
                constructor.setAccessible(true);
                FileStorage instance = (FileStorage) constructor.newInstance(config);
                fileStorageList.add(instance);
            } catch (Exception e) {
                log.warn("className:{} exception:",className,e);
            }
        }
    }
}
