package com.ruoyi.platform;

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.common.constant.ServicePathConstant;
import com.ruoyi.common.webp.exc.WebpEncodeUtil;
import com.ruoyi.config.FileStorageProperties.Thumbnail;
import com.ruoyi.config.FileStorageProperties.WaterMark;
import com.ruoyi.event.FormFileUploadSuccessEvent;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static java.nio.file.Files.exists;

/**
 * @author yangyouqi
 * @date 2024/5/14
 */
public interface FileStorage {

    static final Logger log = LoggerFactory.getLogger(FileStorage.class);

    String FILE_EXTENSION_FLAG = ".";
    String DATA_FILE = "data";
    final int DEFAULT_THUMBNAIL_SIZE = 150;
    final String IMAGE = "image";
    final String WEBP_FILE = "webp";
    final String WEBP_ORIGINAL_FILE = "original.webp";
    final String THUMBNAIL_FILE = "thumb.jpg";
    final String WATERMARK_FILE = "watermark.jpg";
    final String WEBP_MIME = "image/webp";
    final String WEBP_EXTENSION = "." + WEBP_FILE;
    final String WEBP_ORIGIN_EXTENSION = "." + WEBP_ORIGINAL_FILE;
    final String JPG_MIME = "image/jpeg";
    final String JPG_EXTENSION = ".jpg";
    final String SERVICE_UPLOAD_CONTENT_TYPE = "application/offset+octet-stream";
    String UPLOAD_PATH = ServicePathConstant.PREFIX_PUBLIC_PATH + "/tus/upload";

    /**
     * 获取平台
     */
    String getPlatform();

    /**
     * 设置平台
     *
     * @param platform 平台标识
     */
    void setPlatform(String platform);

    /**
     * 设置文件名称
     */
    FileStorage setName(String name);

    /**
     * 上传文件
     *
     * @param file 文件内容
     * @return
     */
    FileInfo upload(MultipartFile file);


    /**
     * 上传文件
     *
     * @return
     */
    FileInfo upload();

    /**
     * 设置文件来源信息
     *
     * @param source   文件资源
     * @param fileName 文件名称
     * @return
     */
    FileStorage serFile(MultipartFile source, String fileName);

    /**
     * 检测文件的MIME类型
     *
     * @param fileInfo fileInfo
     * @return
     */
    String detectMime(FileInfo fileInfo);

    /**
     * 生成其他图片信息
     *
     * @param formFileUploadSuccessEvent formFileUploadSuccessEvent
     */
    void handleFormFileUploadSuccessEvent(FormFileUploadSuccessEvent formFileUploadSuccessEvent);

    /**
     * 获取文件全路径（相对存储平台的存储路径）
     *
     * @param basePath 基本路径
     */
    default String getFileKey(String basePath, FileInfo fileInfo) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        fileInfo.setId(id);
        // 获取文件全路径（相对存储平台的存储路径）
        return String.join("/", basePath, "uploads", "form", String.valueOf(now.getYear()), String.valueOf(now.getMonthValue()), String.valueOf(now.getDayOfMonth()), id, "data");
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件全名
     * @return 文件扩展名
     */
    default String getExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        String ext = ".tar.gz";
        if (fileName.endsWith(ext)) {
            return ext;
        }
        if (StringUtils.contains(fileName, FILE_EXTENSION_FLAG)) {
            return fileName.substring(fileName.lastIndexOf(FILE_EXTENSION_FLAG));
        }
        return null;
    }

    default String getExtension(String fileName, List<String> extensions) {
        String extension = getExtension(fileName);
        if (CollectionUtils.isNotEmpty(extensions) && !extensions.contains(extension)) {
            throw new RuntimeException("不允许上传类型：" + extension);
        }
        return StringUtils.lowerCase(extension);
    }

    default String queryDetectMime(MultipartFile file, List<String> allowMime) {
        Tika tika = new Tika();
        try {
            String detect = tika.detect(file.getInputStream());
            if (!StringUtils.isBlank(detect)) {
                String flag = ";";
                String mime = detect;
                if (detect.contains(flag)) {
                    mime = detect.split(flag)[0];
                }
                if (CollectionUtil.isNotEmpty(allowMime) && !allowMime.contains(mime)) {
                    throw new RuntimeException(detect);
                }
            }
            return detect;
        } catch (IOException e) {
        }
        return null;
    }

    default int[] getImgSize(File file) {
        try {
            BufferedImage img = ImageIO.read(file);
            return new int[]{img.getWidth(), img.getHeight()};
        } catch (IOException e) {
            return new int[]{0, 0};
        }
    }

    default int[] getImgSize(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedImage img = ImageIO.read(inputStream);
            return new int[]{img.getWidth(), img.getHeight()};
        } catch (IOException e) {
            return new int[]{0, 0};
        }
    }

    default Path getTransFile(Path dataFile, String type) {
        return Paths.get(dataFile.getParent().toString(), String.join(".", DATA_FILE, type));
    }

    default String getTransFile(String path, String type) {
        if (ObjectUtils.isNotEmpty(path) && !path.endsWith("/")) {
            path = path + "/";
        }
        return path + String.join(".", DATA_FILE, type);
    }

    default BufferedImage getWatermark(String userId) {
        final Font font = new Font(null, Font.PLAIN, 12);
        BufferedImage image = new BufferedImage(262, 35, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        image = g.getDeviceConfiguration().createCompatibleImage(260, 35, Transparency.TRANSLUCENT);

        int y = 0;
        int divider30 = 30;

        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.YELLOW);
        g.setFont(font);
        String txt = "IOPS" + userId;
        g.drawString(txt, 0, y + divider30);
        g.dispose();
        return image;
    }

    default InputStream processWatermark(MultipartFile source, FileInfo fileInfo, WaterMark waterMark) throws IOException {
        log.debug("Start Process Watermark:{},{}", fileInfo.getId(), fileInfo.getCreateUserId());
        if (waterMark == null || !waterMark.isEnabled()) {
            throw new RuntimeException("Process Watermark is null");
        }
        int minWidth = 300;
        int minHeight = 100;
        if (waterMark.getMinWidth() > 0) {
            minWidth = waterMark.getMinWidth();
        }
        if (waterMark.getMinHeight() > 0) {
            minHeight = waterMark.getMinHeight();
        }
        int[] imgSize = getImgSize(source);
        if (imgSize[0] > minWidth && imgSize[1] > minHeight) {
            log.debug("Start Process Watermark:{}", fileInfo.getId());
            Thumbnails.Builder<BufferedImage> builder = Thumbnails.of(ImageIO.read(source.getInputStream()));
            BufferedImage bufferedImage = getWatermark(fileInfo.getCreateUserId());
            builder.watermark(Positions.CENTER, bufferedImage, 0.5f);
            if (imgSize[0] > (minWidth + minWidth)) {
                builder.watermark(Positions.BOTTOM_LEFT, bufferedImage, 0.5f)
                        .watermark(Positions.BOTTOM_RIGHT, bufferedImage, 0.5f)
                        .watermark(Positions.TOP_LEFT, bufferedImage, 0.5f)
                        .watermark(Positions.TOP_RIGHT, bufferedImage, 0.5f);
            } else {
                builder.watermark(Positions.BOTTOM_CENTER, bufferedImage, 0.5f)
                        .watermark(Positions.TOP_CENTER, bufferedImage, 0.5f);
            }
            // 将图像写入到 ByteArrayOutputStream 中
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            builder.scale(1).outputFormat(fileInfo.getExtension().replace(".","")).toOutputStream(os);
            return new ByteArrayInputStream(os.toByteArray());
        }
        return null;
    }


    default InputStream processThumbnail(MultipartFile source,FileInfo fileInfo,Thumbnail thumbnail) throws IOException {
        log.debug("Start Process Thumbnail:{}", fileInfo.getId());
        int width = DEFAULT_THUMBNAIL_SIZE;
        int height = DEFAULT_THUMBNAIL_SIZE;
        float quality = 0.5f;
        if (thumbnail != null) {
            width = thumbnail.getWidth() <= 0 ? DEFAULT_THUMBNAIL_SIZE : thumbnail.getWidth();
            height = thumbnail.getHeight() <= 0 ? DEFAULT_THUMBNAIL_SIZE : thumbnail.getHeight();
            quality = thumbnail.getQuality();
        }
        // 将图像写入到 ByteArrayOutputStream 中
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Thumbnails.of(source.getInputStream())
                .size(width, height)
                .outputQuality(quality).toOutputStream(os);

        return new ByteArrayInputStream(os.toByteArray());
    }

    default Path processWebp(MultipartFile source,FileInfo fileInfo) throws IOException {
        log.debug("Start Process Webp:{}", fileInfo.getId());
        // 创建临时文件
        File tempFile = File.createTempFile("temp", fileInfo.getExtension());
        // 将MultipartFile转换为临时文件
        source.transferTo(tempFile);
        Path dataFile = tempFile.toPath();
        Path waterMarkFile = getTransFile(dataFile, WATERMARK_FILE);
        if (exists(waterMarkFile)) {
            dataFile = waterMarkFile;
        }
        Path webpFile = getTransFile(dataFile, WEBP_FILE);
        int[] imgSize = getImgSize(dataFile.toFile());
        int minWidth = 400;
        int minHeight = 300;
        if (imgSize[0] > minWidth && imgSize[1] > minHeight) {
            WebpEncodeUtil.toWebp(dataFile, webpFile, (int) (imgSize[0] * 0.8), (int) (imgSize[1] * 0.8));
        } else {
            WebpEncodeUtil.toWebp(dataFile, webpFile);
        }
        log.debug("Finish Process Webp:{}", fileInfo.getId());
        return webpFile;
    }

    default Path processWebpOriginal(MultipartFile source,FileInfo fileInfo) throws IOException {
        log.debug("Start Process Webp Original:{}", fileInfo.getId());
        // 创建临时文件
        File tempFile = File.createTempFile("temp", fileInfo.getExtension());
        // 将MultipartFile转换为临时文件
        source.transferTo(tempFile);
        Path dataFile = tempFile.toPath();
        Path webpOriginalFile = getTransFile(dataFile, WEBP_ORIGINAL_FILE);
        if (exists(webpOriginalFile)) {
            dataFile = webpOriginalFile;
        }
        WebpEncodeUtil.toWebp(dataFile, webpOriginalFile);
        log.debug("Finish Process Webp Original:{}", fileInfo.getId());
        return webpOriginalFile;
    }
}
