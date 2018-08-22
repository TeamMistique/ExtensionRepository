package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.models.File;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface FileStorageService {
    String storeFile(MultipartFile file);
    Resource loadFileAsResource(String fileName);
    File saveToDatabase(File file);
}
