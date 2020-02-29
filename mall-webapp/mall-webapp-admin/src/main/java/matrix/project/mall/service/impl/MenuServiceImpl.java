package matrix.project.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import matrix.module.common.helper.Assert;
import matrix.module.common.utils.RandomUtil;
import matrix.module.common.utils.TreeUtil;
import matrix.project.mall.constants.Constant;
import matrix.project.mall.dto.MenuDto;
import matrix.project.mall.entity.AdminUser;
import matrix.project.mall.entity.Menu;
import matrix.project.mall.enums.GrantEnum;
import matrix.project.mall.mapper.MenuMapper;
import matrix.project.mall.service.MenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<MenuDto> queryTree() {
        AdminUser adminUser = LoginUtil.getAdminUser();
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("STATUS", Constant.ENABLED);
        if (!StringUtils.isEmpty(adminUser.getShopId())) {
            queryWrapper.eq("TYPE", GrantEnum.Operation.name());
        }
        queryWrapper.orderByAsc("ORDER_BY", "CREATE_TIME");
        List<Menu> menus = list(queryWrapper);
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<>();
        }
        List<MenuDto> result = ListUtil.copyList(menus, MenuDto.class);
        TreeUtil.toTree(result, new TreeUtil.Comparator<MenuDto>() {
            @Override
            public boolean isParentWithChild(MenuDto pre, MenuDto after) {
                if (pre.getMenuId().equals(after.getParentId())) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean isTop(MenuDto menuListDto) {
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
        Menu menu = null;
        if (!StringUtils.isEmpty(menuVo.getMenuId())) {
            menu = queryById(menuVo.getMenuId());
            Assert.state(menu != null, "菜单未找到");
        } else {
            menu = new Menu()
                    .setMenuId(StringUtils.isEmpty(menuVo.getMenuId()) ? RandomUtil.getUUID() : menuVo.getMenuId())
                    .setParentId(StringUtils.isEmpty(menuVo.getParentId()) ? "0" : menuVo.getParentId())
                    .setIsDefault(0)
                    .setStatus(Constant.ENABLED)
                    .setCreateTime(new Date());
        }
        assert menu != null;
        if (menu.getIsDefault().equals(0)) {
            menu.setMenuName(menuVo.getMenuName())
                    .setUrl(menuVo.getUrl());
        }
        menu.setOrderBy(menuVo.getOrderBy())
                .setUpdateTime(new Date());
        if (!StringUtils.isEmpty(menuVo.getMenuId())) {
            updateById(menu);
        } else {
            save(menu);
        }
        return true;
    }

    @Override
    public boolean removeTree(String menuId) {
        Menu menu = queryById(menuId);
        Assert.state(menu != null, "未找到菜单");
        assert menu != null;
        Assert.state(menu.getIsDefault().equals(0), "默认菜单不允许删除");
        menu.setStatus(Constant.DELETED);
        updateById(menu);
        return true;
    }

    @Override
    public Menu queryById(String menuId) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MENU_ID", menuId)
                .eq("STATUS", Constant.ENABLED);
        return getOne(queryWrapper, false);
    }

}
