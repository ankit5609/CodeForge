package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.project.ProjectRequest;
import com.example.cybernode.ai.CodeForge.dto.project.ProjectResponse;
import com.example.cybernode.ai.CodeForge.dto.project.ProjectSummaryResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectService {
    List<ProjectSummaryResponse> getUserProjects();

    ProjectResponse getUserProjectById(Long id);


    ProjectResponse createProject(ProjectRequest request);

    ProjectResponse updateProject(Long id, ProjectRequest request);

    void softdelete(Long id);
}
