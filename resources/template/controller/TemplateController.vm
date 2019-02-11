package ${controllerPackage};

import com.github.pagehelper.PageInfo;
import ${servicePackage}.${entityNameUp}Service;
import ${paramPackage}.${entityNameUp}Param;
import ${entityPackage}.${entityNameUp};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${entityName}")
public class ${entityNameUp}Controller {

    @Autowired
    private ${entityNameUp}Service ${entityName}Service;

    /**
     * 新增
     * @return
     */
    @PostMapping("/save")
    public Integer save${entityNameUp}(@RequestBody ${entityNameUp} ${entityName}) {
        Integer result = ${entityName}Service.insert${entityNameUp}(${entityName});
        return result;
    }

    /**
     * 修改
     * @return
     */
    @PostMapping("/update")
    public Integer update${entityNameUp}ById(@RequestBody ${entityNameUp} ${entityName}) {
        Integer result = ${entityName}Service.update${entityNameUp}ById(${entityName});
        return result;
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delete/{${entityName}Id}")
    public Integer delete${entityNameUp}ById(@PathVariable("${entityName}Id") Integer ${entityName}Id) {
        Integer result = ${entityName}Service.delete${entityNameUp}ById(${entityName}Id);
        return result;
    }

    /**
     * 按主键查询
     * @return
     */
    @GetMapping("/get${entityNameUp}ById/{${entityName}Id}")
    public ${entityNameUp} get${entityNameUp}ById(@PathVariable("${entityName}Id") Integer ${entityName}Id) {
        ${entityNameUp} result = ${entityName}Service.select${entityNameUp}ById(${entityName}Id);
        return result;
    }

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/getAll${entityNameUp}s")
    public List<${entityNameUp}> getAll${entityNameUp}s() {
        List<${entityNameUp}> result = ${entityName}Service.selectAll${entityNameUp}s();
        return result;
    }

    /**
     * 分页查询
     * @param param
     * @return
     */
    @PostMapping("/get${entityNameUp}ByPage")
    public PageInfo<${entityNameUp}> get${entityNameUp}ByPage(${entityNameUp}Param param) {
        PageInfo<${entityNameUp}> result = ${entityName}Service.select${entityNameUp}ByPage(param);
        return result;
    }
}
