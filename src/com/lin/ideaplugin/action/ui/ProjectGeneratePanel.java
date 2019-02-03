package com.lin.ideaplugin.action.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.lin.ideaplugin.dto.GenerateProjectExtend;
import com.lin.ideaplugin.service.ProjectGenerateService;
import com.lin.ideaplugin.utils.FileTool;
import com.lin.ideaplugin.utils.HttpClientUtil;
import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONString;
import org.jdesktop.swingx.prompt.BuddyButton;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProjectGeneratePanel {

    private JPanel mainPanel;

    private JButton generateProjectBtn;
    private JButton generateCurdBtn;
    private JButton openSavePathBtn;

    private JComboBox gitRepository;
    private JTextField projectName;
    private JTextField groupId;
    private JTextField artifactId;
    private JTextField version;
    private JTextField basePackage;
    private JProgressBar generateProgress;


    private Project project;

    private String gitRepositoryStr = "https://github.com/linners/springboot-multi-template-web.git";
    private String oldBasePackage = "com.lin.bulter";

    public ProjectGeneratePanel(Project myproject){
        project = myproject;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initData() {
        projectName.setText("product-base");
        groupId.setText("com.sprucetec.product");
        artifactId.setText("product-base");
        version.setText("1.0-SNAPSHOT");
        basePackage.setText("com.sprucetec.product.base");
        generateProgress.setVisible(false);
    }

    private void createUIComponents() {
        // 初始化数据
        initData();
        projectName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println(e);
            }
        });
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
        // 按钮监听事件
        generateProjectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                System.out.println("触发了事件");
                System.out.println(projectName.getText());
                // 自动生成文件
                autoGenerateProject();
                // 参数组装
//                Map<String, String> params = new HashMap<>();
//                params.put("gitRepository", gitRepositoryStr);
//                params.put("branchName", "master");
//                params.put("oldBasePackage", oldBasePackage);
//                params.put("rootGroupId", groupId.getText());
//                params.put("rootArtifactId", artifactId.getText());
//                params.put("version", version.getText());
//                params.put("projectName", projectName.getText());
//                params.put("basePackage", basePackage.getText());
//                String response = HttpClientUtil.post("https://www.wcler.cn/bulter/generate/project", new Gson().toJson(params));
//                String downloadFilePath = "d:/download/" + projectName.getText() + ".zip";
//                FileTool.downloadFile(response, downloadFilePath);
//                ZipUtil.upzipFile(downloadFilePath, "D:/download");
//                FileUtils.deleteQuietly(new File(downloadFilePath));
            }
        });
    }

    // 自动生成工程框架
    public void autoGenerateProject() {
        generateProgress.setVisible(true);
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
        generateProgress.setVisible(false);
        Messages.showInfoMessage("工程生成完毕，生成目录：！"+projectSavePath, "生成提示");
    }
}
