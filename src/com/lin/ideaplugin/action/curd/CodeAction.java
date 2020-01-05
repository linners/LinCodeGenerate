package com.lin.ideaplugin.action.curd;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.lin.ideaplugin.common.contants.ActionType;

public class CodeAction extends AnAction  implements DumbAware {

    private ActionType actionType;

    public CodeAction() {}
    public CodeAction(String actionText) {
        super(actionText);
    }

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        Project project = actionEvent.getProject();
//        VirtualFile projectFile = project.getBaseDir();
//        PsiFile  selectedFile = actionEvent.getData(CommonDataKeys.PSI_FILE);
//        VirtualFile selectedFile = actionEvent.getData(PlatformDataKeys.VIRTUAL_FILE);
//        LocalFileSystem.getInstance().findFileByIoFile();
//        DataContext dataContext = actionEvent.getDataContext();
//        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(dataContext);
        AutoGenerateCurdDialog dialog = new AutoGenerateCurdDialog(project, actionType);
        dialog.show();
        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            dialog.autoGenerateCurd();
//            projectFile.refresh(true, true);
        }
    }

}
