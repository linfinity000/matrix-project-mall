package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.annotation.NotNeedClientVerify;
import matrix.project.mall.annotation.NotNeedUserVerify;
import matrix.project.mall.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wangcheng
 * @date 2019/6/3
 */
@RestController
@RequestMapping("/api/file")
@NotNeedUserVerify
@NotNeedClientVerify
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/previewImage/{fileName}")
    public Result previewImage(@PathVariable String fileName, HttpServletResponse response) {
        fileService.previewImage(fileName, response);
        return Result.success(null);
    }
}
