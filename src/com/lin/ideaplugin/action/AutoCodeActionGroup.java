package com.lin.ideaplugin.action;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.lin.ideaplugin.action.curd.CodeAction;
import com.lin.ideaplugin.action.project.AutoGenerateProjectAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutoCodeActionGroup extends ActionGroup {

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
        return new AnAction[]{
            new AutoGenerateProjectAction("auto generate project"),
            new CodeAction("auto generate curd")
        };
    }
}
