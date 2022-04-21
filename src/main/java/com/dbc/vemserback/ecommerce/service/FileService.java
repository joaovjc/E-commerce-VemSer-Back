package com.dbc.vemserback.ecommerce.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FileService {

//	@Value("${bucket.name}")
	private String bucketString = "e-commerce-vemser.appspot.com";

	private final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + bucketString
			+ "/o/%s?alt=media";

//	@Value("${key.json.path}")
	private String keyPath = "src/main/resources/token/e-commerce-vemser-firebase-adminsdk-9xb5z-1660093775.json";

	private String uploadFile(File file, String fileName) throws IOException {
		BlobId blobId = BlobId.of(bucketString, fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

		Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));

		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		storage.create(blobInfo, Files.readAllBytes(file.toPath()));
//		return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
		return null;
	}

	private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
		File tempFile = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempFile;
	}

	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public String upload(MultipartFile multipartFile) throws BusinessRuleException {
		try {
			String fileName = multipartFile.getOriginalFilename(); // to get original file name
			fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName)); // to generated random string
																							// values for file name.

			File file = this.convertToFile(multipartFile, fileName); // to convert multipartFile to File
			String nome = this.uploadFile(file, fileName); // to get uploaded file link
			file.delete(); // to delete the copy of uploaded file stored in the project folder
			return nome;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessRuleException("faild to send File");
		}

	}

	public String download(String fileName) throws IOException {
		Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(keyPath));
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
		Blob blob = storage.get(BlobId.of(bucketString, fileName));
		byte[] fileContent = blob.getContent();
		return Base64.getEncoder().encodeToString(fileContent);
	}
}
