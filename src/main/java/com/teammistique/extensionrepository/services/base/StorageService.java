package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.exceptions.MyFileNotFoundException;
import com.teammistique.extensionrepository.models.File;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface StorageService {
    String storeFile(MultipartFile file);
    Resource loadFileAsResource(String fileName) throws MyFileNotFoundException;
    void deleteFile(String fileName) throws MyFileNotFoundException;
}
