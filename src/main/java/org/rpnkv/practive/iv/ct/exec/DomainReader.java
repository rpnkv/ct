package org.rpnkv.practive.iv.ct.exec;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@PropertySource("classpath:application.properties")
public class DomainReader {

    private static final Logger logger = LoggerFactory.getLogger(DomainReader.class);

    @Value("${file.input}")
    private String inputPath;


    public Stream<String> getDomains() {
        throw new NotImplementedException("");
    }
}
