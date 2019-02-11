package com.lin.ideaplugin.common.dto;

import com.lin.ideaplugin.common.utils.VelocityUtils;
import org.apache.velocity.VelocityContext;

public class GenerateProjectExtend {

    // git地址
    private String gitRepository;
    private String branchName;
    // git工程，包名
    private String oldBasePackage;
    // maven相关
    private String rootGroupId;
    private String rootArtifactId;
    private String version;
    // 新工程相关
    private String projectName;
    private String basePackage;

    // git模板工程全路径
    private String gitProjectPath;
    // velocityUtils实例
    private VelocityUtils instance;
    // velocityContext参数
    private VelocityContext context;
    // 新生成的工程存放目录
    private String generatedProjectPath;

    public String getGitProjectPath() {
        return gitProjectPath;
    }

    public void setGitProjectPath(String gitProjectPath) {
        this.gitProjectPath = gitProjectPath;
    }

    public VelocityUtils getInstance() {
        return instance;
    }

    public void setInstance(VelocityUtils instance) {
        this.instance = instance;
    }

    public VelocityContext getContext() {
        return context;
    }

    public void setContext(VelocityContext context) {
        this.context = context;
    }

    public String getGeneratedProjectPath() {
        return generatedProjectPath;
    }

    public void setGeneratedProjectPath(String generatedProjectPath) {
        this.generatedProjectPath = generatedProjectPath;
    }

    public String getGitRepository() {
        return gitRepository;
    }

    public void setGitRepository(String gitRepository) {
        this.gitRepository = gitRepository;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getOldBasePackage() {
        return oldBasePackage;
    }

    public void setOldBasePackage(String oldBasePackage) {
        this.oldBasePackage = oldBasePackage;
    }

    public String getRootGroupId() {
        return rootGroupId;
    }

    public void setRootGroupId(String rootGroupId) {
        this.rootGroupId = rootGroupId;
    }

    public String getRootArtifactId() {
        return rootArtifactId;
    }

    public void setRootArtifactId(String rootArtifactId) {
        this.rootArtifactId = rootArtifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
