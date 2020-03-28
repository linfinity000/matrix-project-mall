package matrix.project.mall.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
public interface FileService {

    void previewImage(String fileName, HttpServletResponse response);

}
