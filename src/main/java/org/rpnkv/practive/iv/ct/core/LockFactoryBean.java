package org.rpnkv.practive.iv.ct.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockFactoryBean {

    @Bean
    public Object lock(){
        return new Object();
    }

}
