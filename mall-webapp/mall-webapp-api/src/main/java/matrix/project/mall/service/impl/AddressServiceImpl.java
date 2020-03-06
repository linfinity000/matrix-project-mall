package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Address;
import matrix.project.mall.mapper.AddressMapper;
import matrix.project.mall.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Override
    public Address queryById(String addressId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ADDRESS_ID", addressId)
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }
}
