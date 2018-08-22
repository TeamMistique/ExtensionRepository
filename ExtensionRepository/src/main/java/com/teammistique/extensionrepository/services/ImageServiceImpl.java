package com.teammistique.extensionrepository.services;

import com.teammistique.extensionrepository.data.base.GenericRepository;
import com.teammistique.extensionrepository.models.Image;
import com.teammistique.extensionrepository.services.base.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private GenericRepository<Image> imageRepository;

    @Autowired
    public ImageServiceImpl(GenericRepository<Image> imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public Image createImage(Image image) {
        return imageRepository.create(image);
    }

    @Override
    public Image getImageById(int id) {
        return imageRepository.findById(id);
    }
}
