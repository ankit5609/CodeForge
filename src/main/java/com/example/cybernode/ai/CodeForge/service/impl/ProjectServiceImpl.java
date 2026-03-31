package com.example.cybernode.ai.CodeForge.service.impl;

import com.example.cybernode.ai.CodeForge.dto.project.ProjectRequest;
import com.example.cybernode.ai.CodeForge.dto.project.ProjectResponse;
import com.example.cybernode.ai.CodeForge.dto.project.ProjectSummaryResponse;
import com.example.cybernode.ai.CodeForge.entity.Project;
import com.example.cybernode.ai.CodeForge.entity.ProjectMember;
import com.example.cybernode.ai.CodeForge.entity.ProjectMemberId;
import com.example.cybernode.ai.CodeForge.entity.User;
import com.example.cybernode.ai.CodeForge.enums.ProjectRole;
import com.example.cybernode.ai.CodeForge.error.ResourceNotFoundException;
import com.example.cybernode.ai.CodeForge.mapper.ProjectMapper;
import com.example.cybernode.ai.CodeForge.repository.ProjectMemberRepository;
import com.example.cybernode.ai.CodeForge.repository.ProjectRepository;
import com.example.cybernode.ai.CodeForge.repository.UserRepository;
import com.example.cybernode.ai.CodeForge.service.ProjectMemberService;
import com.example.cybernode.ai.CodeForge.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;
    ProjectMemberRepository projectMemberRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        User owner=userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user",userId.toString())
        );
        Project project=Project.builder().
                name(request.name()).
                isPublic(false).
                build();
        project = projectRepository.save(project);
        ProjectMemberId projectMemberId=new ProjectMemberId(project.getId(), owner.getId());
        ProjectMember projectMember=ProjectMember.builder()
                .id(projectMemberId)
                .projectRole(ProjectRole.OWNER)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(project)
                .build();
        projectMemberRepository.save(projectMember);
        project=projectRepository.save(project);
        return projectMapper.toProjectResponse(project);

    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {

        return projectMapper.toListOfProjectSummaryResponse(projectRepository.findAllAccessibleByUser(userId));
    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId) {
        Project project=getAccessibleProjectById(id,userId);
        return projectMapper.toProjectResponse(project);
    }


    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        Project project=getAccessibleProjectById(id,userId);
        project.setName(request.name());
        project=projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softdelete(Long id, Long userId) {
        Project project=getAccessibleProjectById(id,userId);
        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    /// INTERNAL FUNCTIONS
    public  Project getAccessibleProjectById(Long projectId,Long userId){
        return projectRepository.findAccessibleProjectById(projectId,userId)
                .orElseThrow(()-> new ResourceNotFoundException("Project",projectId.toString()));
    }
}
