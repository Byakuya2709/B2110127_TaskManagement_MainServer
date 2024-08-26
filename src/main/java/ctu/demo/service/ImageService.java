/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.service;

import ctu.demo.exception.AppException;
import static ctu.demo.exception.ErrorCode.NOTFOUND_ERROR;
import ctu.demo.model.Image;
import ctu.demo.model.Product;
import ctu.demo.repository.ImageRepository;
import ctu.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image getimageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new AppException(NOTFOUND_ERROR, id));
    }
}
