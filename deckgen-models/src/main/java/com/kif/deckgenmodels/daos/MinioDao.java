package com.kif.deckgenmodels.daos;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.kif.deckgenmodels.Image;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;

@Service
public class MinioDao {

	public MinioDao() {
		// TODO Auto-generated constructor stub
	}
	
	public int saveImage (BufferedImage image, String cardId) {
		
		
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
			ImageIO.write(image, "png", os);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        
		
		  //UploadObjectArgs args = UploadObjectArgs.builder()
		  //       .bucket("deckgen").object("my-objectname").filename("person.json").build());
	      
	      //minioClient
	      MinioClient minioClient =  getClient();
	      PutObjectArgs args = PutObjectArgs.builder().object(cardId+".png").bucket("deckgen").contentType("image/png").stream(is, -1,1024 * 1024 * 16).build();

	      try {
			minioClient.putObject(args);
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      //minioClient.
		
		return 0;

	}
	
	public int saveImage( Image image,String cardId ) {
		 
		//Retrieve the image from the URL and put it into a bufferedimage
		BufferedImage img = null;
        URL url=null;
		try {
			url = new URL(image.getUrl());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //Convert the buffered image to a input stream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
			ImageIO.write(img, "png", os);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        
		
		  //UploadObjectArgs args = UploadObjectArgs.builder()
		  //       .bucket("deckgen").object("my-objectname").filename("person.json").build());
	      
	      //minioClient
	      MinioClient minioClient =  getClient();
	      PutObjectArgs args = PutObjectArgs.builder().object(cardId+".png").bucket("deckgen").contentType("image/png").stream(is, -1,1024 * 1024 * 16).build();

	      try {
			minioClient.putObject(args);
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      //minioClient.
		
		return 0;
	}
	
	public String getImage(String cardId) {
		
		MinioClient minioClient = getClient();
		
		String objectName = cardId + ".png";
		
		GetPresignedObjectUrlArgs args = new GetPresignedObjectUrlArgs.Builder().method(Method.GET).bucket("deckgen").object(objectName).build();	
		String url = "";
		
		try {
			url = minioClient.getPresignedObjectUrl(args);
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}
	
	public boolean testBucket() {
	      MinioClient minioClient =  getClient();
	      //BucketExistsArgs.builder().bucket("deckgen").build();
	      
	      boolean isExist=false;
		try {
			isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket("deckgen").build());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

	     
	      if (isExist) {
	          System.out.println("Bucket already exists.");
	        } else {
	          // Create a new bucket
	          try {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket("deckgen").build());
			} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
					| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
					| IllegalArgumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          System.out.println("Bucket created successfully.");
	        }
	      

	      
		return false;
		
	}
	
	private MinioClient getClient() {
		
		return MinioClient.builder().credentials("minio99", "minio123").endpoint("http://localhost:9000/").build();
	}

}
