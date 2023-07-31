package com.ou.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ou.service.interfaces.UploadFileService;

@Service
public class UploadFileServiceImpl implements UploadFileService{
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        return cloudinary.uploader()
                        .upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"))
                        .get("secure_url").toString();
    }
    
}
