package com.lin.ideaplugin.action.ui;

import com.google.common.io.Files;
import com.intellij.openapi.project.Project;
import com.lin.ideaplugin.contants.ActionType;
import com.lin.ideaplugin.utils.VelocityUtils;
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
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurdGeneratePanel {

    private JPanel mainPanel;

    private JPanel jpanelLeft;
    private JTextField basePackage;
    private JTextField mapperPackage;
    private JTextField entityName;

    private JPanel jpanelRight;
    private JTextArea tempateTextArea;
    private JButton generateCurdBtn;

    private Project project;
    private String path;
    private ActionType actionType;

    public CurdGeneratePanel(Project myproject, String clickPath, ActionType myActionType) {
        project = myproject;
        path = clickPath;
        actionType = myActionType;
        initData();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initData() {
        jpanelRight.setPreferredSize(new Dimension(350, 490));
        mainPanel.setPreferredSize(new Dimension(900, 500));
        // 初始化模板内容
        String templateContent = getTemplateFileContent();
        tempateTextArea.setText(templateContent);
        // 基础包
        @SystemIndependent String basePath = project.getBasePath();
        String tempPath = path.replaceAll(basePath, "").replaceAll("src/main/java/", "");
        String[] split = tempPath.trim().split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < split.length; i++) {
            sb.append(".").append(split[i]);
        }
        String clickPackage = sb.substring(1);
        mapperPackage.setText(clickPackage);
        basePackage.setText(clickPackage);

        // 按钮监听事件
        generateCurdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                String templateVal = tempateTextArea.getText();
                VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("controllerPackage", "");
                velocityContext.put("servicePackage", "");
                velocityContext.put("entityPackage", "");
                velocityContext.put("paramPackage", "");
                velocityContext.put("mapperPackage", "");
                velocityContext.put("apiPackage", "");
                velocityContext.put("basePackage", basePackage);
                velocityContext.put("entityNameUp", entityName.getText());
                velocityContext.put("entityName", entityName.getText());
                velocityContext.put("tableName", "");
                String newTemplateVal = VelocityUtils.getInstance().compileVelocityString(templateVal, velocityContext);
                try {
                    Files.write(newTemplateVal.getBytes(Charset.forName("utf-8")), new File(path + "/" + entityName.getText() + "Mapper.java"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

    /**
     * 获得模板内容
     * @param templatePath
     * @return
     */
    private String getTemplateContent(String templatePath) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(templatePath);
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    /**
     * 获得模板和模板内容
     * @param actionType
     * @return
     */
    private String getTemplateFileContent() {
        // Map<模板类型，模板内容>
        Map<String, String> templateMap = new HashMap<>();
        if (actionType == ActionType.MAPPER) {
            return getTemplateContent("template/mapper/TemplateMapper.java");
        } else if (actionType == ActionType.MAPPER_XML) {
            return getTemplateContent("template/xml/TemplateMapper.xml");
        } else if (actionType == ActionType.ENTITY) {
            return getTemplateContent("template/entity/TemplateEntity.java");
        } else if (actionType == ActionType.PARAM) {
            return getTemplateContent("template/param/TemplateParam.java");
        } else if (actionType == ActionType.SERVICE) {
            return getTemplateContent("template/service/TemplateService.java");
        } else if (actionType == ActionType.SERVICE_IMPL) {
            return getTemplateContent("template/service/TemplateServiceImpl.java");
        } else if (actionType == ActionType.API) {
            return getTemplateContent("template/api/TemplateApi.java");
        } else if (actionType == ActionType.API_IMPL) {
            return getTemplateContent("template/api/TemplateApiImpl.java");
        }  else if (actionType == ActionType.DTO) {
            return getTemplateContent("template/param/TemplateDto.java");
        } else if (actionType == ActionType.CONTROLLER) {
            return getTemplateContent("template/controller/TemplateController.java");
        } else if (actionType == ActionType.VUE_LIST) {
            return getTemplateContent("template/vue/TemplateVueList.vue");
        } else if (actionType == ActionType.VUE_ADD) {
            return getTemplateContent("template/vue/TemplateVueAdd.vue");
        } else if (actionType == ActionType.VUE_DETAIL) {
            return getTemplateContent("template/vue/TemplateVueDetail.vue");
        }
        return null;
    }
}
