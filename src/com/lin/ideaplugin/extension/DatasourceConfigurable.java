package com.lin.ideaplugin.extension;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.lin.ideaplugin.ui.DatasourceSettingPanel;
import com.lin.ideaplugin.ui.SettingConfigurePanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DatasourceConfigurable implements SearchableConfigurable {

    private DatasourceSetting datasourceSetting;
    private DatasourceSettingPanel configurePanel;

    public DatasourceConfigurable() {
        final Project project = CommonDataKeys.PROJECT.getData(DataManager.getInstance().getDataContext());
        this.datasourceSetting = ServiceManager.getService(DatasourceSetting.class);
    }

    @NotNull
    @Override
    public String getId() {
        return "plugins.LinDatasource";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "LinDatasource";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if(configurePanel==null) {
            configurePanel = new DatasourceSettingPanel();
        }
        return configurePanel.getMainPanel();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        MyDatasourceInfo myDatasourceInfo = configurePanel.getSettingConfigure();
        datasourceSetting.setMyDatasourceInfo(myDatasourceInfo);
    }

    @Override
    public void reset() {

    }
}
