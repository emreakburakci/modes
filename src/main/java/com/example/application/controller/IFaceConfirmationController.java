package com.example.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFaceConfirmationController {
    public ResponseEntity<Object> confirmPhoto(String identityNumber, MultipartFile frontPhotoFromMobile, MultipartFile rightPhotoFromMobile, MultipartFile leftPhotoFromMobile);

}
