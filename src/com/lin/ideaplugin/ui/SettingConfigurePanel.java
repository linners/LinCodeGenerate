package com.lin.ideaplugin.ui;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileChooser.FileChooserDialog;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.uiDesigner.core.GridConstraints;
import com.lin.ideaplugin.extension.CodeGenerateSetting;
import com.lin.ideaplugin.extension.SettingConfigure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SettingConfigurePanel {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JTextField basePackage;
    private JTextField mapperPath;
    private JTextField entityPath;
    private JTextField xmlPath;
    private JTextField paramPath;
    private JTextField servicePath;
    private JTextField controllerPath;
    private JTextField vuePaagePath;

    private JButton mapperBtn;
    private JButton entityBtn;
    private JButton xmlBtn;
    private JButton paramBtn;
    private JButton serviceBtn;
    private JButton controllerBtn;
    private JButton vueBtn;

    private JPanel bottomPanel;
    private JBTabbedPane tabbedPanel;
    private Map<String, SettingCodeTemplate> editPanelMap;

    public SettingConfigurePanel(CodeGenerateSetting setting, Project project) {
        initBtn(project);
        editPanelMap = new HashMap<>();
        SettingConfigure settingConfigure = setting.getSettingConfigure();
        basePackage.setText(settingConfigure.getBasePackage());
        mapperPath.setText(settingConfigure.getMapperPath());
        entityPath.setText(settingConfigure.getEntityPath());
        xmlPath.setText(settingConfigure.getXmlPath());
        paramPath.setText(settingConfigure.getParamPath());
        servicePath.setText(settingConfigure.getServicePath());
        controllerPath.setText(settingConfigure.getControllerPath());
        vuePaagePath.setText(settingConfigure.getVuePagePath());
        tabbedPanel = new JBTabbedPane();
        Map<String, String> codeTemplateMap = settingConfigure.getCodeTemplateMap();
        if (codeTemplateMap == null) {
            codeTemplateMap = new HashMap<>();
        }
        codeTemplateMap.forEach((templateName, templateVal) -> {
            SettingCodeTemplate settingCodeTemplate = new SettingCodeTemplate(setting, templateVal);
            tabbedPanel.addTab(templateName, settingCodeTemplate.getMainPanel());
            editPanelMap.put(templateName, settingCodeTemplate);
        });
        GridConstraints constraints = new GridConstraints();
        constraints.setRow(0);
        constraints.setFill(GridConstraints.FILL_BOTH);
        bottomPanel.add(tabbedPanel, constraints);
    }

    private void initBtn(Project project) {
        mapperBtn.setPreferredSize(new Dimension(30, 30));
        mapperBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mapperPathStr = chooseDirectory(project);
                mapperPath.setText(mapperPathStr);
            }
        });
        entityBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String entityPathStr = chooseDirectory(project);
                entityPath.setText(entityPathStr);
            }
        });
        xmlBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String xmlPathStr = chooseDirectory(project);
                xmlPath.setText(xmlPathStr);
            }
        });
        paramBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String paramPathStr = chooseDirectory(project);
                paramPath.setText(paramPathStr);
            }
        });
        serviceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String servicePathStr = chooseDirectory(project);
                servicePath.setText(servicePathStr);
            }
        });
        controllerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String controllerPathStr = chooseDirectory(project);
                controllerPath.setText(controllerPathStr);
            }
        });
        vueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vuePathStr = chooseDirectory(project);
                vuePaagePath.setText(vuePathStr);
            }
        });
    }

    /**
     * 选择文件路径
     * @param project
     * @return
     */
    private String chooseDirectory(Project project) {
        FileChooserDescriptor descriptor =
                FileChooserDescriptorFactory.createSingleFolderDescriptor()
                        .withTitle("Export Directory Location")
                        .withDescription("Choose directory to export run configurations to")
                        .withHideIgnored(false);
        FileChooserDialog chooser =
                FileChooserFactory.getInstance().createFileChooser(descriptor, project, null);

        final VirtualFile[] files;
//        File existingLocation = new File(getOutputDirectoryPath());
//        if (existingLocation.exists()) {
//            VirtualFile toSelect =
//                    LocalFileSystem.getInstance().refreshAndFindFileByPath(existingLocation.getPath());
//            files = chooser.choose(null, toSelect);
//        } else {
        files = chooser.choose(null);
//        }
        if (files.length == 0) {
            return "";
        }
        VirtualFile file = files[0];
        return file.getPath();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public SettingConfigure getSettingConfigure() {
        SettingConfigure settingConfigure = new SettingConfigure();
        settingConfigure.setBasePackage(basePackage.getText());

        String mapperPathText = mapperPath.getText();
        settingConfigure.setMapperPath(mapperPathText);
        settingConfigure.setMapperPackage(getPackage(mapperPathText));

        String entityPathText = entityPath.getText();
        settingConfigure.setEntityPath(entityPathText);
        settingConfigure.setEntityPackage(getPackage(entityPathText));

        settingConfigure.setXmlPath(xmlPath.getText());

        String paramPathText = paramPath.getText();
        settingConfigure.setParamPath(paramPathText);
        settingConfigure.setParamPackage(getPackage(paramPathText));

        String servicePathText = servicePath.getText();
        settingConfigure.setServicePath(servicePathText);
        settingConfigure.setServicePackage(getPackage(servicePathText));

        String controllerPathText = controllerPath.getText();
        settingConfigure.setControllerPath(controllerPathText);
        settingConfigure.setControllerPackage(getPackage(controllerPathText));

        settingConfigure.setVuePagePath(vuePaagePath.getText());

        Map<String, String> templateMap = new HashMap<>();
        editPanelMap.forEach((templateName, settingCodeTemplate) -> {
            templateMap.put(templateName, settingCodeTemplate.getTemplate());
        });
        settingConfigure.setCodeTemplateMap(templateMap);
        return settingConfigure;
    }

    /**
     * 获得package
     * @param packagePath
     * @return
     */
    public String getPackage(String packagePath) {
        int index = packagePath.indexOf("src/main/java");
        String substring = packagePath.substring(index+14);
        String str = substring.replaceAll("/", ".");
        return str;
    }
}
