package ${mapperPackage};

import ${paramPackage}.${entityNameUp}Param;
import ${entityPackage}.${entityNameUp};
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ${entityNameUp}Mapper {
    
    /**
     *  按主键删除
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  插入一条记录
     */
    int insert(${entityNameUp} record);

    /**
     *  按主键查询
     */
    ${entityNameUp} selectByPrimaryKey(Integer id);

    /**
     *  查询所有记录
     */
    List<${entityNameUp}> selectAll();

    /**
     *  按主键更新
     */
    int updateByPrimaryKeySelective(${entityNameUp} record);

    /**
     *  按条件分页查询
     */
    List<${entityNameUp}> selectByCondition(@Param("param") ${entityNameUp}Param ${entityName}Param);
}
