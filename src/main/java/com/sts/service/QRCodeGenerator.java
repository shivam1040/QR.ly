package com.sts.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeGenerator {
	
	public byte[] getQRImage(String url, int width, int height) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter=new QRCodeWriter();
		BitMatrix bitMatrix=qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
		return byteArrayOutputStream.toByteArray();
	}
}
