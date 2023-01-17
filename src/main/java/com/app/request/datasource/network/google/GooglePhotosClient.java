package com.app.request.datasource.network.google;

import com.app.request.entity.GoogleImageUploadToken;
import feign.Headers;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

class CoreFeignConfiguration {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    @Primary
    @Scope(SCOPE_PROTOTYPE)
    Encoder feignFormEncoder() {
        return new FormEncoder(new SpringEncoder(this.messageConverters));
    }
}

@FeignClient(url = "https://www.googleapis.com/", name = "googleImageUpload", configuration = CoreFeignConfiguration.class)
public interface GooglePhotosClient {

    @PostMapping(value = "oauth2/v4/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    GoogleImageUploadToken getRefreshToken(
            Map<String,String> formParams
    );

}
