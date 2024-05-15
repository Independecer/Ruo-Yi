package com.ruoyi.event;

import org.springframework.context.ApplicationEvent;

import java.nio.file.Path;

/**
 * @author liujunlin
 */
public class FormFileUploadSuccessEvent extends ApplicationEvent {
    private final String id;
    private final String userId;
    private final String mimeType;

    public FormFileUploadSuccessEvent(Path dataFile, String id, String userId, String mimeType) {
        super(dataFile);
        this.id = id;
        this.userId = userId;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
}
