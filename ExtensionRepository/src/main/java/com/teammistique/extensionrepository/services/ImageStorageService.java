package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.config.ImageStorageProperties;
import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.exceptions.FileStorageException;
import com.teammistique.extensionrepository.exceptions.MyFileNotFoundException;
import com.teammistique.extensionrepository.models.Image;
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

@Service
public class ImageStorageService implements StorageService<Image> {
    private final Path imageStorageLocation;
    private GenericRepository<Image> repository;

    @Autowired
    public ImageStorageService(ImageStorageProperties imageStorageProperties, GenericRepository<Image> repository) {
        this.repository = repository;
        this.imageStorageLocation = Paths.get(imageStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.imageStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create the directory where the uploaded images will be stored", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")){
                throw new FileStorageException("Sorry! Image name contains invalid path sequence"+fileName);
            }

            Path targetLocation = this.imageStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex){
            throw new FileStorageException("Could not store image " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.imageStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("Image not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("Image not found " + fileName, ex);
        }
    }

    @Override
    public Image saveToDatabase(Image file) {
        return repository.create(file);
    }
}
