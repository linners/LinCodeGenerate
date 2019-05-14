package com.lin.ideaplugin.ui;

import com.intellij.openapi.ui.Messages;
import com.lin.ideaplugin.common.utils.JdbcUtil;
import com.lin.ideaplugin.extension.MyDatasourceInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DatasourceTemplate {

    private JPanel mainPanel;
    private JTextField datasourceName;
    private JTextField host;
    private JTextField port;
    private JTextField userName;
    private JPasswordField password;
    private JButton testConBtn;
    private JLabel msg;
    private JButton delConBtn;      // 删除连接按钮

    private DatasourceTemplate datasourceTemplate;

    public DatasourceTemplate(DatasourceSettingPanel datasourceSettingPanel){
        datasourceTemplate = this;
        initBtn(datasourceSettingPanel);
    }

    public DatasourceTemplate(MyDatasourceInfo datasourceInfo, DatasourceSettingPanel datasourceSettingPanel) {
        datasourceTemplate = this;
        datasourceName.setText(datasourceInfo.getDatasourceName());
        host.setText(datasourceInfo.getHost());
        port.setText(datasourceInfo.getPort());
        userName.setText(datasourceInfo.getUserName());
        password.setText(datasourceInfo.getPassword());
        initBtn(datasourceSettingPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getDatasourceName(){
        return datasourceName.getText();
    }
    public String getHost(){
        return host.getText();
    }
    public String getPort(){
        return port.getText();
    }
    public String getUserName(){
        return userName.getText();
    }
    public String getPassword(){
        return password.getText();
    }

    public void initBtn(DatasourceSettingPanel datasourceSettingPanel){
        if(port.getText()==null || "".equals(port.getText())){
            port.setText("3306");
        }
        testConBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                msg.setText("测试中，请稍等...");
                try{
                    JdbcUtil instance = JdbcUtil.getInstance(host.getText(), port.getText(), userName.getText(), password.getText());
                    List<Object> objects = instance.excuteQuery("select 'x'", null);
                    if(objects!=null){
                        msg.setText("测试成功！");
                    }
                }catch (Exception e1){
                    e1.printStackTrace();
                    msg.setText("测试失败！");
                }
            }
        });
        delConBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int result = Messages.showYesNoDialog("删除数据源?", "删除", null);
                    if (result == Messages.OK) {
                        datasourceSettingPanel.delDatasource(datasourceTemplate);
                        msg.setText("删除成功！");
                    }
                }catch (Exception e1){
                    e1.printStackTrace();
                    msg.setText("删除失败！");
                }
            }
        });
    }

}
