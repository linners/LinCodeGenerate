package com.lin.ideaplugin.extension;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.lin.ideaplugin.common.contants.VelocityFileType;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@State(name = "LinCodeGenerateSetting", storages = {@Storage(value = "lin-code-generate-setting.xml")})
public class CodeGenerateSetting implements PersistentStateComponent<CodeGenerateSetting> {

    private static final Logger LOGGER = Logger.getInstance(CodeGenerateSetting.class);

    private SettingConfigure settingConfigure;

    public CodeGenerateSetting() {
        loadDefaultSettings();
    }

    public void loadDefaultSettings() {
        settingConfigure = new SettingConfigure();
        Map<String, String> templateMap = new HashMap<>();
        String mapperTemplate = getTemplateContent("/template/mapper/TemplateMapper.vm");
        templateMap.put(VelocityFileType.MAPPER.getType(), mapperTemplate);
        String xmlTemplate = getTemplateContent("/template/xml/TemplateMapperXml.vm");
        templateMap.put(VelocityFileType.MAPPER_XML.getType(), xmlTemplate);
        String xmlExtendTemplate = getTemplateContent("/template/xml/TemplateMapperXmlExtend.vm");
        templateMap.put(VelocityFileType.MAPPER_XML_EXTEND.getType(), xmlExtendTemplate);
        String entityTemplate = getTemplateContent("/template/entity/TemplateEntity.vm");
        templateMap.put(VelocityFileType.ENTITY.getType(), entityTemplate);
        String paramTemplate = getTemplateContent("/template/param/TemplateParam.vm");
        templateMap.put(VelocityFileType.PARAM.getType(), paramTemplate);
        String dtoTemplate = getTemplateContent("/template/param/TemplateDto.vm");
        templateMap.put(VelocityFileType.DTO.getType(), dtoTemplate);
        String serviceTemplate = getTemplateContent("/template/service/TemplateService.vm");
        templateMap.put(VelocityFileType.SERVICE.getType(), serviceTemplate);
        String serviceImplTemplate = getTemplateContent("/template/service/TemplateServiceImpl.vm");
        templateMap.put(VelocityFileType.SERVICE_IMPL.getType(), serviceImplTemplate);
        String apiTemplate = getTemplateContent("/template/api/TemplateApi.vm");
        templateMap.put(VelocityFileType.API.getType(), apiTemplate);
        String apiImplTemplate = getTemplateContent("/template/api/TemplateApiImpl.vm");
        templateMap.put(VelocityFileType.API_IMPL.getType(), apiImplTemplate);
        String controllerTemplate = getTemplateContent("/template/controller/TemplateController.vm");
        templateMap.put(VelocityFileType.CONTROLLER.getType(), controllerTemplate);
        String vueListTemplate = getTemplateContent("/template/vuepage/vueListTemplate.vm");
        templateMap.put(VelocityFileType.VUE_LIST.getType(), vueListTemplate);
        settingConfigure.setCodeTemplateMap(templateMap);
    }

    /**
     * 获得模板内容
     * @param templatePath
     * @return
     */
    private String getTemplateContent(String templatePath) {
        InputStream inputStream = null;
        StringWriter writer = new StringWriter();
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(templatePath);
            IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writer.toString();
    }

    @Nullable
    @Override
    public CodeGenerateSetting getState() {
        if (settingConfigure == null) {
            loadDefaultSettings();
        }
        return this;
    }

    @Override
    public void loadState(CodeGenerateSetting codeGenerateSetting) {
        XmlSerializerUtil.copyBean(codeGenerateSetting, this);
    }

    public SettingConfigure getSettingConfigure() {
        return settingConfigure;
    }

    public void setSettingConfigure(SettingConfigure settingConfigure) {
        this.settingConfigure = settingConfigure;
    }
}
