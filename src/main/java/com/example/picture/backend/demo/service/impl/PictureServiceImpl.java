package com.example.picture.backend.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.picture.backend.demo.model.entity.Picture;
import com.example.picture.backend.demo.service.PictureService;
import com.example.picture.backend.demo.mapper.PictureMapper;
import org.springframework.stereotype.Service;

/**
* @author hw1621
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-08-06 23:36:30
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{

}




