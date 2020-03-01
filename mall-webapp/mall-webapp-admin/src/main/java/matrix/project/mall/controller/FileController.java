package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.module.common.helper.files.DownloadHelper;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangcheng
 * @date 2019/6/3
 */
@RestController
@RequestMapping("/api/file")
@NotNeedUserVerify
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private DownloadHelper downloadHelper;

    @Value("${upload.preview-image-url}")
    private String previewImageUrl;

    @Value("${upload.download-url}")
    private String downloadUrl;

    @PostMapping("/upload")
    public Result upload(HttpServletRequest request) {
        return Result.success(fileService.uploadFile(request, downloadUrl));
    }

    @PostMapping("/uploadImage")
    public Result uploadImage(HttpServletRequest request) {
        return Result.success(fileService.uploadFile(request, previewImageUrl));
    }

    @GetMapping("/previewImage/{fileName}")
    public Result previewImage(@PathVariable String fileName, HttpServletResponse response) {
        fileService.previewImage(fileName, response);
        return Result.success(null);
    }

    @GetMapping("/download/{fileName}")
    public Result download(@PathVariable String fileName, HttpServletResponse response) {
        downloadHelper.download(response, fileName, fileName);
        return Result.success(true);
    }
}
