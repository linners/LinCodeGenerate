package com.lin.ideaplugin.extension;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@State(name = "LinDatasourceSetting", storages = {@Storage(value = "$APP_CONFIG$/lin-datasource-setting.xml")})
public class DatasourceSetting implements PersistentStateComponent<DatasourceSetting> {

    private static final Logger LOGGER = Logger.getInstance(DatasourceSetting.class);

    private Map<String, MyDatasourceInfo> myDatasourceInfoMap;

    public void loadDefaultSettings() {
        myDatasourceInfoMap = new HashMap<>();
    }

    @Nullable
    @Override
    public DatasourceSetting getState() {
        if (myDatasourceInfoMap == null) {
            loadDefaultSettings();
        }
        return this;
    }

    @Override
    public void loadState(DatasourceSetting datasourceSetting) {
        XmlSerializerUtil.copyBean(datasourceSetting, this);
    }

    public Map<String, MyDatasourceInfo> getMyDatasourceInfoMap() {
        return myDatasourceInfoMap;
    }

    public void setMyDatasourceInfoMap(Map<String, MyDatasourceInfo> myDatasourceInfoMap) {
        this.myDatasourceInfoMap = myDatasourceInfoMap;
    }
}
