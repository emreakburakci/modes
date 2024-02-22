package com.example.application.utils;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {
    private static final int MAX_WIDTH = 2048;
    private static final int MAX_HEIGHT = 2048;
    public static byte[] resizeImage(byte[] imageData, int maxFileSizeBytes) throws IOException {
        // Load the original image
        System.out.println("RESIZE IMAGE RUNNING");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage originalImage = ImageIO.read(inputStream);

        // Calculate the scaling factor to fit within the maximum dimensions while maintaining aspect ratio
        double widthRatio = (double) MAX_WIDTH / originalImage.getWidth();
        double heightRatio = (double) MAX_HEIGHT / originalImage.getHeight();
        double scaleFactor = Math.min(widthRatio, heightRatio);

        // Check if resizing is necessary
        if (scaleFactor >= 1.0) {
            // Image already fits within the maximum dimensions
            return imageData;
        }

        // Calculate the new dimensions
        int newWidth = (int) (originalImage.getWidth() * scaleFactor);
        int newHeight = (int) (originalImage.getHeight() * scaleFactor);

        // Create a new resized image
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage bufferedResizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        bufferedResizedImage.getGraphics().drawImage(resizedImage, 0, 0, null);

        // Write the resized image to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedResizedImage, "jpg", outputStream);
        byte[] resizedImageData = outputStream.toByteArray();

        // Check if resized image exceeds the maximum file size
        if (resizedImageData.length > maxFileSizeBytes) {
            System.out.println("Resized image exceeds the maximum file size");
        }

        // Close streams
        inputStream.close();
        outputStream.close();

        return resizedImageData;
    }

}
