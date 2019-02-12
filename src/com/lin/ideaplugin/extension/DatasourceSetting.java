package com.lin.ideaplugin.extension;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(name = "LinDatasourceSetting", storages = {@Storage(value = "lin-datasource-setting.xml")})
public class DatasourceSetting implements PersistentStateComponent<DatasourceSetting> {

    private static final Logger LOGGER = Logger.getInstance(DatasourceSetting.class);

    private MyDatasourceInfo myDatasourceInfo;

    public DatasourceSetting() {
        loadDefaultSettings();
    }

    public void loadDefaultSettings() {
        myDatasourceInfo = new MyDatasourceInfo();
    }

    @Nullable
    @Override
    public DatasourceSetting getState() {
        if (myDatasourceInfo == null) {
            loadDefaultSettings();
        }
        return this;
    }

    @Override
    public void loadState(DatasourceSetting datasourceSetting) {
        XmlSerializerUtil.copyBean(datasourceSetting, this);
    }

    public MyDatasourceInfo getMyDatasourceInfo() {
        return myDatasourceInfo;
    }

    public void setMyDatasourceInfo(MyDatasourceInfo myDatasourceInfo) {
        this.myDatasourceInfo = myDatasourceInfo;
    }
}
