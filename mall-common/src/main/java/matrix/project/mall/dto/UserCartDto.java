package matrix.project.mall.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-07
 */
@Data
@Accessors(chain = true)
public class UserCartDto implements Serializable {

    private String shopId;

    private String shopName;

    private List<UserCartItemDto> cartItems;

    @Data
    @Accessors(chain = true)
    public static class UserCartItemDto implements Serializable {

        private String cartId;

        private String shopId;

        private String shopName;

        private String goodsName;

        private String imageUrl;

        private Integer goodsCount;

        private BigDecimal price;
    }
}
