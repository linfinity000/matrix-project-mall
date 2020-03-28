package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.entity.Address;
import matrix.project.mall.entity.Region;
import matrix.project.mall.mapper.AddressMapper;
import matrix.project.mall.service.AddressService;
import matrix.project.mall.service.RegionService;
import matrix.project.mall.utils.LoginUtil;
import matrix.project.mall.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangcheng
 * @date 2020-03-05
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Autowired
    private RegionService regionService;

    @Override
    public Address queryById(String addressId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ADDRESS_ID", addressId)
                .eq("USER_ID", LoginUtil.getUser().getUserId())
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }

    @Override
    @Transactional
    public boolean saveAddress(AddressVo addressVo) {
        Assert.state(addressVo.getProvinceCode() != null, "省份不能为空");
        Assert.state(addressVo.getCityCode() != null, "城市不能为空");
        Assert.state(addressVo.getAreaCode() != null, "地区不能为空");
        Assert.state(!StringUtils.isEmpty(addressVo.getMobile()), "手机号不能为空");
        Assert.state(!StringUtils.isEmpty(addressVo.getLinkName()), "联系人不能为空");
        Assert.state(!StringUtils.isEmpty(addressVo.getAddress()), "详细地址不能为空");
        List<Region> regionList = regionService.queryByCodes(Arrays.asList(addressVo.getProvinceCode(), addressVo.getCityCode(), addressVo.getAreaCode()));
        Map<Long, Region> regionMap = regionList.stream().collect(Collectors.toMap(Region::getCode, item -> item, (o1, o2) -> o2));
        Date date = new Date();
        Address address = new Address()
                .setAddressId(RandomUtil.getUUID())
                .setUserId(LoginUtil.getUser().getUserId())
                .setAddress(addressVo.getAddress())
                .setLinkName(addressVo.getLinkName())
                .setMobile(addressVo.getMobile())
                .setIsDefault(addressVo.getIsDefault())
                .setProvinceCode(addressVo.getProvinceCode())
                .setProvinceName(regionMap.get(addressVo.getProvinceCode()).getName())
                .setCityCode(addressVo.getCityCode())
                .setCityName(regionMap.get(addressVo.getCityCode()).getName())
                .setAreaCode(addressVo.getAreaCode())
                .setAreaName(regionMap.get(addressVo.getAreaCode()).getName())
                .setCreateTime(date)
                .setUpdateTime(date)
                .setStatus(Constant.ENABLED);
        if (addressVo.getIsDefault().equals(Constant.IS_DEFAULT)) {
            getBaseMapper().updateNoDefault(LoginUtil.getUser().getUserId());
        }
        save(address);
        return true;
    }

    @Override
    public List<Address> listAddress() {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_ID", LoginUtil.getUser().getUserId())
                .eq("STATUS", Constant.ENABLED)
                .orderByDesc("IS_DEFAULT", "UPDATE_TIME");
        return list(queryWrapper);
    }

    @Override
    public boolean setDefault(String addressId) {
        Address address = queryById(addressId);
        getBaseMapper().updateNoDefault(LoginUtil.getUser().getUserId());
        address.setIsDefault(Constant.IS_DEFAULT)
                .setUpdateTime(new Date());
        updateById(address);
        return true;
    }

    @Override
    public boolean removeDefault(String addressId) {
        Address address = queryById(addressId);
        address.setStatus(Constant.DELETED)
                .setUpdateTime(new Date());
        updateById(address);
        return true;
    }

    @Override
    public boolean removeByAddressId(String addressId) {
        Address address = queryById(addressId);
        Assert.state(address != null, "地址不存在");
        assert address != null;
        address.setStatus(Constant.DISABLED);
        updateById(address);
        return true;
    }
}
