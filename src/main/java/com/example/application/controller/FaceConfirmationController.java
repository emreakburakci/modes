package com.example.application.controller;

import com.example.application.services.FaceConfirmationService;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@MultipartConfig(maxRequestSize = 524388)

public class FaceConfirmationController implements IFaceConfirmationController {
    private FaceConfirmationService faceConfirmationService;

    public FaceConfirmationController(FaceConfirmationService faceConfirmationService) {
        this.faceConfirmationService = faceConfirmationService;
    }

    @Override
    @PostMapping("/confirmPhoto")
    public ResponseEntity<Object> confirmPhoto(@RequestParam("identityNumber") String identityNumber, @RequestParam("frontPhoto") MultipartFile frontPhotoFromMobile, @RequestParam("rightPhoto") MultipartFile rightPhotoFromMobile, @RequestParam("leftPhoto") MultipartFile leftPhotoFromMobile) {
        return faceConfirmationService.confirmPhoto(identityNumber, frontPhotoFromMobile, rightPhotoFromMobile, leftPhotoFromMobile);
    }
}
