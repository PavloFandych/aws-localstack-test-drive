package org.total;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Pavlo.Fandych
 */
public class BucketHandler implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(final S3Event s3Event,
                                final Context context) {
        final String bucket = s3Event.getRecords().get(0).getS3().getBucket().getName();
        final String key = s3Event.getRecords().get(0).getS3().getObject().getKey();
        final S3Object obj = TestDrive.CLIENT_S3.getObject(new GetObjectRequest(bucket, key));
        try (final InputStream stream = obj.getObjectContent()) {
            final String content = IOUtils.toString(stream, StandardCharsets.UTF_8.name());
            System.out.println(content);
        } catch (IOException ioe) {
            /*NOP*/
        }
        return obj.getObjectMetadata().getContentType();
    }
}