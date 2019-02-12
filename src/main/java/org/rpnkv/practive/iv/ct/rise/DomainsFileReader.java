package org.rpnkv.practive.iv.ct.rise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
@PropertySource("classpath:application.properties")
class DomainsFileReader {

    private static final Logger logger = LoggerFactory.getLogger(DomainsFileReader.class);

    @Value("${file.input}")
    private String inputPath;

    Stream<String> getDomains() {
        logger.info("Reading domain list from {}", inputPath);
        try {
            return Files.lines(Paths.get(inputPath))
                    .map(this::applyProtocol);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String applyProtocol(String s) {
        return "http://".concat(s);
    }
}
