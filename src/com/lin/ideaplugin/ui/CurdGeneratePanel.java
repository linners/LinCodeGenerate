package com.lin.ideaplugin.ui;

import com.intellij.openapi.progress.BackgroundTaskQueue;
import com.intellij.openapi.project.Project;
import com.intellij.uiDesigner.core.GridConstraints;
import com.lin.ideaplugin.common.contants.ActionType;
import com.lin.ideaplugin.common.dto.CurdTableInfo;
import com.lin.ideaplugin.common.dto.GenerateCurdParam;
import com.lin.ideaplugin.common.dto.TableColumnInfo;
import com.lin.ideaplugin.common.utils.JdbcUtil;
import com.lin.ideaplugin.common.utils.StringUtil;
import com.lin.ideaplugin.extension.CodeGenerateSetting;
import com.lin.ideaplugin.extension.DatasourceSetting;
import com.lin.ideaplugin.extension.MyDatasourceInfo;
import com.lin.ideaplugin.task.GenerateCodeBackgroundTask;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class CurdGeneratePanel {

    private JPanel mainPanel;

    private JPanel topPanel;
    private JComboBox dataSources;
    private JComboBox dataBases;
    private JComboBox tables = new JComboBox();

    // 生成模块相关
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

    // table相关
    private JPanel tableCenterPanel;
    private JPanel btnPanel;
    private JButton addBtn;
    private JButton delBtn;
    private JPanel tablePanel;

    private Project project;
    private String path;
    private ActionType actionType;

    private CodeGenerateSetting settings;
    private DatasourceSetting datasourceSetting;

    private MyDatasourceInfo curDatasourceInfo;
    private String curDatabaseName;

    private Table_Model model;
    private JTable table;

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

        mainPanel.setPreferredSize(new Dimension(550, 450));
        // 查询所有的数据源
        List<String> allDatasources = getAllDatasources();
        dataSources.removeAllItems();
        for (String datasourceKey : allDatasources) {
            dataSources.addItem(datasourceKey);
        }
        // 当前数据源
        curDatasourceInfo = datasourceSetting.getMyDatasourceInfoMap().get(allDatasources.get(0));
        // 查询所有数据库
        List<String> alldbNames = getAllDatabasesByDsKey();
        for (String tableName : alldbNames) {
            dataBases.addItem(tableName);
        }
        // 查询数据库下所有的表
        List<String> allTablesByDbName = getAllTablesByDbName(alldbNames.get(0));
        for(String tableName : allTablesByDbName){
            tables.addItem(tableName);
        }
        // 数据源变化下拉事件
        dataSources.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    // 当前数据源
                    String datasourceKey = item.toString();
                    curDatasourceInfo = datasourceSetting.getMyDatasourceInfoMap().get(datasourceKey);
                    dataBases.removeAllItems();
                    // 查询所有的数据库
                    List<String> alldbNames = getAllDatabasesByDsKey();
                    for (String tableName : alldbNames) {
                        dataBases.addItem(tableName);
                    }
                }
            }
        });
        // 数据库变化下拉事件
        dataBases.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    // 获得所有的表
                    curDatabaseName = item.toString();
                    tables.removeAllItems();
                    List<String> tableList = getAllTablesByDbName(curDatabaseName);
                    for(String tableName : tableList){
                        tables.addItem(tableName);
                    }
                }
            }
        });
        // 初始化table
        initTable();
        // 数据库表变化监听下拉
//        tables.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if(e.getStateChange() == ItemEvent.SELECTED){
//                    Object item = e.getItem();
//                    tableName = item.toString();
//                    entityName.setText("");
//                    columns = null;
//                    // 查询所有的表字段
//                    columns = getAllColumnsByTable(tableName);
//                    // 初始化实体类名称
//                    entityName.setText(StringUtil.camelName(tableName));
//                }
//            }
//        });
    }

    private void initTable() {
        model = new Table_Model(20);
        table = new JTable(model);
        table.setRowHeight(25);
        //得到选中的行号
        TableColumnModel colmodel = table.getColumnModel();
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                System.out.println(e.getColumn());
            }
        });
        TableColumn column = table.getColumnModel().getColumn(0);
        TableColumn column1 = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(40);
        column.setMaxWidth(40);
        column.setMinWidth(40);
        column1.setCellEditor(new DefaultCellEditor(tables));
        JScrollPane scroll = new JScrollPane(table);
        GridConstraints constraints = new GridConstraints();
        constraints.setRow(0);
        constraints.setFill(GridConstraints.FILL_BOTH);
        tablePanel.add(scroll, constraints);

        // 添加按钮事件
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addData();
            }
        });
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                removeData(row);
            }
        });
    }

    private void addData() {
        Object[] str = {"", ""};
        model.addRow(str);
        table.updateUI();
    }

    /**
     * 清除选中行
     *
     * @param row
     */
    private void removeData(int row) {
        model.removeRows(row);
        table.updateUI();
    }

    /**
     * 模型
     */
    class Table_Model extends AbstractTableModel {
        private Vector vector = null;
        private String[] titleName = {"序号", "数据库表", "实体类名称"};

        public Table_Model() {
            vector = new Vector();
        }

        public Table_Model(int count) {
            vector = new Vector(count);
        }

        public void addRow(Object[] str) {
            Vector v = new Vector(titleName.length);
            v.add(0, new Integer(vector.size()));
            for(int i = 1; i <= str.length; i++){
                v.add(i, str[i-1]);
            }
            vector.add(v);
        }

        /**
         * 删除选中行
         *
         * @param row
         */
        public void removeRows(int row) {
            vector.remove(row);
        }

        /**
         * 是否为可编辑
         */
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return false;
            }
            return true;
        }

        /**
         * 使修改的内容生效
         */
        public void setValueAt(Object value, int row, int col) {
            try{
                ((Vector) vector.get(row)).remove(col);
                ((Vector) vector.get(row)).add(col, value);
                this.fireTableCellUpdated(row, col);//通知所有侦听器，已更新 [row, column] 处的单元格值。
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public String getColumnName(int col) {
            return titleName[col];
        }

        public int getColumnCount() {
            return titleName.length;
        }

        public int getRowCount() {
            return vector.size();
        }

        /**
         * 返回 col 和 row 位置的单元格值。
         */
        public Object getValueAt(int row, int col) {
            return ((Vector) vector.get(row)).get(col);
        }

        /**
         * 针对列中所有的单元格值，返回最具体的超类
         * 返回数据类型
         */
        public Class getColumnClass(int col) {
            return getValueAt(0, col).getClass();
        }
    }


    // 自动生成curd代码
    public void autoGenerateCurd() {
        // 查询所有的数据库表和字段
        Map<String, List<TableColumnInfo>> tableColumnMap = getAllColumnsByDbName(curDatabaseName);
        // 获得table输入的值
        if (table.isEditing()) {
            TableCellEditor cellEditor = table.getCellEditor();
            if (cellEditor != null) {
                cellEditor.stopCellEditing();
            }
        }
        TableModel columnModel = table.getModel();
        int rowCount = columnModel.getRowCount();
        List<CurdTableInfo> tableInfoList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            CurdTableInfo curdTableInfo = new CurdTableInfo();
            String tableName = columnModel.getValueAt(i, 1).toString();
            String entityName = columnModel.getValueAt(i, 2).toString();
            curdTableInfo.setTableName(tableName);
            curdTableInfo.setEntityName(entityName);
            curdTableInfo.setColumns(tableColumnMap.get(tableName));
            tableInfoList.add(curdTableInfo);
        }
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
        curdParam.setTableInfos(tableInfoList);
        curdParam.setSettings(settings);
        // 状态任务
        new BackgroundTaskQueue(project, "task name").run(new GenerateCodeBackgroundTask(project, "generate code ...", curdParam));
    }

    /**
     * 获得所有的数据源
     *
     * @return
     */
    public List<String> getAllDatasources() {
        List<String> datasourceList = new ArrayList<>();
        Map<String, MyDatasourceInfo> myDatasourceInfoMap = datasourceSetting.getMyDatasourceInfoMap();
        if (myDatasourceInfoMap != null && myDatasourceInfoMap.size() > 0) {
            myDatasourceInfoMap.forEach((key, datasourceInfo) -> {
                datasourceList.add(datasourceInfo.getHost() + "_" + datasourceInfo.getPort());
            });
        }
        return datasourceList;
    }

    /**
     * 通过数据源，获得所有的数据库
     *
     * @param datasourceKey 形式： host_port
     * @return
     */
    public List<String> getAllDatabasesByDsKey() {
        List<String> dbList = new ArrayList<>();
        JdbcUtil jdbcUtil = JdbcUtil.getInstance(curDatasourceInfo.getHost(), curDatasourceInfo.getPort(), curDatasourceInfo.getUserName(), curDatasourceInfo.getPassword());
        List<Object> result = jdbcUtil.excuteQuery("select schema_name as db_name from information_schema.schemata", null);
        if (result != null && result.size() > 0) {
            for (Object object : result) {
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
        if (result != null && result.size() > 0) {
            for (Object object : result) {
                Map<String, String> map = (Map<String, String>) object;
                tableList.add(map.get("table_name"));
            }
        }
        return tableList;
    }

    /**
     * 查询某个数据库表的所有字段
     *
     * @param tableName
     * @return
     */
    public List<TableColumnInfo> getAllColumnsByTable(String tableName) {
        List<TableColumnInfo> columnList = new ArrayList<>();
        JdbcUtil jdbcUtil = JdbcUtil.getInstance(curDatasourceInfo.getHost(), curDatasourceInfo.getPort(), curDatasourceInfo.getUserName(), curDatasourceInfo.getPassword());
        List<Object> result = jdbcUtil.excuteQuery("select distinct column_name, table_name as own_table_name, data_type, column_comment from information_schema.columns where table_name = ?", new Object[]{tableName});
        if (result != null && result.size() > 0) {
            for (Object object : result) {
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

    /**
     * 查询某个数据库下的所有表，以及表的所有字段
     *
     * @param dbName
     * @return map<表名称   ,       表字段列表>
     */
    public Map<String, List<TableColumnInfo>> getAllColumnsByDbName(String dbName) {
        Map<String, List<TableColumnInfo>> resultMap = new HashMap<>();
        List<TableColumnInfo> columnList = new ArrayList<>();
        JdbcUtil jdbcUtil = JdbcUtil.getInstance(curDatasourceInfo.getHost(), curDatasourceInfo.getPort(), curDatasourceInfo.getUserName(), curDatasourceInfo.getPassword());
        List<Object> result = jdbcUtil.excuteQuery("select distinct column_name, table_name as own_table_name, data_type, column_comment from information_schema.columns where table_schema = ?", new Object[]{dbName});
        if (result != null && result.size() > 0) {
            for (Object object : result) {
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
                tableColumnInfo.setFieldName(StringUtil.lowerFirst(StringUtil.camelName(columnName)));
                tableColumnInfo.setFieldType(StringUtil.sqlType2JavaType(dataType));
                columnList.add(tableColumnInfo);
            }
            resultMap = columnList.stream().collect(Collectors.groupingBy(TableColumnInfo::getOwnTableName));
        }
        return resultMap;
    }
}
