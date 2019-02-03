package ${servicePackage}.api.impl;

import com.github.pagehelper.PageInfo;
import ${apiPackage}.${entityNameUp}Api;
import ${servicePackage}.${entityNameUp}Service;
import ${basePackage}.common.utils.BeanUtil;
import ${paramPackage}.${entityNameUp}Dto;
import ${paramPackage}.${entityNameUp}Param;
import ${paramPackage}.common.BaseResultDto;
import ${paramPackage}.common.Page;
import ${entityPackage}.${entityNameUp};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${entityNameUp}ApiImpl implements ${entityNameUp}Api {

    @Autowired
    private ${entityNameUp}Service ${entityName}Service;

    @Override
    public BaseResultDto<Integer> insert${entityNameUp}(${entityNameUp}Dto ${entityName}Dto) {
        ${entityNameUp} ${entityName} = BeanUtil.copy(${entityName}Dto, () -> {
            ${entityNameUp} ${entityName}Tmp = new ${entityNameUp}();
            return ${entityName}Tmp;
        });
        ${entityName}Service.insert${entityNameUp}(${entityName});
        return BaseResultDto.renderSuccessResult(${entityName}.getId());
    }

    @Override
    public BaseResultDto<Integer> update${entityNameUp}ById(${entityNameUp}Dto ${entityName}Dto) {
        ${entityNameUp} ${entityName} = BeanUtil.copy(${entityName}Dto, () -> {
            ${entityNameUp} ${entityName}Tmp = new ${entityNameUp}();
            return ${entityName}Tmp;
        });
        ${entityName}Service.update${entityNameUp}ById(${entityName});
        return BaseResultDto.renderSuccessResult(${entityName}.getId());
    }

    @Override
    public BaseResultDto<Integer> delete${entityNameUp}ById(Integer ${entityName}Id) {
        ${entityName}Service.delete${entityNameUp}ById(${entityName}Id);
        return BaseResultDto.renderSuccessResult(${entityName}Id);
    }

    @Override
    public BaseResultDto<List<${entityNameUp}Dto>> selectAll${entityNameUp}s() {
        List<${entityNameUp}> ${entityName}s = ${entityName}Service.selectAll${entityNameUp}s();
        List<${entityNameUp}Dto> ${entityName}Dtos = BeanUtil.copyList(${entityName}s, () -> {
            ${entityNameUp}Dto ${entityName}Dto = new ${entityNameUp}Dto();
            return ${entityName}Dto;
        });
        return BaseResultDto.renderSuccessResult(${entityName}Dtos);
    }

    @Override
    public BaseResultDto<Page<${entityNameUp}Dto>> select${entityNameUp}ByPage(${entityNameUp}Param ${entityName}Param) {
        // 查询数据
        PageInfo<${entityNameUp}> ${entityName}PageInfo = ${entityName}Service.select${entityNameUp}ByPage(${entityName}Param);
        // 转换
        List<${entityNameUp}Dto> resultList = BeanUtil.copyList(${entityName}PageInfo.getList(), () -> {
            ${entityNameUp}Dto ${entityName}Dto = new ${entityNameUp}Dto();
            return ${entityName}Dto;
        });
        // 装载数据
        Page<${entityNameUp}Dto> resultPage = new Page<>(resultList, ${entityName}PageInfo.getTotal(), ${entityName}PageInfo.getPages());
        return BaseResultDto.renderSuccessResult(resultPage);
    }
}
