package org.rpnkv.practive.iv.ct.fetch;

import org.rpnkv.practive.iv.ct.DomainInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Tries to fill {@link DomainInfo} with first {@link #contentsLength} bytes of contents, fetched from the Internet.
 * If attempt fails - writes error cause instead of contents.
 */
@Service
public class DomainInfoFetcher implements Consumer<DomainInfo> {

    private static final Logger logger = LoggerFactory.getLogger(DomainInfoFetcher.class);

    @Value("${contents.fetch.length}")
    private int contentsLength;

    @Value("${request.connection.timeout}")
    private int connectionTimeout;

    @Value("${request.read.timeout}")
    private int readTimeout;

    @Override
    public void accept(DomainInfo domainInfo) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(domainInfo.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);

            InputStream is = connection.getInputStream();
            domainInfo.setContents(readResponse(is));
        } catch (Exception e) {
            logger.info("Failed fetching from {} - {}", domainInfo.getUrl(), e.getMessage());
            domainInfo.setContents(resolveContentsForFailedRequest(e));
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
            logger.info("Failed reading contents from {}, {}", e.getMessage());
            return resolveContentsForFailedRequest(e);
        }
    }

    private byte[] resolveContentsForFailedRequest(Exception e){
        if(e.getMessage() == null || e.getMessage().isEmpty()){
            return "Unknown fetch error".getBytes();
        }else {
            return e.getMessage().getBytes();
        }
    }
}
