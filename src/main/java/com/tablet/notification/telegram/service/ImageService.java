package com.tablet.notification.telegram.service;

import com.tablet.notification.telegram.model.Image;
import com.tablet.notification.telegram.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public void deleteAll() {
        imageRepository.deleteAll();
    }

    public Image getImage(int id) {
        return imageRepository.findById(id).get();
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
