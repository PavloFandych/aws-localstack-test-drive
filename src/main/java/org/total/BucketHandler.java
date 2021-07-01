package org.total;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

/**
 * @author Pavlo.Fandych
 */
public final class BucketHandler implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(final S3Event s3Event,
                                final Context context) {
        System.out.println("OK");
        return "Event!";
    }
}