package com.ou.service.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    String uploadImage(MultipartFile image) throws IOException;
}
