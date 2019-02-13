package com.lin.ideaplugin.extension;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.lin.ideaplugin.ui.DatasourceSettingPanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class DatasourceConfigurable implements SearchableConfigurable {

    private DatasourceSetting datasourceSetting;
    private DatasourceSettingPanel configurePanel;

    public DatasourceConfigurable() {
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
            configurePanel = new DatasourceSettingPanel(datasourceSetting);
        }
        return configurePanel.getMainPanel();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        Map<String, MyDatasourceInfo> myDatasourceInfoList = configurePanel.getSettingConfigure();
        datasourceSetting.setMyDatasourceInfoMap(myDatasourceInfoList);
    }

    @Override
    public void reset() {

    }
}
