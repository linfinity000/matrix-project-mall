package matrix.project.mall.service;

import matrix.project.mall.vo.DoPayVo;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
public interface PayService {

    String getPayUrl(DoPayVo payVo);

}
