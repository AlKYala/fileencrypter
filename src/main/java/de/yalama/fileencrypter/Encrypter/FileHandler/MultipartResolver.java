package de.yalama.fileencrypter.Encrypter.FileHandler;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class MultipartResolver {

    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        //commonsMultipartResolver.setMaxUploadSize(500000000);
        return commonsMultipartResolver;
    }
}
