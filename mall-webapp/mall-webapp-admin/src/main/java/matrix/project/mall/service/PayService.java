package matrix.project.mall.service;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
public interface PayService {

    void processOrderSuccess(List<String> orderIds);

}
