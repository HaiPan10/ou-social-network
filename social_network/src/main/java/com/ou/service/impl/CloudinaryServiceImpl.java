package com.ou.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ou.service.interfaces.CloudinaryService;

@Service
public class CloudinaryServiceImpl implements CloudinaryService{
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        Map<String, String> response = cloudinary.uploader()
                        .upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

        System.out.println(response);
        return response.get("secure_url").toString();
                        // .get("secure_url").toString();
    }

    @Override
    public Map<String, String> deleteImage(String imageUrl) throws IOException {
        return cloudinary.uploader().destroy("glhiwkfnwsvn1cdnwgbl", ObjectUtils.emptyMap());
    }
    
}
