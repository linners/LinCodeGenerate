package ${apiPackage};

import ${paramPackage}.${entityNameUp}Dto;
import ${paramPackage}.${entityNameUp}Param;
import ${paramPackage}.common.BaseResultDto;
import ${paramPackage}.common.Page;

import java.util.List;

public interface ${entityNameUp}Api {

    /**
     * 新增
     * @return
     */
    BaseResultDto<Integer> insert${entityNameUp}(${entityNameUp}Dto ${entityName}Dto);

    /**
     * 修改
     * @return
     */
    BaseResultDto<Integer> update${entityNameUp}ById(${entityNameUp}Dto ${entityName}Dto);

    /**
     * 删除
     */
    BaseResultDto<Integer> delete${entityNameUp}ById(Integer ${entityName}Id);

    /**
     * 查询所有
     */
    BaseResultDto<List<${entityNameUp}Dto>> selectAll${entityNameUp}s();

    /**
     * 分页查询
     */
    BaseResultDto<Page<${entityNameUp}Dto>> select${entityNameUp}ByPage(${entityNameUp}Param ${entityName}Param);
}
