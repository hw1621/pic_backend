package com.example.picture.backend.demo.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.picture.backend.demo.config.CosClientConfig;
import com.example.picture.backend.demo.exception.BusinessException;
import com.example.picture.backend.demo.exception.ErrorCode;
import com.example.picture.backend.demo.exception.ThrowUtils;
import com.example.picture.backend.demo.model.dto.file.UploadPictureResult;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Deprecated
public class FileManager {

    @Resource
    private CosManager cosManager;

    @Resource
    private COSClient cosClient;

    @Resource
    private CosClientConfig cosClientConfig;

    public UploadPictureResult uploadPicture(MultipartFile multipartFile, String uploadPathPrefix) {
        // Validate picture
        validPicture(multipartFile);

        //Construct file name and upload path
        String uuid = RandomUtil.randomString(16);
        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFilename = String.format("%s_%s_%s", DateUtil.formatDate(new Date()), uuid, FileUtil.getSuffix(originalFileName));
        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFilename);

        File file = null;
        try {
            file = File.createTempFile(uploadPath, null);
            multipartFile.transferTo(file);
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();

            int picWidth = imageInfo.getWidth();
            int picHeight = imageInfo.getHeight();
            double picScale = NumberUtil.round((picWidth * 1.0 / picHeight), 2).doubleValue();

            UploadPictureResult uploadPictureResult = new UploadPictureResult();
            uploadPictureResult.setUrl(cosClientConfig.getHost() + "/" + uploadPath);
            uploadPictureResult.setPicName(originalFileName);
            uploadPictureResult.setPicSize(FileUtil.size(file));
            uploadPictureResult.setPicWidth(imageInfo.getWidth());
            uploadPictureResult.setPicHeight(imageInfo.getHeight());
            uploadPictureResult.setPicFormat(imageInfo.getFormat());
            uploadPictureResult.setPicScale(picScale);

            return uploadPictureResult;
        } catch (Exception e) {
            log.error("file upload error, filepath = " + uploadPath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", uploadPath);
                }
            }
        }
    }


    private void validPicture(MultipartFile file) {
        ThrowUtils.throwIf(file == null, ErrorCode.PARAMS_ERROR, "File cannot be empty");
        long fileSize = file.getSize();
        final long ONE_M = 1024 * 1024;
        ThrowUtils.throwIf(fileSize > 2 * ONE_M, ErrorCode.PARAMS_ERROR, "File size must be less than 2MB");
        String fileSuffix = FileUtil.getSuffix(file.getOriginalFilename());
        final List<String> ALLOW_FORMAT = Arrays.asList("jpeg", "jpg", "png", "gif", "bmp");
        ThrowUtils.throwIf(!ALLOW_FORMAT.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "File type not allowed");
    }
}
