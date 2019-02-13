package com.lin.ideaplugin.ui;

import com.google.common.io.Files;
import com.intellij.openapi.progress.BackgroundTaskQueue;
import com.intellij.openapi.project.Project;
import com.lin.ideaplugin.common.contants.ActionType;
import com.lin.ideaplugin.common.contants.VelocityFileType;
import com.lin.ideaplugin.common.dto.GenerateCurdParam;
import com.lin.ideaplugin.common.dto.TableColumnInfo;
import com.lin.ideaplugin.common.utils.JdbcUtil;
import com.lin.ideaplugin.common.utils.StringUtil;
import com.lin.ideaplugin.common.utils.VelocityUtils;
import com.lin.ideaplugin.extension.CodeGenerateSetting;
import com.lin.ideaplugin.extension.DatasourceSetting;
import com.lin.ideaplugin.extension.MyDatasourceInfo;
import com.lin.ideaplugin.extension.SettingConfigure;
import com.lin.ideaplugin.task.GenerateCodeBackgroundTask;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.jetbrains.annotations.SystemIndependent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurdGeneratePanel {

    private JPanel mainPanel;

    private JPanel topPanel;
    private JComboBox dataSources;
    private JComboBox dataBases;
    private JComboBox tables;

    private JPanel bottomPanel;
    private JCheckBox mapperCheckBox;
    private JCheckBox xmlCheckBox;
    private JCheckBox entityCheckBox;
    private JCheckBox paramCheckBox;
    private JCheckBox serviceCheckBox;
    private JCheckBox apiCheckBox;
    private JCheckBox dtoCheckBox;
    private JCheckBox controllerCheckBox;
    private JCheckBox vueListCheckBox;
    private JCheckBox vueNewCheckBox;
    private JCheckBox vueDetailCheckBox;
    private JTextField entityName;

    private String tableName;
    private List<TableColumnInfo> columns;

    private Project project;
    private String path;
    private ActionType actionType;

    private CodeGenerateSetting settings;
    private DatasourceSetting datasourceSetting;

    private MyDatasourceInfo curDatasourceInfo;

    public CurdGeneratePanel(CodeGenerateSetting mysettings, DatasourceSetting myDatasourceSetting, Project myproject, ActionType myActionType) {
        settings = mysettings;
        datasourceSetting = myDatasourceSetting;
        project = myproject;
        actionType = myActionType;
        initData();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initData() {
        mainPanel.setPreferredSize(new Dimension(550, 320));
        // 查询所有的数据源
        List<String> allDatasources = getAllDatasources();
        dataSources.removeAllItems();
        for(String datasourceKey : allDatasources){
            dataSources.addItem(datasourceKey);
        }
        // 当前数据源
        curDatasourceInfo = datasourceSetting.getMyDatasourceInfoMap().get(allDatasources.get(0));
        // 查询所有数据库
        List<String> alldbNames = getAllDatabasesByDsKey();
        for(String tableName : alldbNames) {
            dataBases.addItem(tableName);
        }
        // 查询所有数据库表
        List<String> allTableNames = getAllTablesByDbName(alldbNames.get(0));
        for(String tableName : allTableNames) {
            tables.addItem(tableName);
        }
        // 数据源变化下拉事件
        dataSources.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    Object item = e.getItem();
                    // 当前数据源
                    String datasourceKey = item.toString();
                    curDatasourceInfo = datasourceSetting.getMyDatasourceInfoMap().get(datasourceKey);
                    dataBases.removeAllItems();
                    // 查询所有的数据库
                    List<String> alldbNames = getAllDatabasesByDsKey();
                    for(String tableName : alldbNames) {
                        dataBases.addItem(tableName);
                    }
                }
            }
        });
        // 数据库变化下拉事件
        dataBases.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    Object item = e.getItem();
                    tables.removeAllItems();
                    // 查询所有的数据库表
                    List<String> allTables = getAllTablesByDbName(item.toString());
                    for(String tableName : allTables) {
                        tables.addItem(tableName);
                    }
                }
            }
        });
        // 数据库表变化监听下拉
        tables.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    Object item = e.getItem();
                    tableName = item.toString();
                    entityName.setText("");
                    columns = null;
                    // 查询所有的表字段
                    columns = getAllColumnsByTable(tableName);
                    // 初始化实体类名称
                    entityName.setText(StringUtil.camelName(tableName));
                }
            }
        });
    }

    // 自动生成curd代码
    public void autoGenerateCurd() {
        GenerateCurdParam curdParam = new GenerateCurdParam();
        curdParam.setMapperCheckBox(mapperCheckBox.isSelected());
        curdParam.setXmlCheckBox(xmlCheckBox.isSelected());
        curdParam.setEntityCheckBox(entityCheckBox.isSelected());
        curdParam.setParamCheckBox(paramCheckBox.isSelected());
        curdParam.setServiceCheckBox(serviceCheckBox.isSelected());
        curdParam.setApiCheckBox(apiCheckBox.isSelected());
        curdParam.setDtoCheckBox(dtoCheckBox.isSelected());
        curdParam.setControllerCheckBox(controllerCheckBox.isSelected());
        curdParam.setVueListCheckBox(vueListCheckBox.isSelected());
        curdParam.setVueNewCheckBox(vueNewCheckBox.isSelected());
        curdParam.setVueDetailCheckBox(vueDetailCheckBox.isSelected());
        curdParam.setSettings(settings);
        curdParam.setTableName(tableName);
        curdParam.setColumns(columns);
        curdParam.setEntityName(entityName.getText());
        // 状态任务
        new BackgroundTaskQueue(project, "task name").run(new GenerateCodeBackgroundTask(project, "generate code ...", curdParam));
    }

    /**
     * 获得所有的数据源
     * @return
     */
    public List<String> getAllDatasources() {
        List<String> datasourceList = new ArrayList<>();
        Map<String, MyDatasourceInfo> myDatasourceInfoMap = datasourceSetting.getMyDatasourceInfoMap();
        if(myDatasourceInfoMap!=null && myDatasourceInfoMap.size()>0){
            myDatasourceInfoMap.forEach((key, datasourceInfo)->{
                datasourceList.add(datasourceInfo.getHost()+"_"+datasourceInfo.getPort());
            });
        }
        return datasourceList;
    }

    /**
     * 通过数据源，获得所有的数据库
     * @param datasourceKey  形式： host_port
     * @return
     */
    public List<String> getAllDatabasesByDsKey() {
        List<String> dbList = new ArrayList<>();
        JdbcUtil jdbcUtil = JdbcUtil.getInstance(curDatasourceInfo.getHost(), curDatasourceInfo.getPort(), curDatasourceInfo.getUserName(), curDatasourceInfo.getPassword());
        List<Object> result = jdbcUtil.excuteQuery("select schema_name as db_name from information_schema.schemata", null);
        if(result!=null && result.size()>0) {
            for(Object object : result) {
                Map<String, String> map = (Map<String, String>) object;
                dbList.add(map.get("db_name"));
            }
        }
        return dbList;
    }

    public List<String> getAllTablesByDbName(String dbName) {
        List<String> tableList = new ArrayList<>();
        JdbcUtil jdbcUtil = JdbcUtil.getInstance(curDatasourceInfo.getHost(), curDatasourceInfo.getPort(), curDatasourceInfo.getUserName(), curDatasourceInfo.getPassword());
        List<Object> result = jdbcUtil.excuteQuery("select table_schema as own_db_name, table_name,table_comment from information_schema.tables where table_schema = ?", new Object[]{dbName});
        if(result!=null && result.size()>0) {
            for(Object object : result) {
                Map<String, String> map = (Map<String, String>) object;
                tableList.add(map.get("table_name"));
            }
        }
        return tableList;
    }

    public List<TableColumnInfo> getAllColumnsByTable(String tableName) {
        List<TableColumnInfo> columnList = new ArrayList<>();
        JdbcUtil jdbcUtil = JdbcUtil.getInstance(curDatasourceInfo.getHost(), curDatasourceInfo.getPort(), curDatasourceInfo.getUserName(), curDatasourceInfo.getPassword());
        List<Object> result = jdbcUtil.excuteQuery("select distinct column_name, table_name as own_table_name, data_type, column_comment from information_schema.columns where table_name = ?", new Object[]{tableName});
        if(result!=null && result.size()>0) {
            for(Object object : result) {
                Map<String, String> map = (Map<String, String>) object;
                String columnName = map.get("column_name");
                String ownTableName = map.get("own_table_name");
                String dataType = map.get("data_type");
                String columnComment = map.get("column_comment");
                TableColumnInfo tableColumnInfo = new TableColumnInfo();
                tableColumnInfo.setOwnTableName(ownTableName);
                tableColumnInfo.setColumnName(columnName);
                tableColumnInfo.setDataType(dataType);
                tableColumnInfo.setColumnComment(columnComment);
                tableColumnInfo.setFieldName(StringUtil.camelName(columnName));
                tableColumnInfo.setFieldType(StringUtil.sqlType2JavaType(dataType));
                columnList.add(tableColumnInfo);
            }
        }
        return columnList;
    }
}
