package com.rab3tech.customer.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class CreditCardService  implements CreditCardInterface{
	@Override
	public  byte[] generatedCreditCard(String cardNumber,String exp,String name) {
		
		byte[] photo = new byte[]{};
		Resource resource = new ClassPathResource("/images/credit-card-front-template.jpg");
		try {
			InputStream resourceInputStream = resource.getInputStream();

			Image src = ImageIO.read(resourceInputStream);

			int wideth = src.getWidth(null);
			int height = src.getHeight(null);

			BufferedImage tag = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = tag.createGraphics();

			g.setBackground(new Color(200, 250, 200));
			g.clearRect(0, 0, wideth, height);
			g.setColor(Color.WHITE);
			g.drawImage(src, 0, 0, wideth, height, null);

			// credit card number
			g.setFont(new Font("Lucida Console", Font.BOLD, 36));
			g.drawString(cardNumber.substring(0, 4), 40, 207);
			g.drawString(cardNumber.substring(4, 8), 150, 207);
			g.drawString(cardNumber.substring(8, 12), 260, 207);
			g.drawString(cardNumber.substring(12, 16), 370, 207);

			// exp date
			g.setFont(new Font("Lucida Console", Font.PLAIN, 24));
			g.drawString(exp, 65, 250);

			// customer name
			g.setFont(new Font("Tahoma", Font.PLAIN, 28));
			g.drawString(name.toUpperCase(), 30, 290);

			//cardType
			g.setFont(new Font("Lucida Console",Font.ITALIC,20));
			//g.drawString("VISA", 120, 20);

			//load new image
			Resource simage = new ClassPathResource("/images/bank-icon.png");
			InputStream simageInputStream = simage.getInputStream();
			Image img = ImageIO.read(simageInputStream);
			//Draw image on given card
			g.drawImage(img, 304, 255, 91, 45, null);

			g.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(tag, "jpg", baos);
			baos.flush();
			photo= baos.toByteArray();
			System.out.println(photo);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return photo;
	}
	//***************************************//
	@Override
	public  byte[] generatedBackCreditCard(String cvv) {
		
		
		
		byte[] photo = new byte[]{};

		Resource resource = new ClassPathResource("/images/credit-card-back-template.jpg");

		try {
			InputStream resourceInputStream = resource.getInputStream();

			Image src = ImageIO.read(resourceInputStream);

			int wideth = src.getWidth(null);
			int height = src.getHeight(null);

			BufferedImage tag = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = tag.createGraphics();

			g.setBackground(new Color(200, 250, 200));
			g.clearRect(0, 0, wideth, height);
			g.setColor(Color.BLACK);
			g.drawImage(src, 0, 0, wideth, height, null);
			
			g.setFont(new Font("Lucida Console", Font.PLAIN, 24));
			g.drawString(cvv, 360, 135);


			
			g.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(tag, "jpg", baos);
			baos.flush();
			photo= baos.toByteArray();
			System.out.println(photo);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return photo;
	}
	//***************************************//
@Override
	public  String generateCreditCardNumber() {
		Random random = new Random();
		StringBuilder number = new StringBuilder();
		number.append(String.format("%04d", random.nextInt(10000)));
		number.append(String.format("%04d", random.nextInt(10000)));
		number.append(String.format("%04d", random.nextInt(10000)));
		number.append(String.format("%04d", random.nextInt(10000)));
		return number.toString();
	}
@Override
	public String generateCreditCardExpireDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		LocalDate date = LocalDate.now();
		date = date.plusYears(3);
		return formatter.format(date);
	}
@Override
	public String generateCCVNumber() {
		Random random = new Random();
		StringBuilder number = new StringBuilder();
		number.append(String.format("%03d", random.nextInt(1000)));
		return number.toString();
	}
@Override
	public void saveCreditInfo() {
	
	}
}
