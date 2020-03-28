package matrix.project.mall.utils;

import matrix.module.common.helper.files.ImageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangcheng
 * @date 2020-03-01
 */
@Configuration
public class MainUtil {

    @Value("${upload.file-path}")
    private String filePath;

    @Bean
    public ImageHelper imageHelper() {
        return ImageHelper.getInstance(filePath);
    }
}
