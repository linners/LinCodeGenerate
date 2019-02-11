package ${servicePackage}.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${servicePackage}.${entityNameUp}Service;
import ${paramPackage}.${entityNameUp}Param;
import ${mapperPackage}.${entityNameUp}Mapper;
import ${entityPackage}.${entityNameUp};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ${entityNameUp}ServiceImpl implements ${entityNameUp}Service {

    @Autowired
    private ${entityNameUp}Mapper ${entityName}Mapper;

    @Override
    @Transactional
    public Integer insert${entityNameUp}(${entityNameUp} ${entityName}) {
        return ${entityName}Mapper.insert(${entityName});
    }

    @Override
    @Transactional
    public Integer update${entityNameUp}ById(${entityNameUp} ${entityName}) {
        return ${entityName}Mapper.updateByPrimaryKeySelective(${entityName});
    }

    @Override
    @Transactional
    public Integer delete${entityNameUp}ById(Integer ${entityName}Id) {
        return ${entityName}Mapper.deleteByPrimaryKey(${entityName}Id);
    }

    @Override
    public ${entityNameUp} select${entityNameUp}ById(Integer ${entityName}Id) {
        return ${entityName}Mapper.selectByPrimaryKey(${entityName}Id);
    }

    @Override
    public List<${entityNameUp}> selectAll${entityNameUp}s() {
        return ${entityName}Mapper.selectAll();
    }

    @Override
    public PageInfo<${entityNameUp}> select${entityNameUp}ByPage(${entityNameUp}Param ${entityName}Param) {
        PageInfo<${entityNameUp}> ${entityName}s = PageHelper.startPage(${entityName}Param.getPage().getPageNum(), ${entityName}Param.getPage().getPageSize())
                .doSelectPageInfo(() -> ${entityName}Mapper.selectByCondition(${entityName}Param));
        return ${entityName}s;
    }
}
