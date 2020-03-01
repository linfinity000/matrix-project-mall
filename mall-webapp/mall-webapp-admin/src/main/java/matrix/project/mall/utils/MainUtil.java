package matrix.project.mall.utils;

import matrix.module.common.helper.files.DownloadHelper;
import matrix.module.common.helper.files.ImageHelper;
import matrix.module.common.helper.files.UploadHelper;
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

    @Value("${upload.file-max-size}")
    private Long fileMaxSize;

    @Value("${upload.suffix-filter}")
    private String suffixFilter;

    @Bean
    public UploadHelper uploadHelper() {
        return UploadHelper.getInstance(filePath, fileMaxSize, suffixFilter);
    }

    @Bean
    public ImageHelper imageHelper() {
        return ImageHelper.getInstance(filePath);
    }

    @Bean
    public DownloadHelper downloadHelper() {
        return DownloadHelper.getInstance(filePath);
    }
}
