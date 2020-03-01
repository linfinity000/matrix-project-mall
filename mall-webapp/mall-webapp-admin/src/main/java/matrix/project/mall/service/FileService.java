package matrix.project.mall.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wangcheng
 * @date 2019/6/3
 */
public interface FileService {

    List<String> uploadFile(HttpServletRequest request, String preLinkUrl);

    void previewImage(String fileName, HttpServletResponse response);

}
