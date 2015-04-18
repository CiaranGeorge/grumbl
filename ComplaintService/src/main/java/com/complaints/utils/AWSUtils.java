package com.complaints.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

public class AWSUtils {
	
	private static Logger LOG = Logger.getLogger(AWSUtils.class);
	
	private static final String BUCKET_NAME = "###";
	private static final String ACCESS_KEY = "###";
	private static final String SECRET_KEY = "###";

	/**
     * @author c.george
     * 
     * Upload an image to Amazon web services basing the name on
     * the username and the complaint ID.
     */
    public static void uploadImage(String username, Integer id, String imageData) 
    {
    	LOG.info("uploadImage from - "+username);
    	//need to change file name based on something.
        String fileName = username+"_"+id.toString()+".jpg";
        byte[] contents = Base64.decodeBase64(imageData.getBytes());
        uploadToAWS(contents, fileName);
    }
    
    /**
     * @author c.george
     * 
     * Send image data to Amazon Web Services.
     */
    private static void uploadToAWS(byte[] contents, String fileName) {
    	LOG.info("uploadToAWS");
    	AmazonS3 client = new AmazonS3Client(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
        InputStream stream = new ByteArrayInputStream(contents);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(contents.length);
        meta.setContentType("image/jpg");
        client.putObject(BUCKET_NAME, fileName, stream, meta);
        client.setObjectAcl(BUCKET_NAME, fileName, CannedAccessControlList.PublicRead);
    }
    
    /**
     * @author c.george
     * 
     * Download images from AWS based on the username and complaint ID.
     */
    public static String getImageFromAWS(String username, String id) throws IOException 
    {
    	LOG.info("getImageFromAWS for user: "+username+"_"+id);
    	AmazonS3 client = new AmazonS3Client(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
    	S3Object object = client.getObject(new GetObjectRequest(BUCKET_NAME, username+"_"+id+".jpg"));
    	byte[] byteArray = IOUtils.toByteArray(object.getObjectContent());
    	return Base64.encodeBase64String(byteArray);
    }
}
