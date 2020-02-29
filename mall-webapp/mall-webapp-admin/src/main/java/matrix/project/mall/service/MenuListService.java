package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.MenuListDto;
import matrix.project.mall.entity.MenuList;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
public interface MenuListService extends IService<MenuList> {

    List<MenuListDto> queryTree();

}
