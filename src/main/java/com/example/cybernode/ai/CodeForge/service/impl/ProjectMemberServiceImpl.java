package com.example.cybernode.ai.CodeForge.service.impl;

import com.example.cybernode.ai.CodeForge.dto.member.MemberResponse;
import com.example.cybernode.ai.CodeForge.dto.member.UpdateMemberRoleRequest;
import com.example.cybernode.ai.CodeForge.dto.member.InviteMemberRequest;
import com.example.cybernode.ai.CodeForge.entity.Project;
import com.example.cybernode.ai.CodeForge.entity.ProjectMember;
import com.example.cybernode.ai.CodeForge.entity.ProjectMemberId;
import com.example.cybernode.ai.CodeForge.entity.User;
import com.example.cybernode.ai.CodeForge.mapper.ProjectMapper;
import com.example.cybernode.ai.CodeForge.mapper.UserMapper;
import com.example.cybernode.ai.CodeForge.repository.ProjectMemberRepository;
import com.example.cybernode.ai.CodeForge.repository.ProjectRepository;
import com.example.cybernode.ai.CodeForge.repository.UserRepository;
import com.example.cybernode.ai.CodeForge.security.AuthUtil;
import com.example.cybernode.ai.CodeForge.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    UserRepository userRepository;
    UserMapper userMapper;
    ProjectMapper projectMapper;
    AuthUtil authUtil;

    @Override
    @PreAuthorize("@security.canViewMembers(#projectId)")
    public List<MemberResponse> getProjectMembers(Long projectId) {
        Long userId=authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        return projectMemberRepository.findByIdProjectId(projectId).
                stream().
                map(projectMapper::toMemberResponseFromProjectMember).
                toList();
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request) {
        Long userId=authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        User invitee=userRepository.findByUsername(request.username()).orElseThrow();

        if(invitee.getId().equals(userId)){
            throw new RuntimeException("Cannot invite yourself");
        }
        ProjectMemberId projectMemberId=new ProjectMemberId(projectId, invitee.getId());

        if(projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Cannot invite once again");
        }

        ProjectMember member= ProjectMember.builder()
                .id(projectMemberId)
                .projectRole(request.role())
                .user(invitee)
                .project(project)
                .invitedAt(Instant.now())
                .build();
        projectMemberRepository.save(member);

        return projectMapper.toMemberResponseFromProjectMember(member);
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request) {
        Long userId=authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);


        if(request.role().toString().equals("OWNER")){
            throw new RuntimeException("Invalid role Update");
        }

        ProjectMemberId projectMemberId=new ProjectMemberId(projectId, memberId);
        ProjectMember projectMember=projectMemberRepository.findById(projectMemberId).orElseThrow();
        projectMember.setProjectRole(request.role());
        projectMemberRepository.save(projectMember);
        return projectMapper.toMemberResponseFromProjectMember(projectMember);
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public void removeProjectMember(Long projectId, Long memberId) {

        Long userId=authUtil.getCurrentUserId();

        Project project = getAccessibleProjectById(projectId, userId);

        ProjectMemberId projectMemberId=new ProjectMemberId(projectId, memberId);

        if(!projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Invalid member");
        }

        projectMemberRepository.deleteById(projectMemberId);

        return ;
    }

    /// INTERNAL FUNCTIONS
    /// Give project if this user is allowed to access this project
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();
    }
}
