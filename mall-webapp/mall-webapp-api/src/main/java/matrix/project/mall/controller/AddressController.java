package matrix.project.mall.controller;

import matrix.module.common.bean.Result;
import matrix.project.mall.service.AddressService;
import matrix.project.mall.service.RegionService;
import matrix.project.mall.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangcheng
 * @date 2020-03-06
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private RegionService regionService;

    @GetMapping("/listRegion")
    public Result listRegion(@RequestParam Long parentCode) {
        return Result.success(regionService.listRegion(parentCode));
    }

    @GetMapping("/listAddress")
    public Result listAddress() {
        return Result.success(addressService.listAddress());
    }

    @GetMapping("/setDefault")
    public Result setDefault(@RequestParam String addressId) {
        return Result.success(addressService.setDefault(addressId));
    }

    @GetMapping("/removeDefault")
    public Result removeDefault(@RequestParam String addressId) {
        return Result.success(addressService.removeDefault(addressId));
    }

    @PostMapping("/saveAddress")
    public Result saveAddress(@RequestBody AddressVo addressVo) {
        return Result.success(addressService.saveAddress(addressVo));
    }

    @GetMapping("/detail")
    public Result detailAddress(@RequestParam("addressId") String addressId) {
        return Result.success(addressService.queryById(addressId));
    }

    @GetMapping("/deleteAddress")
    public Result deleteAddress(@RequestParam String addressId) {
        return Result.success(addressService.removeByAddressId(addressId));
    }

}
