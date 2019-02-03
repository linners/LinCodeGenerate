package ${servicePackage};

import com.github.pagehelper.PageInfo;
import ${paramPackage}.${entityNameUp}Param;
import ${entityPackage}.${entityNameUp};

import java.util.List;

public interface ${entityNameUp}Service {

    /**
     * 新增
     *
     * @return
     */
    Integer insert${entityNameUp}(${entityNameUp} ${entityName});

    /**
     * 修改
     *
     * @return
     */
    Integer update${entityNameUp}ById(${entityNameUp} ${entityName});

    /**
     * 删除
     */
    Integer delete${entityNameUp}ById(Integer ${entityName}Id);

    /**
     * 按主键查询
     */
    ${entityNameUp} select${entityNameUp}ById(Integer ${entityName}Id);

    /**
     * 查询所有
     */
    List<${entityNameUp}> selectAll${entityNameUp}s();

    /**
     * 分页查询
     */
    PageInfo<${entityNameUp}> select${entityNameUp}ByPage(${entityNameUp}Param ${entityName}Param);
}
