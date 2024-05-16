package com.ruoyi.service;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.bean.FilePersistInfo;
import com.ruoyi.common.constant.SystemConst;
import com.ruoyi.config.FileStorageProperties;
import com.ruoyi.platform.FileStorage;
import com.ruoyi.util.Tools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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

    public void updateDateAttr(String id, Map<String, Object> attrs) {
        if (attrs == null || attrs.isEmpty()) {
            return;
        }
//        FileInfo fileInfo = this.getFileInfo(id).orElseThrow(() -> new RuntimeException("文件", id));
//        Set<String> nullAttrs = attrs.entrySet().parallelStream().filter(entry -> entry.getValue() == null).map(Map.Entry::getKey).collect(Collectors.toSet());
//        String sql = "update iuser.t_file_attr set del_flag = 1,update_time = ?, update_user_id = ? where tenant_id = ? and file_id = ? and attr_name in " + SqlUtil.getInSql(nullAttrs);
//        List<Object> args = new ArrayList<>(10);
//        String userId = KeycloakClientUtil.getUserInfo().map(UserInfo::getId).orElse(null);
//        args.add(DateTimeUtil.now());
//        args.add(userId);
//        args.add(SystemConst.DEFAULT_TENANT_ID);
//        args.add(id);
//        args.addAll(nullAttrs);
//        transactionService.getJdbcTemplate().update(sql, args.toArray());
//        sql = "insert into iuser.t_file_attr(id,tenant_id,file_id,attr_name,attr_value,px,create_user_id,create_time,del_flag)values(?,?,?,?,?,?,?,?,?)";
//        List<Object[]> batchArgs = attrs.entrySet()
//                .parallelStream()
//                .filter(entry -> entry.getValue() != null)
//                .map(entry -> new Object[]{RandomUtil.ulid(), SystemConst.DEFAULT_TENANT_ID, id, entry.getKey(), entry.getValue(), 0, userId, DateTimeUtil.now(), 0})
//                .toList();
//        transactionService.getJdbcTemplate().batchUpdate(sql, batchArgs);
//        fileInfo.setAttr(attrs);
    }

    private Map<String, Object> getFileInfo(String id) {
        return jdbcTemplate.queryForMap("select * from t_file where id = ?",id);
    }
}
