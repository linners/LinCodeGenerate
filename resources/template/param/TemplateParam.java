package ${paramPackage};

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ${entityNameUp}Param {

    private PageParam page;
    #foreach($column in $columns)#if($column.columnComment!='')// $column.columnComment #end

    private $column.fieldType $column.fieldName; 
    #end

}
