package com.lin.ideaplugin.ui;

import com.google.gson.Gson;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileChooser.FileChooserDialog;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.lin.ideaplugin.action.project.AutoGenerateProjectDialog;
import com.lin.ideaplugin.common.dto.GenerateProjectExtend;
import com.lin.ideaplugin.common.utils.FileTool;
import com.lin.ideaplugin.common.utils.HttpClientUtil;
import com.lin.ideaplugin.common.utils.ZipUtils;
import com.lin.ideaplugin.service.ProjectGenerateService;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
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

    private JComboBox gitRepository;
    private JTextField projectName;
    private JTextField groupId;
    private JTextField artifactId;
    private JTextField version;
    private JTextField basePackage;
    private JTextField savePath;
    private JButton choseSavePath;
    private JButton autoCreateBtn;
    private JTextArea resultMsg;

    private Project project;

    private String gitRepositoryStr = "https://github.com/linners/springboot-multi-template-web.git";
    private String oldBasePackage = "com.lin.bulter";
    private AutoGenerateProjectDialog dialog;

    private String lineSeparator = System.getProperty("line.separator");

    public ProjectGeneratePanel(Project myproject, AutoGenerateProjectDialog projectDialog){
        project = myproject;
        dialog = projectDialog;
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

        resultMsg.setPreferredSize(new Dimension (300, 50));
        resultMsg.setAutoscrolls(true);
        resultMsg.setMargin(new Insets(10, 10, 10, 10));

        // 监听下拉事件
        gitRepository.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    Object item = e.getItem();
                    if(item.toString().equals("多模块-web-模板")){
                        gitRepositoryStr = "https://github.com/linners/springboot-multi-template-web.git";
                        oldBasePackage = "com.lin.bulter";
                    }else if(item.toString().equals("多模块-web-dubbo-模板")){
                        gitRepositoryStr = "https://github.com/linners/springboot-multi-template-web-dubbo.git";
                        oldBasePackage = "com.sprucetec.product.base";
                    }else if(item.toString().equals("多模块-web-京东-模板")){
                        gitRepositoryStr = "https://github.com/linners/springboot-multi-template-web-jd.git";
                        oldBasePackage = "com.lin.base";
                    }
                }
            }
        });

        // 选择保存路径
        choseSavePath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String savePathStr = chooseDirectory(project, savePath.getText());
                savePath.setText(savePathStr);
            }
        });

        autoCreateBtn.addActionListener(e -> {
            showResultMsg("项目生成中，请稍后。。。。" + lineSeparator);
            // 自动生成代码
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    autoGenerateProject2();
                }
            });
        });
    }

    /**
     * 选择文件路径
     * @param project
     * @return
     */
    private String chooseDirectory(Project project, String path) {
        FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor()
                .withTitle("Select Directory Location")
                .withDescription("Choose directory for lin generate code configurations")
                .withHideIgnored(true);
        FileChooserDialog chooser = FileChooserFactory.getInstance().createFileChooser(descriptor, project, null);
        final VirtualFile[] files;
        File existingLocation = new File(path);
        if (existingLocation.exists()) {
            VirtualFile toSelect = LocalFileSystem.getInstance().refreshAndFindFileByPath(existingLocation.getPath());
            files = chooser.choose(project, toSelect);
        } else {
            files = chooser.choose(project);
        }
        if (files==null) {
            return "";
        }
        VirtualFile file = files[0];
        return file.getPath();
    }

    public void showResultMsg(String msg) {
        resultMsg.append(msg);
        resultMsg.paintImmediately(resultMsg.getBounds());
    }

    public void autoGenerateProject2() {
        String projectSavePath = savePath.getText()==null?"d:/download":savePath.getText();
        // 参数组装
        Map<String, String> params = new HashMap<>();
        params.put("gitRepository", gitRepositoryStr);
        params.put("branchName", "master");
        params.put("oldBasePackage", oldBasePackage);
        params.put("rootGroupId", groupId.getText());
        params.put("rootArtifactId", artifactId.getText());
        params.put("version", version.getText());
        params.put("projectName", projectName.getText());
        params.put("basePackage", basePackage.getText());
        // 得到生成的zip文件
        String response = HttpClientUtil.post("https://www.wcler.com/bulter/generate/project", new Gson().toJson(params));
        // 下载zip文件
        String downloadFilePath = projectSavePath + projectName.getText() + ".zip";
        FileTool.downloadFile(response, downloadFilePath);
        // 解压zip文件
        try {
            ZipUtils.unFiles(downloadFilePath, projectSavePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 删除zip文件
        FileUtils.deleteQuietly(new File(downloadFilePath));
        Messages.showInfoMessage("工程生成完毕，生成目录：！"+projectSavePath, "生成提示");
        showResultMsg("项目生成完毕，生成路径为：" + projectSavePath + lineSeparator);
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
        param.setGeneratedProjectPath(savePath.getText());
        projectGenerateService.generatorProject(param, this);
        Messages.showInfoMessage("工程生成完毕，生成目录：！"+projectSavePath, "生成提示");
    }
}
