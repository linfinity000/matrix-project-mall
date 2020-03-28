package matrix.project.mall.service.impl;

import matrix.module.common.exception.ServiceException;
import matrix.module.common.helper.Assert;
import matrix.module.common.helper.files.ImageHelper;
import matrix.project.mall.service.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LogManager.getLogger(FileServiceImpl.class);

    @Autowired
    private ImageHelper imageHelper;

    @Value("${upload.file-path}")
    private String filePath;

    @Override
    public void previewImage(String fileName, HttpServletResponse response) {
        Assert.isNotNull(fileName, "fileName");
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            logger.warn("previewImage: not found ---" + file.getAbsolutePath());
            return;
        }
        BufferedImage bufferedImage = imageHelper.getBufferedImage(fileName);
        try {
            ImageIO.write(bufferedImage, "png", response.getOutputStream());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
