package org.total;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Pavlo.Fandych
 */
public class TestDrive {

    public static final AmazonS3 CLIENT_S3 = prepareClientS3();

    public static void main(String[] args) {
        for (final Bucket bucket : getAllBuckets()) {
            final String bucketName = bucket.getName();
            for (S3ObjectSummary os : getObjectSummariesByBucketName(bucketName)) {
                System.out.println(bucketName + " -> " + os.getKey());
            }
        }
    }

    private static AmazonS3 prepareClientS3() {
        final BasicAWSCredentials credentials = new BasicAWSCredentials("temp", "temp");
        final AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder
                .EndpointConfiguration("http://localhost:4566", "us-east-1");
        final AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
        builder.withEndpointConfiguration(config);
        builder.withPathStyleAccessEnabled(true);
        builder.withCredentials(new AWSStaticCredentialsProvider(credentials));
        return builder.build();
    }

    private static void createBucket(final String bucketName) {
        if (CLIENT_S3.doesBucketExistV2(bucketName)) {
            System.out.println("Bucket name is not available. Try again with a different Bucket name.");
            return;
        }
        CLIENT_S3.createBucket(bucketName);
    }

    private static void deleteBucket(final String bucketName) {
        CLIENT_S3.deleteBucket(bucketName);
    }

    private static List<Bucket> getAllBuckets() {
        return CLIENT_S3.listBuckets();
    }

    private static List<S3ObjectSummary> getObjectSummariesByBucketName(final String bucketName) {
        return CLIENT_S3.listObjects(bucketName).getObjectSummaries();
    }

    private static void uploadObject(final String bucketName,
                                     final String key) {
        //fullPath == key
        CLIENT_S3.putObject(bucketName, key, new File(key));
    }

    private static void downloadObject(final String bucketName,
                                       final String key) {
        final S3Object s3object = CLIENT_S3.getObject(bucketName, key);
        final S3ObjectInputStream inputStream = s3object.getObjectContent();
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File("src/main/resources/test-file-destination.txt"));
        } catch (IOException e) {
            /*NOP*/
        }
    }

    private static void deleteObject(final String bucketName,
                                     final String key) {
        CLIENT_S3.deleteObject(bucketName, key);
    }

    private static void copyObject(final String sourceBucketName,
                                   final String sourceKey,
                                   final String destinationBucketName,
                                   final String destinationKey) {
        CLIENT_S3.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
    }

    private static void deleteMultipleObjects(final String bucketName,
                                              final String[] keysArray) {
        DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucketName)
                .withKeys(keysArray);
        CLIENT_S3.deleteObjects(delObjReq);
    }
}

