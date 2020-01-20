package org.princelle.lp1project.Utils;

import io.minio.MinioClient;
import org.princelle.lp1project.Entities.Task;
import org.springframework.web.multipart.MultipartFile;

public class StorageManager {
	public static String upload(MultipartFile file, Task task) throws Exception {
		try {
			// Create a minioClient with the MinIO Server name, Port, Access key and Secret key.
			String endpoint = System.getenv("S3_ENDPOINT");
			String accessKey = System.getenv("ACCESS_KEY");
			String secretKey = System.getenv("SECRET_KEY");

			MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey);

			String bucketName = "lp1Project";

			// Check if the bucket already exists.
			boolean isExist = minioClient.bucketExists(bucketName);
			if(isExist) {
				System.out.println("Bucket already exists.");
			} else {
				minioClient.makeBucket(bucketName);
			}

			// Upload the zip file to the bucket with putObject
			String filename = task.getId() + task.getFinishDate().toString() + file.getOriginalFilename();

			minioClient.putObject(bucketName, filename, String.valueOf(file.getBytes()), null, null, null, "application/octet-stream");

			String url = minioClient.presignedGetObject(bucketName, filename);

			return url;
		} catch(Exception e) {
			System.out.println("Error occurred: " + e);
			throw e;
		}
	}
}
