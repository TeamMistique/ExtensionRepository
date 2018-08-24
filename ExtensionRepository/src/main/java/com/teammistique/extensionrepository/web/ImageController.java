package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.models.Image;
import com.teammistique.extensionrepository.services.ImageStorageService;
import com.teammistique.extensionrepository.services.base.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private StorageService<Image> imageStorageService;

    @Autowired
    public ImageController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping("/uploadImage")
    public Image uploadImage(@RequestParam("image") MultipartFile image){
        String imageName = imageStorageService.storeFile(image);

        String imageDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadImage")
                .path(imageName)
                .toUriString();

        Image imageToSave = new Image(imageDownloadUri);
        imageStorageService.saveToDatabase(imageToSave);
        return imageToSave;
    }
}
