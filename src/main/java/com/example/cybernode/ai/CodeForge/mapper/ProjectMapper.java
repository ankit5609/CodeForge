package com.example.cybernode.ai.CodeForge.mapper;

import com.example.cybernode.ai.CodeForge.dto.member.MemberResponse;
import com.example.cybernode.ai.CodeForge.dto.project.ProjectResponse;
import com.example.cybernode.ai.CodeForge.dto.project.ProjectSummaryResponse;
import com.example.cybernode.ai.CodeForge.entity.Project;
import com.example.cybernode.ai.CodeForge.entity.ProjectMember;
import com.example.cybernode.ai.CodeForge.enums.ProjectRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ProjectMapper {
    ProjectResponse toProjectResponse(Project project);

    List<ProjectSummaryResponse> toListOfProjectSummaryResponse(List<Project> projects);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "role", source = "projectRole")
    MemberResponse toMemberResponseFromProjectMember(ProjectMember projectMember);

    ProjectSummaryResponse toProjectSummaryResponse(Project project, ProjectRole role);
}
