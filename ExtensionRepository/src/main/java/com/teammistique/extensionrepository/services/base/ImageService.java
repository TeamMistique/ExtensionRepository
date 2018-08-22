package com.teammistique.extensionrepository.services.base;

import com.teammistique.extensionrepository.models.Image;

public interface ImageService {

    Image createImage(Image extension);

    Image getImageById(int id);

}
