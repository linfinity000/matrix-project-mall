package matrix.project.mall.converter;

import matrix.project.mall.dto.SkuDto;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author wangcheng
 * @date 2019/6/8
 */
public class SkuLabelConvert {

    public static List<SkuDto> convert(List<SkuDto.SkuLabelDto> allLabel) {
        if (!CollectionUtils.isEmpty(allLabel)) {
            List<SkuDto> result = new ArrayList<>();
            allLabel.forEach(new Consumer<SkuDto.SkuLabelDto>() {
                String labelId = null;

                @Override
                public void accept(SkuDto.SkuLabelDto skuLabelDto) {
                    if (!skuLabelDto.getLabelId().equals(labelId)) {
                        labelId = skuLabelDto.getLabelId();
                        result.add(new SkuDto()
                                .setLabelId(skuLabelDto.getLabelId())
                                .setLabelName(skuLabelDto.getLabelName())
                                .setSkuValues(new ArrayList<>()));
                    }
                    if (!StringUtils.isEmpty(skuLabelDto.getSkuValue())) {
                        result.get(result.size() - 1).getSkuValues().add(skuLabelDto.getSkuValue());
                    }
                }
            });
            return result;
        }
        return null;
    }
}
