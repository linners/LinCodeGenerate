package com.lin.ideaplugin.ui;

import com.google.gson.Gson;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.lin.ideaplugin.common.dto.GenerateProjectExtend;
import com.lin.ideaplugin.common.utils.FileTool;
import com.lin.ideaplugin.common.utils.HttpClientUtil;
import com.lin.ideaplugin.service.ProjectGenerateService;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ProjectGeneratePanel {

    private JPanel mainPanel;

    private JComboBox gitRepository;
    private JTextField projectName;
    private JTextField groupId;
    private JTextField artifactId;
    private JTextField version;
    private JTextField basePackage;

    private Project project;

    private String gitRepositoryStr = "https://github.com/linners/springboot-multi-template-web.git";
    private String oldBasePackage = "com.lin.bulter";

    public ProjectGeneratePanel(Project myproject){
        project = myproject;
        // 初始化数据
        initData();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initData() {
        mainPanel.setPreferredSize(new Dimension(550, 320));
        projectName.setText("product-base");
        groupId.setText("com.sprucetec.product");
        artifactId.setText("product-base");
        version.setText("1.0-SNAPSHOT");
        basePackage.setText("com.sprucetec.product.base");

        // 监听下拉事件
        gitRepository.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    Object item = e.getItem();
                    if(item.toString().equals("多模块-web-模板")){
                        gitRepositoryStr = "https://github.com/linners/springboot-multi-template-web.git";
                        oldBasePackage = "com.lin.bulter";
                    }else {
                        gitRepositoryStr = "https://github.com/linners/springboot-multi-template-web-dubbo.git";
                        oldBasePackage = "com.sprucetec.product.base";
                    }
                }
            }
        });
    }

    private void createUIComponents() {
//        // 参数组装
//        Map<String, String> params = new HashMap<>();
//        params.put("gitRepository", gitRepositoryStr);
//        params.put("branchName", "master");
//        params.put("oldBasePackage", oldBasePackage);
//        params.put("rootGroupId", groupId.getText());
//        params.put("rootArtifactId", artifactId.getText());
//        params.put("version", version.getText());
//        params.put("projectName", projectName.getText());
//        params.put("basePackage", basePackage.getText());
//        String response = HttpClientUtil.post("https://www.wcler.cn/bulter/generate/project", new Gson().toJson(params));
//        String downloadFilePath = "d:/download/" + projectName.getText() + ".zip";
//        FileTool.downloadFile(response, downloadFilePath);
//        ZipUtil.upzipFile(downloadFilePath, "D:/download");
//        FileUtils.deleteQuietly(new File(downloadFilePath));
    }

    // 自动生成工程框架
    public void autoGenerateProject() {
        VirtualFile baseDir = project.getBaseDir();
        String projectSavePath = baseDir.getParent().getPath();
        ProjectGenerateService projectGenerateService = new ProjectGenerateService("E:/workspace/github", projectSavePath);
        GenerateProjectExtend param = new GenerateProjectExtend();
        param.setGitRepository(gitRepositoryStr);
        param.setBranchName("master");
        param.setOldBasePackage(oldBasePackage);
        param.setRootGroupId(groupId.getText());
        param.setRootArtifactId(artifactId.getText());
        param.setVersion(version.getText());
        param.setProjectName(projectName.getText());
        param.setBasePackage(basePackage.getText());
        projectGenerateService.generatorProject(param);
        Messages.showInfoMessage("工程生成完毕，生成目录：！"+projectSavePath, "生成提示");
    }
}
