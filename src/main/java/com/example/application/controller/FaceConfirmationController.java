/**
 * FaceConfirmationController is a REST controller responsible for handling requests related to face confirmation.
 * It receives HTTP POST requests for confirming identity using facial recognition.
 *
 * @author emreakburakcı
 * @version 1.0
 */
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

    /**
     * Constructs a new FaceConfirmationController with the specified FaceConfirmationService.
     *
     * @param faceConfirmationService the service responsible for handling face confirmation operations
     */
    public FaceConfirmationController(FaceConfirmationService faceConfirmationService) {
        this.faceConfirmationService = faceConfirmationService;
    }

    /**
     * Handles HTTP POST requests to confirm identity using facial recognition.
     * Receives identity number and three photos (front, right, and left) from a mobile application.
     *
     * @param identityNumber       the identity number associated with the user
     * @param frontPhotoFromMobile the front-facing photo of the user from the mobile application
     * @param rightPhotoFromMobile the right-facing photo of the user from the mobile application
     * @param leftPhotoFromMobile  the left-facing photo of the user from the mobile application
     * @return a ResponseEntity with the confirmation status and any associated message
     */
    @Override
    @PostMapping("/confirmPhoto")
    public ResponseEntity<Object> confirmPhoto(@RequestParam("identityNumber") String identityNumber, @RequestParam("frontPhoto") MultipartFile frontPhotoFromMobile, @RequestParam("rightPhoto") MultipartFile rightPhotoFromMobile, @RequestParam("leftPhoto") MultipartFile leftPhotoFromMobile) {
        return faceConfirmationService.confirmPhoto(identityNumber, frontPhotoFromMobile, rightPhotoFromMobile, leftPhotoFromMobile);
    }
}
