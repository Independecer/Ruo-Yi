package com.onethinker.service;

import cn.hutool.core.util.StrUtil;
import com.onethinker.bean.FilePersistInfo;
import com.onethinker.config.FileStorageProperties;
import com.onethinker.util.Tools;
import com.ruoyi.common.constant.SystemConst;
import com.onethinker.platform.FileStorage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yangyouqi
 * @date 2024/5/14
 * 用来处理文件存储，对接多个平台
 */
@Slf4j
@Getter
@Setter
public class FileStorageService {

    private FileStorageProperties properties;

    private CopyOnWriteArrayList<FileStorage> fileStorageList;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SAVE_FILE_SQL = "insert into t_file(" +
            "id," +
            "tenant_id," +
            "file_name," +
            "fingerprint," +
            "file_size," +
            "path," +
            "mime_type," +
            "detect_mime," +
            "extension," +
            "app_name," +
            "create_user_id," +
            "remark," +
            "creator_ip_address," +
            "host_name," +
            "disk_path,platform)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SAVE_FILE_RELATION_SQL = "insert into t_file_relation(" +
            "id," +
            "tenant_id," +
            "file_id," +
            "relation_type," +
            "relation_target," +
            "create_time)values(?,?,?,?,?,?)";

    /**
     * 获取默认的存储平台
     */
    public <T extends FileStorage> T getFileStorage() {
        return this.getFileStorage(properties.getDefaultPlatform());
    }

    /**
     * 获取对应的存储平台
     */
    public <T extends FileStorage> T getFileStorage(String platform) {
        for (FileStorage fileStorage : fileStorageList) {
            if (fileStorage.getPlatform().equals(platform)) {
                return Tools.cast(fileStorage);
            }
        }
        return null;
    }

    /**
     * 获取对应的存储平台，如果存储平台不存在则抛出异常
     */
    public <T extends FileStorage> T getFileStorageVerify(String platform) {
        T fileStorage = this.getFileStorage(platform);
        if (fileStorage == null)
            throw new RuntimeException(StrUtil.format("没有找到对应的存储平台！platform:{}", platform));
        return fileStorage;
    }

    /**
     * 保存实体
     *
     * @param filePersistInfo
     * @param detectMime
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveFileInfo(FilePersistInfo filePersistInfo, String detectMime) {
        Object[] params = {
                filePersistInfo.getFileInfo().getId(),
                filePersistInfo.getFileInfo().getTenantId(),
                filePersistInfo.getFileInfo().getFileName(),
                filePersistInfo.getFileInfo().getFingerprint(),
                filePersistInfo.getFileInfo().getFileSize(),
                filePersistInfo.getFileInfo().getPath(),
                filePersistInfo.getFileInfo().getMimeType(),
                detectMime,
                filePersistInfo.getFileInfo().getExtension(),
                filePersistInfo.getFileInfo().getAppName(),
                filePersistInfo.getFileInfo().getCreateUserId(),
                filePersistInfo.getFileInfo().getRemark(),
                filePersistInfo.getFileInfo().getCreatorIpAddresses(),
                filePersistInfo.getFileInfo().getHostName(),
                filePersistInfo.getFileInfo().getDiskPath(),
                this.getFileStorage().getPlatform()
        };
        jdbcTemplate.update(SAVE_FILE_SQL, params);
        Object[] params2 = {
                UUID.randomUUID().toString().replace("-", ""),
                SystemConst.DEFAULT_TENANT_ID,
                filePersistInfo.getFileInfo().getId(),
                filePersistInfo.getRelationType(),
                filePersistInfo.getRelationTarget(),
                LocalDateTime.now()
        };
        jdbcTemplate.update(SAVE_FILE_RELATION_SQL, params2);
    }

    private Map<String, Object> getFileInfo(String id) {
        return jdbcTemplate.queryForMap("select * from t_file where id = ?",id);
    }
}
