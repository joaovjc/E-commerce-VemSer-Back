package com.dbc.vemserback.ecommerce.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Component
public class FileRepository {
	
	private final String bucketString = "e-commerce-vemser.appspot.com";

	private final String keyPath = "src/main/resources/token/e-commerce-vemser-firebase-adminsdk-9xb5z-1660093775.json";
	
	private Storage getStorage(){
		Credentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
	}
	
	public String save(File file, String fileName, String content) throws IOException  {
		BlobId blobId = BlobId.of(bucketString, fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(content).build();
		Storage storage = this.getStorage();
		storage.create(blobInfo, Files.readAllBytes(file.toPath()));
		return String.format(fileName);
	}
	
	public Optional<Blob> findByFileName(String fileName){
		Storage storage = this.getStorage();
		return Optional.of(storage.get(BlobId.of(bucketString, fileName)));
	}

}
