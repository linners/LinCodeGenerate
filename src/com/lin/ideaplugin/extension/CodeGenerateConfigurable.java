package com.lin.ideaplugin.extension;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.lin.ideaplugin.ui.SettingConfigurePanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CodeGenerateConfigurable implements SearchableConfigurable {

    private CodeGenerateSetting settings;
    private SettingConfigurePanel configurePanel;
    private Project project;

    public CodeGenerateConfigurable() {
        final Project project = CommonDataKeys.PROJECT.getData(DataManager.getInstance().getDataContext());
        this.project = project;
        this.settings = ServiceManager.getService(CodeGenerateSetting.class);
    }

    @NotNull
    @Override
    public String getId() {
        return "plugins.LinCodeGenerate";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "LinCodeGenerate";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if(configurePanel==null) {
            configurePanel = new SettingConfigurePanel(settings, project);
        }
        return configurePanel.getMainPanel();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        SettingConfigure settingConfigure = configurePanel.getSettingConfigure();
        settings.setSettingConfigure(settingConfigure);
    }

    @Override
    public void reset() {

    }
}
