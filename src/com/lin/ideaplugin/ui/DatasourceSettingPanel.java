package com.lin.ideaplugin.ui;

import com.intellij.ui.components.JBTabbedPane;
import com.intellij.uiDesigner.core.GridConstraints;
import com.lin.ideaplugin.extension.DatasourceSetting;
import com.lin.ideaplugin.extension.MyDatasourceInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatasourceSettingPanel {

    private JPanel mainPanel;
    private JButton addDatasourceBtn;
    private JPanel bottomPanel;
    private JBTabbedPane tabbedPanel;

    private int count = 0;
    private List<DatasourceTemplate> datasourceTemplateList;
    private Map<String, MyDatasourceInfo> myDatasourceInfoMap;

    private DatasourceSettingPanel datasourceSettingPanel;

    public DatasourceSettingPanel(DatasourceSetting datasourceSetting) {
        datasourceSettingPanel = this;
        datasourceTemplateList = new ArrayList<>();
        // Map<host_port, datasourceInfo>
        myDatasourceInfoMap = datasourceSetting.getMyDatasourceInfoMap();
        // 初始化tab组件
        tabbedPanel = new JBTabbedPane();
        if(myDatasourceInfoMap!=null && myDatasourceInfoMap.size()>0){
            myDatasourceInfoMap.forEach((dsName, dsInfo) -> {
                count++;
                DatasourceTemplate template = new DatasourceTemplate(dsInfo, this);
                tabbedPanel.addTab("数据源_"+ count, template.getMainPanel());
                datasourceTemplateList.add(template);
            });
        }
        GridConstraints constraints = new GridConstraints();
        constraints.setRow(0);
        constraints.setFill(GridConstraints.FILL_BOTH);
        bottomPanel.add(tabbedPanel, constraints);
        // 初始化按钮
        initBtn();
    }

    private void initBtn() {
        // 添加新数据
        addDatasourceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                DatasourceTemplate template = new DatasourceTemplate(datasourceSettingPanel);
                tabbedPanel.addTab("数据源_"+ count, template.getMainPanel());
                tabbedPanel.setSelectedIndex(tabbedPanel.getTabCount() - 1);
                datasourceTemplateList.add(template);
            }
        });
    }

    /**
     * 删除数据源模板
     * @param template      数据源模板
     */
    public void delDatasource(DatasourceTemplate template) {
        datasourceTemplateList.remove(template);
        myDatasourceInfoMap.remove(template.getHost() + "_" + template.getPort());
        resetTabPane();
    }

    private void resetTabPane() {
        tabbedPanel.removeAll();
        if(myDatasourceInfoMap!=null && myDatasourceInfoMap.size()>0){
            myDatasourceInfoMap.forEach((dsName, dsInfo) -> {
                count++;
                DatasourceTemplate template = new DatasourceTemplate(dsInfo, this);
                tabbedPanel.addTab("数据源_"+ count, template.getMainPanel());
                datasourceTemplateList.add(template);
            });
        }
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Map<String, MyDatasourceInfo> getSettingConfigure() {
        Map<String, MyDatasourceInfo> result = new HashMap<>();
        for(DatasourceTemplate template : datasourceTemplateList){
            String key = template.getHost()+"_"+ template.getPort();
            MyDatasourceInfo datasourceInfo = new MyDatasourceInfo(template.getDatasourceName(), template.getHost(),
                    template.getPort(), template.getUserName(), template.getPassword());
            result.put(key ,datasourceInfo);
        }
        return result;
    }
}
