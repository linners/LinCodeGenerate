package com.lin.ideaplugin.extension;

public class MyDatasourceInfo {
    private String datasourceName;
    private String host;
    private String port;
    private String userName;
    private String password;

    public MyDatasourceInfo(){}

    public MyDatasourceInfo(String datasourceName, String host, String port, String userName, String password) {
        this.datasourceName = datasourceName;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getDatasourceName() {
        return datasourceName;
    }
}
