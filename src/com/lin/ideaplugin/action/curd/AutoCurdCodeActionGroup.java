package com.lin.ideaplugin.action.curd;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Separator;
import com.lin.ideaplugin.contants.ActionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutoCurdCodeActionGroup extends ActionGroup {

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
        return new AnAction[] {
            new CodeAction("Mapper Code Generate", ActionType.MAPPER),
            new CodeAction("Mapper Entity Code Generate", ActionType.ENTITY),
            new CodeAction("Mapper Param Code Generate", ActionType.PARAM),
            new Separator(),
            new CodeAction("Mapper Xml Code Generate", ActionType.MAPPER_XML),
            new Separator(),
            new CodeAction("Service Code Generate", ActionType.SERVICE),
            new CodeAction("Service Impl Code Generate", ActionType.SERVICE_IMPL),
            new Separator(),
            new CodeAction("Api Code Generate", ActionType.API),
            new CodeAction("Api Impl Code Generate", ActionType.API_IMPL),
            new CodeAction("Api Dto Code Generate", ActionType.DTO),
            new Separator(),
            new CodeAction("Controller Code Generate", ActionType.CONTROLLER),
            new Separator(),
            new CodeAction("Vue List Code Generate", ActionType.VUE_LIST),
            new CodeAction("Vue Add Code Generate", ActionType.VUE_ADD),
            new CodeAction("Vue Detail Code Generate", ActionType.VUE_DETAIL)
        };
    }
}
