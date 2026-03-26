package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.auth.UserProfileResponse;
import com.example.cybernode.ai.CodeForge.dto.member.MemberResponse;
import com.example.cybernode.ai.CodeForge.dto.member.UpdateMemberRoleRequest;
import com.example.cybernode.ai.CodeForge.dto.project.InviteMemberRequest;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectMemberService {

    List<MemberResponse> getProjectMembers(Long projectId, Long userId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

    MemberResponse updateMemberRole(Long userId, Long projectId, Long memberId, UpdateMemberRoleRequest request);

    MemberResponse deleteProjectMember(Long userId, Long projectId, Long memberID);
}
