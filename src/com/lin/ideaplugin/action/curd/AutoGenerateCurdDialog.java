package com.lin.ideaplugin.action.curd;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.lin.ideaplugin.action.ui.CurdGeneratePanel;
import com.lin.ideaplugin.contants.ActionType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AutoGenerateCurdDialog extends DialogWrapper {

    private final CurdGeneratePanel panel;

    public AutoGenerateCurdDialog(@Nullable Project project, String clickPath, ActionType actionType) {
        super(project);
        panel = new CurdGeneratePanel(project, clickPath, actionType);
        setTitle("project auto generate");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel.getMainPanel();
    }
}
