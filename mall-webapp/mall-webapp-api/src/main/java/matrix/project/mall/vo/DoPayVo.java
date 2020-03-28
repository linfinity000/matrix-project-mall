package matrix.project.mall.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import matrix.module.pay.enums.PayMode;
import matrix.project.mall.enums.PayChannel;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-28
 */
@Data
@Accessors(chain = true)
public class DoPayVo implements Serializable {

    private List<String> orderIds;

    //PC,H5,APP,QrCode,WE_JSAPI
    private String payMode = PayMode.QrCode.getCode();

    //0:支付宝,1:微信
    private Integer payChannel = PayChannel.ALI.getCode();
}
