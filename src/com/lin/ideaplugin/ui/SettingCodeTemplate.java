package com.lin.ideaplugin.ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.lin.ideaplugin.extension.CodeGenerateSetting;
import com.lin.ideaplugin.extension.SettingConfigure;

import javax.swing.*;
import java.awt.*;

public class SettingCodeTemplate {

    private JPanel mainPanel;

    private JPanel bottomPanel;
    private Editor editor;

    public SettingCodeTemplate(CodeGenerateSetting setting, String template) {
        EditorFactory factory = EditorFactory.getInstance();
        Document velocityTemplate = factory.createDocument(template);
        editor = factory.createEditor(velocityTemplate, null, FileTypeManager.getInstance()
                .getFileTypeByExtension("vm"), false);
        GridConstraints constraints = new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(300, 400), null, 0, true);
        bottomPanel.add(editor.getComponent(), constraints);
    }

    public String getTemplate() {
        return editor.getDocument().getText();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
