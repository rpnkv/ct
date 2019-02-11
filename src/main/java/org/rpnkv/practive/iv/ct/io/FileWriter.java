package org.rpnkv.practive.iv.ct.io;

import org.rpnkv.practive.iv.ct.get.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;

@Component
@PropertySource("classpath:application.properties")
public class FileWriter {

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

    public void writeSite(Site site) {
        try {
            outputStream.write((site.getUrl() + "---------\n").getBytes());
            outputStream.write(site.getContents());
        } catch (IOException e) {
            logger.error("Failed saving contents of " + site. getUrl(), e);
        }
    }
}
