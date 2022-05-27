package fertdt.alglearningv2.config;

import fertdt.alglearningv2.converter.CubeNameToCubeResponseConverter;
import fertdt.alglearningv2.converter.MethodNameToMethodResponseConverter;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    MethodNameToMethodResponseConverter methodNameToMethodResponseConverter;

    @Autowired
    CubeNameToCubeResponseConverter cubeNameToCubeResponseConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(methodNameToMethodResponseConverter);
        registry.addConverter(cubeNameToCubeResponseConverter);
    }
}
