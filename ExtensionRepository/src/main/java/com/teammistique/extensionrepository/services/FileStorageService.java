package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.config.FileStorageProperties;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.exceptions.FileStorageException;
import com.teammistique.extensionrepository.exceptions.MyFileNotFoundException;
import com.teammistique.extensionrepository.models.File;
import com.teammistique.extensionrepository.services.base.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService implements StorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = String.valueOf(UUID.randomUUID());

        try{
            if(fileName.contains("..")){
                throw new FileStorageException("Sorry! Filename contains invalid path sequence"+fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex){
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                Files.delete(filePath);
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
