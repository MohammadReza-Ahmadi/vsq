package com.vosouq.contract.utills;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class QrUtil {

    public static void generate(Long contractId) {

        try {
            Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix bitMatrix;

            String content = "CONTRACT_QR_CODE_" + contractId;
            bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 500, 500, hints);

            MatrixToImageConfig config = new MatrixToImageConfig(0xFF28323c, MatrixToImageConfig.WHITE);

            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

            File logoFile = new File("/var/vsq/others/vsq-logo.png");
            BufferedImage logoImage = ImageIO.read(logoFile);

            int deltaHeight = Math.abs(qrImage.getHeight() - logoImage.getHeight());
            int deltaWidth = Math.abs(qrImage.getWidth() - logoImage.getWidth());

            BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) combined.getGraphics();

            g.drawImage(qrImage, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            g.drawImage(logoImage, Math.round(deltaWidth / 2.0f), Math.round(deltaHeight / 2.0f), null);

            ImageIO.write(
                    combined,
                    "png",
                    new File("/var/vsq/others/" + ("contract-" + contractId + ".png")));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
