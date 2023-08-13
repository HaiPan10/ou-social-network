package com.ou.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadImage(MultipartFile image) throws IOException;
    Map<String, String> deleteImage() throws IOException;
}
