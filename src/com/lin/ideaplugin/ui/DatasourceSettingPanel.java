package com.lin.ideaplugin.ui;

import com.intellij.ui.components.JBTabbedPane;
import com.intellij.uiDesigner.core.GridConstraints;
import com.lin.ideaplugin.extension.MyDatasourceInfo;

import javax.swing.*;

public class DatasourceSettingPanel {

    private JPanel mainPanel;
    private JButton addDatasourceBtn;
    private JPanel bottomPanel;

    private JBTabbedPane tabbedPanel;

    public DatasourceSettingPanel() {
        // 初始化按钮
        initBtn();
        DatasourceTemplate datasourcePanel = new DatasourceTemplate();
        tabbedPanel = new JBTabbedPane();
        tabbedPanel.addTab("数据源1", datasourcePanel.getMainPanel());
        GridConstraints constraints = new GridConstraints();
        constraints.setRow(0);
        constraints.setFill(GridConstraints.FILL_BOTH);
        bottomPanel.add(tabbedPanel, constraints);
    }

    private void initBtn() {

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public MyDatasourceInfo getSettingConfigure() {

        return null;
    }
}
