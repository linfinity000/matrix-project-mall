package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Address;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
public interface AddressService extends IService<Address> {

    Address queryById(String addressId);
}
