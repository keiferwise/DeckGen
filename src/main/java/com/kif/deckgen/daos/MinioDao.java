package com.kif.deckgen.daos;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
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
	
	public int saveImage( byte[] image ) {
		
	      MinioClient minioClient =  getClient();
	      
	      //minioClient.
		
		return 0;
	}
	
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
