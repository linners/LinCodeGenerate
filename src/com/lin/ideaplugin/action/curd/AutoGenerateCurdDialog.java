package com.lin.ideaplugin.action.curd;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.lin.ideaplugin.extension.CodeGenerateSetting;
import com.lin.ideaplugin.extension.SettingConfigure;
import com.lin.ideaplugin.ui.CurdGeneratePanel;
import com.lin.ideaplugin.common.contants.ActionType;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AutoGenerateCurdDialog extends DialogWrapper {

    private final CurdGeneratePanel panel;
    private CodeGenerateSetting settings;

    public AutoGenerateCurdDialog(@Nullable Project project, ActionType actionType) {
        super(project);
        settings =  ServiceManager.getService(project, CodeGenerateSetting.class);
        SettingConfigure settingConfigure = settings.getSettingConfigure();
        panel = new CurdGeneratePanel(settings, project, actionType);
        setTitle("project auto generate");
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
