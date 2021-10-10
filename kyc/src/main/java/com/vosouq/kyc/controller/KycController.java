package com.vosouq.kyc.controller;

import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.kyc.exception.*;
import com.vosouq.kyc.faranam.Inquiry;
import com.vosouq.kyc.faranam.InquirySoap;
import com.vosouq.kyc.faranam.NOCRPersonModel;
import com.vosouq.kyc.faranam.NocrModel;
import com.vosouq.kyc.model.User;
import com.vosouq.kyc.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.Date;

@VosouqRestController
@Slf4j
public class KycController {

    private final ProfileService profileService;

    public KycController(ProfileService profileService) {

        this.profileService = profileService;
    }

    @PostMapping("/verify-info")
    @NoContent
    public void verifyInfo(
            @RequestParam(name = "nationalCode") String nationalCode,
            @RequestParam(name = "birthDate") String birthDate,
            @RequestParam(name = "serial") String serial) {

        log.info("request for verify-info received at {}", new Date());
        log.info("verify-info: nationalCode: {}, birthDate: {}, serial: {}", nationalCode, birthDate, serial);

        Inquiry inquiry = new Inquiry();
        InquirySoap soapCall = inquiry.getInquirySoap();

        log.info("sending request to SABT for base info at {}", new Date());

        NOCRPersonModel personModel;
        try {
            personModel = soapCall.inquiryFromNOCR(
                    nationalCode,
                    birthDate,
                    "14008367749",
                    "14008367749");
        } catch (Throwable throwable) {
            throw new SabtConnectionException();
        }

        if (personModel == null || personModel.getStatus() == -1) {
            log.error("SABT is not responding. returned statue code is {}", personModel == null ? "null" : personModel.getStatus());
            throw new SabtConnectionException();
        }

        if (personModel.getStatus() != 2 || !personModel.isAlive()) {
            log.error("incorrect base info. returned statue code is {}", personModel.getStatus());
            throw new IdentityMismatchException();
        }

        log.info("sending request to SABT for client picture at {}", new Date());

        NocrModel nocrModel;

        try {
            nocrModel = soapCall.nocrImageInquiryNew(
                    "14008367749",
                    "14008367749",
                    Long.parseLong(nationalCode),
                    Integer.parseInt(birthDate),
                    serial);
        } catch (Throwable throwable) {
            throw new SabtConnectionException();
        }

        if (nocrModel == null || nocrModel.getImage() == null) {
            log.error("SABT is not responding. returned statue code is {}", nocrModel == null ? "null" : nocrModel.getStatus());
            throw new IdentityMismatchException();
        }

        try {
            byte[] byteArrayImage = nocrModel.getImage();
            String encodedImage = Base64.getEncoder().encodeToString(byteArrayImage);
            byte[] referenceImage = DatatypeConverter.parseBase64Binary(encodedImage);

            String fileName = "/home/sabt/" + nationalCode + ".jpg";
            File sabtImage = new File(fileName);
            FileUtils.writeByteArrayToFile(sabtImage, referenceImage);
        } catch (Throwable throwable) {
            log.error("Error in persisting the image");
            throw new SabtConnectionException();
        }

        profileService.addKycInfo(
                nationalCode,
                serial,
                birthDate,
                personModel.getFirstName(),
                personModel.getLastName());

        log.info("returning response to client");
    }

    @PostMapping("/verify-user")
    @NoContent
    @ApiIgnore
    public void verifyUser(
            @RequestParam(name = "nationalCode") String nationalCode,
            @RequestParam(name = "method") String method,
            @RequestParam("video") MultipartFile video) {

        log.info("request for verify-user received at {}", new Date());
        log.info("verify-user with nationalCode: {}", nationalCode);

        ResponseEntity<FaceVerificationResponse> fvResponseResponseEntity;

        try {

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("video", video.getResource());
            body.add("method", method);

            File sabtImage = new File("/home/sabt/" + nationalCode + ".jpg");
            FileSystemResource referenceResource = new FileSystemResource(sabtImage);
            body.add("reference", referenceResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            log.info("calling face verification engine for verifying the integrity of user at {}", new Date());

            fvResponseResponseEntity = restTemplate.postForEntity(
                    "http://192.168.12.2:5000/claim_check",
                    requestEntity,
                    FaceVerificationResponse.class);

        } catch (Throwable t) {
            throw new VideoProcessingException();
        }

        if (fvResponseResponseEntity.getStatusCode().is2xxSuccessful()) {
            FaceVerificationResponse faceVerificationResponse = fvResponseResponseEntity.getBody();

            if (faceVerificationResponse != null) {

                String status = faceVerificationResponse.getStatus();

                switch (status) {
                    case "0":
                        return;
                    case "1":
                        throw new InvalidVideoException();
                    case "3":
                        throw new FaceMismatchException();
                    case "4":
                        throw new FaceNotFoundException();
                    case "5":
                        throw new MultiFaceFoundException();
                    case "6":
                        throw new RealPersonNotFoundException();
                    case "7":
                        throw new VideoProcessingException();
                    case "8":
                        throw new CameraPositionException();
                }
            }
        }

        throw new VideoProcessingException();
    }

    @PostMapping("/verify-video")
    public VerifyVideoResponse verifyVideo(
            @RequestParam(name = "nationalCode") String nationalCode,
            @RequestParam(name = "method") String method,
            @RequestParam MultipartFile image,
            @RequestParam MultipartFile video) {

        log.info("request for verify-video received at {}", new Date());
        log.info("verify-video: nationalCode: {}", nationalCode);

        ResponseEntity<FaceVerificationResponse> fvResponseResponseEntity;

        try {

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("video", video.getResource());
            body.add("method", method);
            body.add("nationalCode", nationalCode);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            log.info("calling face verification engine at {}", new Date());

            fvResponseResponseEntity = restTemplate.postForEntity(
                    "http://192.168.12.2:5000/pose_check",
                    requestEntity,
                    FaceVerificationResponse.class);
        } catch (Throwable t) {
            throw new VideoProcessingException();
        }

        if (fvResponseResponseEntity.getStatusCode().is2xxSuccessful()) {
            FaceVerificationResponse faceVerificationResponse = fvResponseResponseEntity.getBody();

            if (faceVerificationResponse != null) {

                String status = faceVerificationResponse.getStatus();

                switch (status) {
                    case "0":
                        try {
                            String profileImageAddress = "/home/profile/" + nationalCode + ".jpg";
                            File profileImage = new File(profileImageAddress);
                            FileUtils.writeByteArrayToFile(profileImage, image.getBytes());
                            User user = profileService.completeKycInfo(profileImageAddress);
                            return new VerifyVideoResponse(
                                    Integer.parseInt(status),
                                    "احراز هویت شما با موفقیت انجام شد",
                                    user.getFirstName(),
                                    user.getLastName());
                        } catch (Throwable throwable) {
                            throw new VideoProcessingException();
                        }
                    case "1":
                        throw new InvalidVideoException();
                    case "3":
                        throw new FaceMismatchException();
                    case "4":
                        throw new FaceNotFoundException();
                    case "5":
                        throw new MultiFaceFoundException();
                    case "6":
                        throw new RealPersonNotFoundException();
                    case "7":
                        throw new VideoProcessingException();
                    case "8":
                        throw new CameraPositionException();
                }
            }
        }

        throw new VideoProcessingException();
    }

    @PostMapping("/spoof-check")
    @NoContent
    public void checkSpoof(
            @RequestParam(name = "nationalCode") String nationalCode,
            @RequestParam MultipartFile image01,
            @RequestParam MultipartFile image02,
            @RequestParam MultipartFile image03) {

        log.info("request for spoof check received at {}", new Date());
        log.info("spoof checking for nationalCode: {}", nationalCode);

        ResponseEntity<FaceVerificationResponse> scResponseResponseEntity;

        try {

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            File sabtImage = new File("/home/sabt/" + nationalCode + ".jpg");
            FileSystemResource referenceResource = new FileSystemResource(sabtImage);
            body.add("reference", referenceResource);

            body.add("image01", image01.getResource());
            body.add("image02", image02.getResource());
            body.add("image03", image03.getResource());

            body.add("nationalCode", nationalCode);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            log.info("calling spoof checking engine at {}", new Date());

            scResponseResponseEntity = restTemplate.postForEntity(
                    "http://192.168.12.2:5000/spoof_check",
                    requestEntity,
                    FaceVerificationResponse.class);

        } catch (Throwable t) {
            throw new ImageProcessingException();
        }

        if (scResponseResponseEntity.getStatusCode().is2xxSuccessful()) {
            FaceVerificationResponse faceVerificationResponse = scResponseResponseEntity.getBody();

            if (faceVerificationResponse != null) {

                String status = faceVerificationResponse.getStatus();

                switch (status) {
                    case "0":
                        return;
                    case "1":
                        throw new InvalidVideoException();
                    case "3":
                        throw new FaceMismatchException();
                    case "4":
                        throw new FaceNotFoundException();
                    case "5":
                        throw new MultiFaceFoundException();
                    case "6":
                        throw new RealPersonNotFoundException();
                    case "7":
                        throw new ImageProcessingException();
                    case "8":
                        throw new CameraPositionException();
                }
            }
        }

        throw new ImageProcessingException();

    }

    @GetMapping("/download-image")
    public ResponseEntity<Resource> downloadImage(@RequestParam(name = "nationalCode") String nationalCode) {

        MediaType mediaType = MediaType.IMAGE_JPEG;

        File file = new File("/home/sabt/" + nationalCode + ".jpg");
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(mediaType)
                .body(resource);
    }

}
