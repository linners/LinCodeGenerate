package com.lin.ideaplugin.action.curd;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.lin.ideaplugin.extension.CodeGenerateSetting;
import com.lin.ideaplugin.extension.DatasourceSetting;
import com.lin.ideaplugin.extension.SettingConfigure;
import com.lin.ideaplugin.ui.CurdGeneratePanel;
import com.lin.ideaplugin.common.contants.ActionType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AutoGenerateCurdDialog extends DialogWrapper {

    private final CurdGeneratePanel panel;
    private CodeGenerateSetting settings;
    private DatasourceSetting datasourceSetting;

    public AutoGenerateCurdDialog(@Nullable Project project, ActionType actionType) {
        super(project);
        settings =  ServiceManager.getService(project, CodeGenerateSetting.class);
        datasourceSetting =  ServiceManager.getService(DatasourceSetting.class);
        panel = new CurdGeneratePanel(settings, datasourceSetting, project, actionType);
        setTitle("curd auto generate");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel.getMainPanel();
    }

    /**
     * 自动生成curd代码
     */
    public void autoGenerateCurd() {
        panel.autoGenerateCurd();
    }
}
