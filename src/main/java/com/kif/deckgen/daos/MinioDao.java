package com.kif.deckgen.daos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.kif.deckgen.models.Image;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;

@Service
public class MinioDao {

	public MinioDao() {
		// TODO Auto-generated constructor stub
	}
	
	public int saveImage( Image image ) {
		 
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
		
		  //UploadObjectArgs args = UploadObjectArgs.builder()
		  //       .bucket("deckgen").object("my-objectname").filename("person.json").build());
	      
	      //minioClient
	      MinioClient minioClient =  getClient();
	      PutObjectArgs args = PutObjectArgs.builder().object("myimg").bucket("deckgen").contentType("image/png").stream(img, 0, 0).build();

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
	/*
	public boolean saveImage() {
		
		MinioClient minioClient = getClient();
		
		return true;
	}
	*/
	public boolean testBucket() {
	      MinioClient minioClient =  getClient();
	      BucketExistsArgs.builder().bucket("deckgen").build();
	      
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
