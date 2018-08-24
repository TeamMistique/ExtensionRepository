package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.exceptions.NotImageException;
import com.teammistique.extensionrepository.models.File;
import com.teammistique.extensionrepository.services.base.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private StorageService fileStorageService;

    @Autowired
    public FileController(StorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadFile")
    public File uploadFile(@RequestParam("file") MultipartFile file){
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/downloadFile/")
                .path(fileName)
                .toUriString();
        return new File(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadImage")
    public File uploadImage(@RequestParam("image") MultipartFile image) throws NotImageException {
        if(!Objects.requireNonNull(image.getContentType()).contains("image")) throw new NotImageException("The file that you're trying to upload is not an image.");
        String fileName = fileStorageService.storeFile(image);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/downloadFile/")
                .path(fileName)
                .toUriString();
        return new File(fileName, fileDownloadUri, image.getContentType(), image.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex){
            logger.info("Could not determine file type.");
        }

        if(contentType==null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
