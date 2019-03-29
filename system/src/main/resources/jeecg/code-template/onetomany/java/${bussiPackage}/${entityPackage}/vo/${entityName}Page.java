package ${bussiPackage}.${entityPackage}.vo;

import java.util.List;
import ${bussiPackage}.${entityPackage}.entity.${entityName};
<#list subTables as sub>
import ${bussiPackage}.${entityPackage}.entity.${sub.entityName};
</#list>
import lombok.Data;

@Data
public class ${entityName}Page {
	
	private ${entityName} ${entityName?uncap_first};
	<#list subTables as sub>
	private List<${sub.entityName}> ${sub.entityName?uncap_first}List;
	</#list>
	
}
