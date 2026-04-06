package com.example.cybernode.ai.CodeForge.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.example.cybernode.ai.CodeForge.enums.ProjectPermission.*;

@RequiredArgsConstructor
@Getter
public enum ProjectRole {
    EDITOR(Set.of(EDIT, VIEW, DELETE)),
    VIEWER(Set.of(VIEW,VIEW_MEMBERS)),
    OWNER(Set.of(VIEW,EDIT,DELETE,MANAGE_MEMBERS, VIEW_MEMBERS));

    private final Set<ProjectPermission> permissions;
}
