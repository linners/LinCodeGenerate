package com.lin.ideaplugin.action.project;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.lin.ideaplugin.ui.ProjectGeneratePanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AutoGenerateProjectDialog extends DialogWrapper {

    private final ProjectGeneratePanel panel;

    public AutoGenerateProjectDialog(@Nullable Project project) {
        super(project);
        panel = new ProjectGeneratePanel(project, this);
        setTitle("project auto generate");
        setOKActionEnabled(false);
        init();
    }

    /**
     * 自动生成project框架代码
     */
    public void autoGenerateProject() {
        panel.autoGenerateProject2();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel.getMainPanel();
    }
}
