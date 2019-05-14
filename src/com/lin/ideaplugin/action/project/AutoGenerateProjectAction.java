package com.lin.ideaplugin.action.project;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;

public class AutoGenerateProjectAction extends AnAction implements DumbAware {

    public AutoGenerateProjectAction() {}

    public AutoGenerateProjectAction(String actionText) {
        super(actionText);
    }

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        Project project = actionEvent.getProject();
        AutoGenerateProjectDialog dialog = new AutoGenerateProjectDialog(project);
        dialog.show();
//        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
//            dialog.autoGenerateProject();
//        }
    }
}
