package com.lin.ideaplugin.service;

import com.google.common.io.Files;
import com.lin.ideaplugin.common.contants.VelocityFileType;
import com.lin.ideaplugin.common.dto.CurdTableInfo;
import com.lin.ideaplugin.common.dto.GenerateCurdParam;
import com.lin.ideaplugin.common.dto.TableColumnInfo;
import com.lin.ideaplugin.common.utils.StringUtil;
import com.lin.ideaplugin.common.utils.VelocityUtils;
import com.lin.ideaplugin.extension.CodeGenerateSetting;
import com.lin.ideaplugin.extension.SettingConfigure;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurdGenerateService {

    private static Logger logger = LoggerFactory.getLogger(CurdGenerateService.class);

    /**
     * 自动生成curd代码
     * @param param
     * @return
     */
    public List<String> generatorCurdCode(GenerateCurdParam curdParam) {
        List<String> resultList = new ArrayList<>();
        List<CurdTableInfo> tableInfos = curdParam.getTableInfos();
        if(tableInfos!=null && tableInfos.size()>0) {
            for(CurdTableInfo curdTableInfo : tableInfos){
                singleTableGenerate(resultList, curdParam, curdTableInfo.getTableName(), curdTableInfo.getEntityName(), curdTableInfo.getColumns());
            }
        }
        return resultList;
    }

    public void singleTableGenerate(List<String> resultList, GenerateCurdParam curdParam, String tableName, String myEntityName, List<TableColumnInfo> columnInfos){
        // setting config
        CodeGenerateSetting settings = curdParam.getSettings();
        SettingConfigure settingConfigure = settings.getSettingConfigure();
        Map<String, String> templateMap = settingConfigure.getCodeTemplateMap();

        String entityNameUp = StringUtil.upperFirst(myEntityName);
        String entityName = StringUtil.lowerFirst(entityNameUp);

        // velocity context
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("basePackage", settingConfigure.getBasePackage());
        velocityContext.put("mapperPackage", settingConfigure.getMapperPackage());
        velocityContext.put("entityPackage", settingConfigure.getEntityPackage());
        velocityContext.put("paramPackage", settingConfigure.getParamPackage());
        velocityContext.put("servicePackage", settingConfigure.getServicePackage());
        velocityContext.put("apiPackage", "");
        velocityContext.put("controllerPackage", settingConfigure.getControllerPackage());
        velocityContext.put("entityNameUp", entityNameUp);
        velocityContext.put("entityName", entityName);
        velocityContext.put("tableName", tableName);
        velocityContext.put("columns", columnInfos);

        // template val
        boolean mapperCheckBoxSelected = curdParam.isMapperCheckBox();
        boolean xmlCheckBoxSelected = curdParam.isXmlCheckBox();
        boolean entityCheckBoxSelected = curdParam.isEntityCheckBox();
        boolean paramCheckBoxSelected = curdParam.isParamCheckBox();
        boolean serviceCheckBoxSelected = curdParam.isServiceCheckBox();
        boolean apiCheckBoxSelected = curdParam.isApiCheckBox();
        boolean dtoCheckBoxSelected = curdParam.isDtoCheckBox();
        boolean controllerCheckBoxSelected = curdParam.isControllerCheckBox();
        boolean vueListCheckBoxSelected = curdParam.isVueListCheckBox();
        boolean vueNewCheckBoxSelected = curdParam.isVueNewCheckBox();
        boolean vueDetailCheckBoxSelected = curdParam.isVueDetailCheckBox();
        if(mapperCheckBoxSelected){
            String mapperTemplate = templateMap.get(VelocityFileType.MAPPER.getType());
            String filePath = generateTemplateFile(velocityContext, mapperTemplate, settingConfigure.getMapperPath() + "/" + entityNameUp + "Mapper.java");
            if(filePath!=null){
                resultList.add(settingConfigure.getMapperPackage()+ "." + entityNameUp + "Mapper.java");
            }
        }
        if(xmlCheckBoxSelected){
            String xmlTemplate = templateMap.get(VelocityFileType.MAPPER_XML.getType());
            String filePath1 = generateTemplateFile(velocityContext, xmlTemplate, settingConfigure.getXmlPath() + "/" + entityNameUp + "Mapper.xml");
            if(filePath1!=null){
                resultList.add(entityNameUp + "Mapper.xml");
            }
            String xmlExtendTemplate = templateMap.get(VelocityFileType.MAPPER_XML_EXTEND.getType());
            String filePath2 = generateTemplateFile(velocityContext, xmlExtendTemplate, settingConfigure.getXmlPath() + "/extend/" + entityNameUp + "Mapper.xml");
            if(filePath2!=null){
                resultList.add("extend/" + entityNameUp + "Mapper.xml");
            }
        }
        if(entityCheckBoxSelected){
            String entityTemplate = templateMap.get(VelocityFileType.ENTITY.getType());
            String filePath = generateTemplateFile(velocityContext, entityTemplate, settingConfigure.getEntityPath() + "/" + entityNameUp + ".java");
            if(filePath!=null){
                resultList.add(settingConfigure.getEntityPackage()+ "." + entityNameUp + ".java");
            }
        }
        if(paramCheckBoxSelected){
            String paramTemplate = templateMap.get(VelocityFileType.PARAM.getType());
            String filePath = generateTemplateFile(velocityContext, paramTemplate, settingConfigure.getParamPath() + "/" + entityNameUp + "Param.java");
            if(filePath!=null){
                resultList.add(settingConfigure.getParamPackage()+ "." + entityNameUp + "Param.java");
            }
        }
        if(serviceCheckBoxSelected){
            String serviceTemplate = templateMap.get(VelocityFileType.SERVICE.getType());
            String filePath1 = generateTemplateFile(velocityContext, serviceTemplate, settingConfigure.getServicePath() + "/" + entityNameUp + "Service.java");
            if(filePath1!=null){
                resultList.add(settingConfigure.getServicePackage() + "." + entityNameUp + "Service.java");
            }
            String serviceImplTemplate = templateMap.get(VelocityFileType.SERVICE_IMPL.getType());
            String filePath2 = generateTemplateFile(velocityContext, serviceImplTemplate, settingConfigure.getServicePath() + "/impl/" + entityNameUp + "ServiceImpl.java");
            if(filePath2!=null){
                resultList.add(settingConfigure.getServicePackage() + ".impl." + entityNameUp + "ServiceImpl.java");
            }
        }
        if(apiCheckBoxSelected){
            String apiTemplate = templateMap.get(VelocityFileType.API.getType());
            String filePath = generateTemplateFile(velocityContext, apiTemplate, settingConfigure.getServicePath() + "/" + entityNameUp + "Api.java");
            if(filePath!=null){
                resultList.add(settingConfigure.getServicePackage() + "." + entityNameUp + "Api.java");
            }
        }
        if(dtoCheckBoxSelected){
            String dtoTemplate = templateMap.get(VelocityFileType.DTO.getType());
            String filePath = generateTemplateFile(velocityContext, dtoTemplate, settingConfigure.getParamPath() + "/" + entityNameUp + "Dto.java");
            if(filePath!=null){
                resultList.add(settingConfigure.getParamPackage() + "." + entityNameUp + "Dto.java");
            }
        }
        if(controllerCheckBoxSelected){
            String controllerTemplate = templateMap.get(VelocityFileType.CONTROLLER.getType());
            String filePath = generateTemplateFile(velocityContext, controllerTemplate, settingConfigure.getControllerPath() + "/" + entityNameUp + "Controller.java");
            if(filePath!=null){
                resultList.add(settingConfigure.getControllerPackage() + "." + entityNameUp + "Controller.java");
            }
        }
        if(vueListCheckBoxSelected){
            String vueListTemplate = templateMap.get(VelocityFileType.VUE_LIST.getType());
            String filePath = generateTemplateFile(velocityContext, vueListTemplate, settingConfigure.getVuePagePath() + "/" + entityName + "/" + entityNameUp + "List.vue");
            if(filePath!=null){
                resultList.add(entityName + "/" + entityNameUp + "List.vue");
            }
        }
    }

    /**
     * 生成代码文件
     * @param velocityContext
     * @param templateVal
     * @param filePath
     * @return
     */
    public String generateTemplateFile(VelocityContext velocityContext, String templateVal, String filePath) {
        String newTemplateVal = VelocityUtils.getInstance().compileVelocityString(templateVal, velocityContext);
        try {
            Files.write(newTemplateVal.getBytes("UTF-8"), new File(filePath));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return filePath;
    }
}
