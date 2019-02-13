package com.lin.ideaplugin.ui;

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

    public DatasourceTemplate(){
        initBtn();
    }

    public DatasourceTemplate(MyDatasourceInfo datasourceInfo) {
        datasourceName.setText(datasourceInfo.getDatasourceName());
        host.setText(datasourceInfo.getHost());
        port.setText(datasourceInfo.getPort());
        userName.setText(datasourceInfo.getUserName());
        password.setText(datasourceInfo.getPassword());
        initBtn();
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

    public void initBtn(){
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
    }

}
