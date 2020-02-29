package matrix.project.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import matrix.project.mall.dto.MenuDto;
import matrix.project.mall.entity.Menu;
import matrix.project.mall.vo.MenuVo;

import java.util.List;

/**
 * @author wangcheng
 * @date 2020-02-29
 */
public interface MenuService extends IService<Menu> {

    List<MenuDto> queryTree();

    boolean saveTree(MenuVo menuVo);

    boolean removeTree(String menuId);

    Menu queryById(String menuId);
}
