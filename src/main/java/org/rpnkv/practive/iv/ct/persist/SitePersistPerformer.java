package org.rpnkv.practive.iv.ct.persist;

import org.rpnkv.practive.iv.ct.core.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;


@Service
@PropertySource("classpath:application.properties")
public class SitePersistPerformer {

    private static final Logger logger = LoggerFactory.getLogger(FileWriter.class);

    @Value("${output.file}")
    private String outputPath;

    @Value("${output.buffer.size}")
    private int bufferSize = 2048;

    private OutputStream outputStream;

    @PostConstruct
    public void init() throws FileNotFoundException {
        outputStream = new BufferedOutputStream(new FileOutputStream(outputPath), bufferSize);
    }

    void persist(Site site) {
        try {
            outputStream.write((site.getUrl() + "---------\n").getBytes());
            outputStream.write(site.getContents());
             outputStream.write("\n\n".getBytes());
        } catch (IOException e) {
            logger.error("Failed saving contents of " + site. getUrl(), e);
        }
    }

    void close() {
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}