package com.lin.ideaplugin.ui;

import com.google.common.io.Files;
import com.intellij.openapi.progress.BackgroundTaskQueue;
import com.intellij.openapi.project.Project;
import com.lin.ideaplugin.common.contants.ActionType;
import com.lin.ideaplugin.common.contants.VelocityFileType;
import com.lin.ideaplugin.common.dto.GenerateCurdParam;
import com.lin.ideaplugin.common.utils.VelocityUtils;
import com.lin.ideaplugin.extension.CodeGenerateSetting;
import com.lin.ideaplugin.extension.SettingConfigure;
import com.lin.ideaplugin.task.GenerateCodeBackgroundTask;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.jetbrains.annotations.SystemIndependent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CurdGeneratePanel {

    private JPanel mainPanel;

    private JPanel topPanel;
    private JComboBox dataSources;
    private JComboBox dataBases;
    private JComboBox tables;

    private JPanel bottomPanel;
    private JCheckBox mapperCheckBox;
    private JCheckBox xmlCheckBox;
    private JCheckBox entityCheckBox;
    private JCheckBox paramCheckBox;
    private JCheckBox serviceCheckBox;
    private JCheckBox apiCheckBox;
    private JCheckBox dtoCheckBox;
    private JCheckBox controllerCheckBox;
    private JCheckBox vueListCheckBox;
    private JCheckBox vueNewCheckBox;
    private JCheckBox vueDetailCheckBox;

    private Project project;
    private String path;
    private ActionType actionType;

    private CodeGenerateSetting settings;

    public CurdGeneratePanel(CodeGenerateSetting mysettings, Project myproject, ActionType myActionType) {
        settings = mysettings;
        project = myproject;
        actionType = myActionType;
        initData();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initData() {
        mainPanel.setPreferredSize(new Dimension(550, 320));
    }

    // 自动生成curd代码
    public void autoGenerateCurd() {
        GenerateCurdParam curdParam = new GenerateCurdParam();
        curdParam.setMapperCheckBox(mapperCheckBox.isSelected());
        curdParam.setXmlCheckBox(xmlCheckBox.isSelected());
        curdParam.setEntityCheckBox(entityCheckBox.isSelected());
        curdParam.setParamCheckBox(paramCheckBox.isSelected());
        curdParam.setServiceCheckBox(serviceCheckBox.isSelected());
        curdParam.setApiCheckBox(apiCheckBox.isSelected());
        curdParam.setDtoCheckBox(dtoCheckBox.isSelected());
        curdParam.setControllerCheckBox(controllerCheckBox.isSelected());
        curdParam.setVueListCheckBox(vueListCheckBox.isSelected());
        curdParam.setVueNewCheckBox(vueNewCheckBox.isSelected());
        curdParam.setVueDetailCheckBox(vueDetailCheckBox.isSelected());
        curdParam.setSettings(settings);
        // 状态任务
        new BackgroundTaskQueue(project, "task name").run(new GenerateCodeBackgroundTask(project, "generate code ...", curdParam));

//        // setting config
//        SettingConfigure settingConfigure = settings.getSettingConfigure();
//        Map<String, String> templateMap = settingConfigure.getCodeTemplateMap();
//
//        String entityNameUp = "User";
//        String entityName = "user";
//
//        // velocity context
//        VelocityContext velocityContext = new VelocityContext();
//        velocityContext.put("basePackage", settingConfigure.getBasePackage());
//        velocityContext.put("mapperPackage", settingConfigure.getMapperPackage());
//        velocityContext.put("entityPackage", settingConfigure.getEntityPackage());
//        velocityContext.put("paramPackage", settingConfigure.getParamPackage());
//        velocityContext.put("servicePackage", settingConfigure.getServicePackage());
//        velocityContext.put("apiPackage", "");
//        velocityContext.put("controllerPackage", settingConfigure.getControllerPackage());
//        velocityContext.put("entityNameUp", entityNameUp);
//        velocityContext.put("entityName", entityName);
//        velocityContext.put("tableName", "");
//
//        // template val
//        boolean mapperCheckBoxSelected = mapperCheckBox.isSelected();
//        boolean xmlCheckBoxSelected = xmlCheckBox.isSelected();
//        boolean entityCheckBoxSelected = entityCheckBox.isSelected();
//        boolean paramCheckBoxSelected = paramCheckBox.isSelected();
//        boolean serviceCheckBoxSelected = serviceCheckBox.isSelected();
//        boolean apiCheckBoxSelected = apiCheckBox.isSelected();
//        boolean dtoCheckBoxSelected = dtoCheckBox.isSelected();
//        boolean controllerCheckBoxSelected = controllerCheckBox.isSelected();
//        boolean vueListCheckBoxSelected = vueListCheckBox.isSelected();
//        boolean vueNewCheckBoxSelected = vueNewCheckBox.isSelected();
//        boolean vueDetailCheckBoxSelected = vueDetailCheckBox.isSelected();
//        if(mapperCheckBoxSelected){
//            String mapperTemplate = templateMap.get(VelocityFileType.MAPPER.getType());
//            generateTemplateFile(velocityContext, mapperTemplate, settingConfigure.getMapperPath()+"/"+entityNameUp+"Mapper.java");
//        }
//        if(xmlCheckBoxSelected){
//            String xmlTemplate = templateMap.get(VelocityFileType.MAPPER_XML.getType());
//            generateTemplateFile(velocityContext, xmlTemplate, settingConfigure.getXmlPath()+"/"+entityNameUp+"Mapper.xml");
//            String xmlExtendTemplate = templateMap.get(VelocityFileType.MAPPER_XML_EXTEND.getType());
//            generateTemplateFile(velocityContext, xmlExtendTemplate, settingConfigure.getXmlPath()+"/extend/"+entityNameUp+"Mapper.xml");
//        }
//        if(entityCheckBoxSelected){
//            String entityTemplate = templateMap.get(VelocityFileType.ENTITY.getType());
//            generateTemplateFile(velocityContext, entityTemplate, settingConfigure.getEntityPath()+"/"+entityNameUp+".java");
//        }
//        if(paramCheckBoxSelected){
//            String paramTemplate = templateMap.get(VelocityFileType.PARAM.getType());
//            generateTemplateFile(velocityContext, paramTemplate, settingConfigure.getParamPath()+"/"+entityNameUp+"Param.java");
//        }
//        if(serviceCheckBoxSelected){
//            String serviceTemplate = templateMap.get(VelocityFileType.SERVICE.getType());
//            generateTemplateFile(velocityContext, serviceTemplate, settingConfigure.getServicePath()+"/"+entityNameUp+"Service.java");
//            String serviceImplTemplate = templateMap.get(VelocityFileType.SERVICE_IMPL.getType());
//            generateTemplateFile(velocityContext, serviceImplTemplate, settingConfigure.getXmlPath()+"/impl/"+entityNameUp+"ServiceImpl.java");
//        }
//        if(apiCheckBoxSelected){
//            String apiTemplate = templateMap.get(VelocityFileType.API.getType());
//            generateTemplateFile(velocityContext, apiTemplate, settingConfigure.getServicePath()+"/"+entityNameUp+"Api.java");
//        }
//        if(dtoCheckBoxSelected){
//            String dtoTemplate = templateMap.get(VelocityFileType.DTO.getType());
//            generateTemplateFile(velocityContext, dtoTemplate, settingConfigure.getParamPath()+"/"+entityNameUp+"Dto.java");
//        }
//        if(controllerCheckBoxSelected){
//            String controllerTemplate = templateMap.get(VelocityFileType.CONTROLLER.getType());
//            generateTemplateFile(velocityContext, controllerTemplate, settingConfigure.getControllerPath()+"/"+entityNameUp+"Controller.java");
//        }
//        if(vueListCheckBoxSelected){
//            String vueListTemplate = templateMap.get(VelocityFileType.VUE_LIST.getType());
//            generateTemplateFile(velocityContext, vueListTemplate, settingConfigure.getVuePagePath()+"/"+entityName+"/"+entityNameUp+"List.vue");
//        }
    }

//    public void generateTemplateFile(VelocityContext velocityContext, String templateVal, String filePath) {
//        String newTemplateVal = VelocityUtils.getInstance().compileVelocityString(templateVal, velocityContext);
//        try {
//            Files.write(newTemplateVal.getBytes(Charset.forName("utf-8")), new File(filePath));
//            System.out.println("生成完毕！");
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//    }
}
