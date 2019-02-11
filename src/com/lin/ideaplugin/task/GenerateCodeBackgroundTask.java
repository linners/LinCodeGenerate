package com.lin.ideaplugin.task;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import com.lin.ideaplugin.common.dto.GenerateCurdParam;
import com.lin.ideaplugin.service.CurdGenerateService;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class GenerateCodeBackgroundTask extends Task.Backgroundable {

    private final Project project;
    private GenerateCurdParam curdParam;

    public GenerateCodeBackgroundTask(@Nullable Project project, @Nls(capitalization = Nls.Capitalization.Title) @NotNull String title, GenerateCurdParam curdParam) {
        super(project, title);      // title会显示在状态栏的进度条中
        this.project = project;
        this.curdParam = curdParam;
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        progressIndicator.setIndeterminate(true);   // true幕后进度条，即走马灯
        // 核心业务逻辑代码
        CurdGenerateService curdGenerateService = new CurdGenerateService();
        List<String> resultList = curdGenerateService.generatorCurdCode(curdParam);
        String result = buildDisplayContent(resultList);
        VirtualFile projectFile = project.getBaseDir();
        projectFile.refresh(true, true);
        progressIndicator.setIndeterminate(false);  // 关闭走马灯
        progressIndicator.setFraction(1.0);     // 设置进度为100%
        progressIndicator.setText("generate code complete.");

        StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder("Generate Code complete", MessageType.ERROR.INFO, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);       // 在状态栏弹出提示信息“Generate Code complete”

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(resultList.isEmpty()){
                    Messages.showMessageDialog("No File Generate", "wWarning", AllIcons.Icon);
                }
                Messages.showMessageDialog(project, result, "Generate Result", AllIcons.Icon);
            }
        });
    }

    private String buildDisplayContent(List<String> resultList){
        StringBuilder stringBuilder = new StringBuilder();
        for(String filePath : resultList) {
            stringBuilder.append(filePath).append("\r\n");
        }
        return stringBuilder.toString();
    }
}
