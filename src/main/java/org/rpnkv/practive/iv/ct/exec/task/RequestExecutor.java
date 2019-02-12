package org.rpnkv.practive.iv.ct.exec.task;

import org.rpnkv.practive.iv.ct.core.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

@Service
public class RequestExecutor implements Consumer<Site> {

    private static final Logger logger = LoggerFactory.getLogger(RequestExecutor.class);

    @Value("${contents.fetch.length}")
    private int contentsLength;

    @Value("${request.connection.timeout}")
    private int connectionTimeout;

    @Value("${request.read.timeout}")
    private int readTimeout;

    @Override
    public void accept(Site site) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(site.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);

            //Get Response
            InputStream is = connection.getInputStream();
            site.setContents(readResponse(is));
        } catch (Exception e) {
            logger.warn("Failed fetching from {} - {}", site.getUrl(), e.getMessage());
            site.setContents(resolveContentsForFailedRequest(e));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private byte[] readResponse(InputStream is) {
        try {
            byte[] contents = new byte[is.available() > contentsLength ? contentsLength : is.available()];
            is.read(contents);
            is.close();
            return contents;
        } catch (IOException e) {
            logger.warn("Failed reading contents from {}, {}", e.getMessage());
            return resolveContentsForFailedRequest(e);
        }
    }

    private byte[] resolveContentsForFailedRequest(Exception e){
        return e.getMessage() != null ? e.getMessage().getBytes() : "Failure".getBytes();
    }
}
