package pe.com.utp.service.user.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Primary;

@Configuration
public class FeignConfiguration {
    @Bean
    @Primary
    public Encoder feignFormEncoder(HttpMessageConverters messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(() -> messageConverters));
    }
}

