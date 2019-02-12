package org.rpnkv.practive.iv.ct.persist;

import org.rpnkv.practive.iv.ct.core.DomainInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;


@Service
public class DomainInfoToFileWriter {

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

    void persist(DomainInfo domainInfo) {
        try {
            outputStream.write((domainInfo.getUrl() + "---------\n").getBytes());
            outputStream.write(domainInfo.getContents());
            outputStream.write("\n\n".getBytes());
        } catch (IOException e) {
            logger.error("Failed saving contents of " + domainInfo.getUrl(), e);
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