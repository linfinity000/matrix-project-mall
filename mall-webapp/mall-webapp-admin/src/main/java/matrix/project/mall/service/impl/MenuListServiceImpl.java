package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
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
import matrix.project.mall.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
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
        queryWrapper.orderByAsc("ORDER_BY", "CREATE_TIME");
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

    @Override
    public boolean saveTree(MenuVo menuVo) {
        Assert.state(!StringUtils.isEmpty(menuVo.getMenuName()), "菜单名不允许为空");
        MenuList menuList = null;
        if (!StringUtils.isEmpty(menuVo.getMenuId())) {
            menuList = queryById(menuVo.getMenuId());
            Assert.state(menuList != null, "菜单未找到");
        } else {
            menuList = new MenuList()
                    .setMenuId(StringUtils.isEmpty(menuVo.getMenuId()) ? RandomUtil.getUUID() : menuVo.getMenuId())
                    .setParentId(StringUtils.isEmpty(menuVo.getParentId()) ? "0" : menuVo.getParentId())
                    .setIsDefault(0)
                    .setStatus(Constant.ENABLED)
                    .setCreateTime(new Date());
        }
        assert menuList != null;
        if (menuList.getIsDefault().equals(0)) {
            menuList.setMenuName(menuVo.getMenuName())
                    .setUrl(menuVo.getUrl());
        }
        menuList.setOrderBy(menuVo.getOrderBy())
                .setUpdateTime(new Date());
        if (!StringUtils.isEmpty(menuVo.getMenuId())) {
            updateById(menuList);
        } else {
            save(menuList);
        }
        return true;
    }

    @Override
    public boolean removeTree(String menuId) {
        MenuList menuList = queryById(menuId);
        Assert.state(menuList != null, "未找到菜单");
        assert menuList != null;
        Assert.state(menuList.getIsDefault().equals(0), "默认菜单不允许删除");
        menuList.setStatus(Constant.DELETED);
        updateById(menuList);
        return true;
    }

    @Override
    public MenuList queryById(String menuId) {
        QueryWrapper<MenuList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MENU_ID", menuId)
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }

}
