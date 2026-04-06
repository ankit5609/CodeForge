package com.example.cybernode.ai.CodeForge.service;

import com.example.cybernode.ai.CodeForge.dto.member.MemberResponse;
import com.example.cybernode.ai.CodeForge.dto.member.UpdateMemberRoleRequest;
import com.example.cybernode.ai.CodeForge.dto.member.InviteMemberRequest;

import java.util.List;

public interface ProjectMemberService {

    List<MemberResponse> getProjectMembers(Long projectId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request);

    void removeProjectMember(Long projectId, Long memberId);
}
