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
import com.example.cybernode.ai.CodeForge.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);
        List<MemberResponse> memberResponsesList = new ArrayList<>();
        memberResponsesList.add(userMapper.toMemberResponse(project.getOwner()));
        memberResponsesList.addAll(projectMemberRepository.findByIdProjectId(projectId).
                stream().
                map(projectMapper::toMemberResponseFromProjectMember).
                toList());
        return memberResponsesList;
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("Not allowed");
        }
        User invitee=userRepository.findByEmail(request.email()).orElseThrow();

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
    public MemberResponse updateMemberRole(Long userId, Long projectId, Long memberId, UpdateMemberRoleRequest request) {
        Project project = getAccessibleProjectById(projectId, userId);

        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("Not allowed");
        }

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
    public void removeProjectMember(Long userId, Long projectId, Long memberId) {

        Project project = getAccessibleProjectById(projectId, userId);

        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("Not allowed");
        }

        ProjectMemberId projectMemberId=new ProjectMemberId(projectId, memberId);

        if(!projectMemberRepository.existsById(projectMemberId)){
            throw new RuntimeException("Invalid member");
        }

        projectMemberRepository.deleteById(projectMemberId);

        return ;
    }

    /// INTERNAL FUNCTIONS
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();
    }
}
