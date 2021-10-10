package com.vosouq.contract.utills;

import com.vosouq.commons.util.NumberUtil;
import com.vosouq.contract.exception.BadFileException;
import com.vosouq.contract.exception.BadFileFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Slf4j
public class FileUtil {

    public static String generateAddress(Address address, MultipartFile file) {
        if (file != null && !file.isEmpty() && file.getOriginalFilename() != null) {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null
                    || originalFileName.isEmpty()
                    || originalFileName.contains(".."))
                throw new BadFileException();

            String fileExtension;
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            } catch (Exception e) {
                throw new BadFileFormatException();
            }

            if (!(fileExtension.equals("jpeg")
                    || fileExtension.equals("jpg")
                    || fileExtension.equals("png")
                    || fileExtension.equals("pdf")))
                throw new BadFileFormatException();

            return address.getAddress() +
                    originalFileName.substring(0, originalFileName.lastIndexOf(".")) +
                    "_" +
                    NumberUtil.randomUnsignedInt(5) +
                    "." +
                    fileExtension;
        }
        throw new BadFileException();
    }

    public static void uploadFileHandler(String path, MultipartFile multipartFile) {

        if (!multipartFile.isEmpty()) {
            try {
                long fileSize = multipartFile.getSize();

                if (fileSize > 200000) {
                    resizeAndSave(path, multipartFile);
                } else {
                    save(path, multipartFile);
                }

            } catch (Throwable e) {
                log.error("error in saving multipartFile!", e);
                throw new BadFileException();
            }
        } else {
            log.error("multipartFile is empty!");
            throw new BadFileException();
        }
    }

    private static void save(String path, MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();

        File file = new File(path);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
        stream.write(bytes);
        stream.close();
    }

    private static void resizeAndSave(String path, MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        BufferedImage inputImage = ImageIO.read(inputStream);

        int desiredWidth = 750;
        int currentWith = inputImage.getWidth();

        double percent;

        if (currentWith > desiredWidth)
            percent = ((double) desiredWidth) / currentWith;
        else
            percent = ((double) currentWith) / desiredWidth;

        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);

        BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        String formatName = path.substring(path.lastIndexOf(".") + 1);

        ImageIO.write(outputImage, formatName, new File(path));

        try {
            inputStream.close();
        } catch (Throwable throwable) {
            log.error("input stream is not open!", throwable);
        }
    }

}
