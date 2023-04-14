package com.forum.utils;

import com.forum.constants.Constants;
import com.forum.entity.config.AppConfig;
import com.forum.entity.dto.FileUploadDto;
import com.forum.enums.DateTimePatternEnum;
import com.forum.enums.FileUploadTypeEnum;
import com.forum.exception.BusinessException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

@Component
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    @Resource
    private AppConfig appConfig;

    @Resource
    private ImageUtils imageUtils;

    public FileUploadDto uploadFile2Local(MultipartFile file, String folder, FileUploadTypeEnum uploadTypeEnum) throws BusinessException {
        try {
            FileUploadDto fileUploadDto = new FileUploadDto();
            String originalFileName = file.getOriginalFilename();
            String fileSuffix = StringTools.getFileSuffix(originalFileName);
            if (originalFileName.length() > Constants.LENGTH_200) {
                originalFileName = StringTools.getFileName(originalFileName).substring(0, 190) + fileSuffix;
            }
            if (ArrayUtils.contains(uploadTypeEnum.getSuffixArray(), fileSuffix)) {
                throw new BusinessException("文件类型不正确");
            }
            String month = DateUtils.formal(new Date(), DateTimePatternEnum.YYYY_MM.getPattern());
            String baseFolder = appConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE;
            File targetFileFolder = new File(baseFolder + folder + month + "/");
            String fileName = StringTools.getRandomString(Constants.LENGTH_15) + fileSuffix;
            File targetFile = new File(targetFileFolder.getPath() + "/" + fileName);
            String localPath = month + "/" + fileName;

            if (uploadTypeEnum == FileUploadTypeEnum.AVATAR) {
                // TODO 头像上传
                targetFileFolder = new File(baseFolder + Constants.FILE_FOLDER_AVATAR_NAME);
                targetFile = new File(targetFileFolder.getPath() + "/" + folder + Constants.AVATAR_SUFFIX);
                localPath = folder + Constants.AVATAR_SUFFIX;
            }
            if (!targetFileFolder.exists()) {
                targetFileFolder.mkdirs();
            }
            file.transferTo(targetFile);

            // 压缩图片
            if (uploadTypeEnum == FileUploadTypeEnum.COMMENT_IMAGE) {
                String thumbnailName = targetFile.getName().replace(".", "_.");
                File thumbnail = new File(targetFile.getParent() + "/" + thumbnailName);
                Boolean thumbnailCreated = imageUtils.createThumbnail(targetFile, Constants.LENGTH_200, Constants.LENGTH_200, thumbnail);
                if (!thumbnailCreated) {
                    org.apache.commons.io.FileUtils.copyFile(targetFile, thumbnail);
                }
            } else if (uploadTypeEnum == FileUploadTypeEnum.AVATAR || uploadTypeEnum == FileUploadTypeEnum.ARTICLE_COVER) {
                // 相当于直接把用户自己上传的图片变小然后覆盖调，用小的
                imageUtils.createThumbnail(targetFile, Constants.LENGTH_200, Constants.LENGTH_200, targetFile);
            }

            fileUploadDto.setLocalPath(localPath);
            fileUploadDto.setOriginalFileName(originalFileName);
            return fileUploadDto;
        } catch (Exception e) {
            logger.error("上传文件失败");
            throw new BusinessException("上传文件失败");
        }
    }
}
