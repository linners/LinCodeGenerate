package com.lin.ideaplugin.action.curd;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.lin.ideaplugin.contants.ActionType;

public class CodeAction extends AnAction {

    private ActionType actionType;

    public CodeAction(String actionText, ActionType myActionType) {
        super(actionText);
        actionType = myActionType;
    }

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        Project project = actionEvent.getProject();
        DataContext dataContext = actionEvent.getDataContext();
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(dataContext);
        String path = file.getPath();
        AutoGenerateCurdDialog dialog = new AutoGenerateCurdDialog(project, path, actionType);
        dialog.show();
        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
//            commitPanel.setCommitMessage(dialog.getCommitMessage());
            System.out.println(112123);
        }
    }
}
