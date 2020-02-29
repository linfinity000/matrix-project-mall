package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.utils.TreeUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.MenuListDto;
import matrix.project.mall.entity.AdminUser;
import matrix.project.mall.entity.MenuList;
import matrix.project.mall.enums.GrantEnum;
import matrix.project.mall.mapper.MenuListMapper;
import matrix.project.mall.service.MenuListService;
import matrix.project.mall.utils.ListUtil;
import matrix.project.mall.utils.LoginUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
@Service
public class MenuListServiceImpl extends ServiceImpl<MenuListMapper, MenuList> implements MenuListService {

    @Override
    public List<MenuListDto> queryTree() {
        AdminUser adminUser = LoginUtil.getAdminUser();
        QueryWrapper<MenuList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED);
        if (!StringUtils.isEmpty(adminUser.getShopId())) {
            queryWrapper.eq("TYPE", GrantEnum.Operation.name());
        }
        List<MenuList> menuLists = list(queryWrapper);
        if (CollectionUtils.isEmpty(menuLists)) {
            return new ArrayList<>();
        }
        List<MenuListDto> result = ListUtil.copyList(menuLists, MenuListDto.class);
        TreeUtil.toTree(result, new TreeUtil.Comparator<MenuListDto>() {
            @Override
            public boolean isParentWithChild(MenuListDto pre, MenuListDto after) {
                if (pre.getMenuId().equals(after.getParentId())) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean isTop(MenuListDto menuListDto) {
                String parentId = menuListDto.getParentId();
                if (!StringUtils.isEmpty(parentId) && parentId.equals("0")) {
                    return true;
                }
                return false;
            }
        });
        return result;
    }

}
