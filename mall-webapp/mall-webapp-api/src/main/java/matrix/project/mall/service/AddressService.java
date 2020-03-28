package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.entity.Address;
import matrix.project.mall.vo.AddressVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
public interface AddressService extends IService<Address> {

    Address queryById(String addressId);

    boolean saveAddress(AddressVo addressVo);

    List<Address> listAddress();

    boolean setDefault(String addressId);

    boolean removeDefault(String addressId);

    boolean removeByAddressId(String addressId);
}
