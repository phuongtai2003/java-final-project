package com.example.finalproject.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BarcodeService {
    public File generateBarcodeFile(String barcodeText, String filePath) throws WriterException, IOException, IOException, WriterException {
        Code128Writer barcodeWriter = new Code128Writer();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, 300, 150, hints);

        File barcodeFile = new File(filePath);
        FileOutputStream outputStream = new FileOutputStream(barcodeFile);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();

        return barcodeFile;
    }

}
