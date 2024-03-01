/**
 * FaceConfirmationService is a service class responsible for confirming a user's identity
 * by comparing the provided photos with the photos stored in the database.
 *
 * @author emreakburakcı
 * @version 1.0
 */

package com.example.application.services;

import com.example.application.data.entities.User;
import com.example.application.data.repositories.UserRepository;
import com.example.application.exceptions.UserNotFoundException;
import com.example.application.utils.ImageUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

@Service
public class FaceConfirmationService {

    // Face++ API URL and credentials
    private static final String FACE_PLUS_PLUS_API_URL = "https://api-us.faceplusplus.com/facepp/v3/compare";
    private static final String apiKey = "Ik1X9kDUd81XibNwmk9JUXL_VIifTooD";
    private static final String apiSecret = "CrhF0rhDKW6w3owGcriaoVbxCNdiCK8G";

    // Confidence threshold for face comparison
    private static final double CONFIDENCE_TRESHOLD = 80.0;
    private final UserRepository userRepository;
    private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();

    /**
     * Constructs a FaceConfirmationService with the provided UserRepository.
     *
     * @param userRepository The repository for accessing user data.
     */
    public FaceConfirmationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Confirms the identity of a user by comparing the provided photos with the stored photos.
     *
     * @param identityNumber       The identity number of the user.
     * @param frontPhotoFromMobile The front photo uploaded by the user.
     * @param rightPhotoFromMobile The right-side photo uploaded by the user.
     * @param leftPhotoFromMobile  The left-side photo uploaded by the user.
     * @return ResponseEntity indicating success or failure of identity confirmation.
     */
    public ResponseEntity<Object> confirmPhoto(String identityNumber, MultipartFile frontPhotoFromMobile, MultipartFile rightPhotoFromMobile, MultipartFile leftPhotoFromMobile) {

        try {
            System.out.println("confirmPhoto running");

            User user = userRepository.findById(identityNumber).orElse(null);

            System.out.println("USER FETCHED FROM DB");

            if (user != null) {

                byte[] userPhotoFront = user.getPictureFront();
                byte[] userPhotoLeft = user.getPictureLeft();
                byte[] userPhotoRight = user.getPictureRight();


                System.out.println("USER PHOTO FETCEHD FROM USER OBJECT");

                byte[] resizedFrontPhotoFromMobile = ImageUtils.resizeImage(frontPhotoFromMobile.getBytes(), 2);
                byte[] resizedLeftPhotoFromMobile = ImageUtils.resizeImage(leftPhotoFromMobile.getBytes(), 2);
                byte[] resizedRightPhotoFromMobile = ImageUtils.resizeImage(rightPhotoFromMobile.getBytes(), 2);


                byte[] resizedUserFrontPhoto = ImageUtils.resizeImage(userPhotoFront, 2);
                byte[] resizedUserLeftPhoto = ImageUtils.resizeImage(userPhotoLeft, 2);
                byte[] resizedUserRightPhoto = ImageUtils.resizeImage(userPhotoRight, 2);

                System.out.println("IMAGES RESIZED");


                boolean isFrontMatched = compareFaces(resizedFrontPhotoFromMobile, resizedUserFrontPhoto);
                boolean isLeftMatched = compareFaces(resizedLeftPhotoFromMobile, resizedUserLeftPhoto);
                boolean isRightMatched = compareFaces(resizedRightPhotoFromMobile, resizedUserRightPhoto);


                System.out.println("COMPARE FACES RUNNED, FRONT RESULT: " + isFrontMatched);
                System.out.println("COMPARE FACES RUNNED, RIGHT RESULT: " + isRightMatched);
                System.out.println("COMPARE FACES RUNNED, LEFT RESULT: " + isLeftMatched);


                if (isFrontMatched && isLeftMatched && isRightMatched) {
                    return ResponseEntity.ok().body("{\"success\": true}");

                } else {
                    return ResponseEntity.ok().body("{\"success\": false}");

                }
            } else {
                throw new UserNotFoundException();
            }

        } catch (Exception e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false}");
        }
    }

    /**
     * Compares faces using Face++ API.
     *
     * @param photoFromMobile Photo from mobile to compare.
     * @param userPhoto       User photo for comparison.
     * @return True if the faces match with confidence above the threshold, false otherwise.
     */
    public boolean compareFaces(byte[] photoFromMobile, byte[] userPhoto) {


        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", "dam4ZdTkSsZOUAiR4oQpP3DRnjEz1fcD");
        map.put("api_secret", "0MOCfpum1Lec06EMOzuJPOEa_EhM4Ttg");

        byteMap.put("image_file1", photoFromMobile);
        byteMap.put("image_file2", userPhoto);

        try {
            // Connecting and retrieving the JSON results
            byte[] bacd = post(FACE_PLUS_PLUS_API_URL, map, byteMap);
            String jsonStr = new String(bacd);

            // Parse the JSON and get the confidence value
            JSONObject obj = new JSONObject(jsonStr);
            System.out.println(obj);
            double confidence = obj.getDouble("confidence");
            System.out.println("CONFIDENCE VALUE: " + confidence);
            return (confidence > CONFIDENCE_TRESHOLD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sends a POST request to the specified URL with parameters and files.
     *
     * @param url     The URL to send the request to.
     * @param map     Parameters for the request.
     * @param fileMap Files to be included in the request.
     * @return The response as a byte array.
     * @throws Exception if an error occurs during the request.
     */
    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if (fileMap != null && fileMap.size() > 0) {
            Iterator fileIter = fileMap.entrySet().iterator();
            while (fileIter.hasNext()) {
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try {
            if (code == 200) {
                ins = conne.getInputStream();
            } else {
                ins = conne.getErrorStream();
            }
        } catch (SSLException e) {
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while ((len = ins.read(buff)) != -1) {
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }

    private static String encode(String value) throws Exception {
        return URLEncoder.encode(value, "UTF-8");
    }

    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }


}
