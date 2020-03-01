package matrix.project.mall.service.impl;

import matrix.module.common.bean.UploadResult;
import matrix.module.common.exception.ServiceException;
import matrix.module.common.helper.Assert;
import matrix.module.common.helper.files.ImageHelper;
import matrix.module.common.helper.files.UploadHelper;
import matrix.project.mall.service.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangcheng
 * @date 2019/6/3
 */
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LogManager.getLogger(FileServiceImpl.class);

    @Autowired
    private UploadHelper uploadHelper;

    @Autowired
    private ImageHelper imageHelper;

    @Value("${upload.file-path}")
    private String filePath;

    @Override
    public List<String> uploadFile(HttpServletRequest request, String preLinkUrl) {
        List<String> result = new ArrayList<>();
        Map<String, UploadResult> map = uploadHelper.upload(request);
        if (map != null) {
            map.forEach((name, uploadResult) -> {
                if (!uploadResult.isSuccess()) {
                    throw new ServiceException(uploadResult.getMessage());
                }
                result.add(preLinkUrl + uploadResult.getSysFileName());
            });
        }
        return result;
    }

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
