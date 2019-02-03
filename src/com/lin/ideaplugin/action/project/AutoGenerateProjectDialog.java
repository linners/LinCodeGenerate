package com.lin.ideaplugin.action.project;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.lin.ideaplugin.action.ui.ProjectGeneratePanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AutoGenerateProjectDialog extends DialogWrapper {

    private final ProjectGeneratePanel panel;

    public AutoGenerateProjectDialog(@Nullable Project project) {
        super(project);
        panel = new ProjectGeneratePanel(project);
        setTitle("project auto generate");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel.getMainPanel();
    }
}
