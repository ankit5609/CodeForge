package com.example.cybernode.ai.CodeForge.repository;

import com.example.cybernode.ai.CodeForge.entity.Project;
import com.example.cybernode.ai.CodeForge.enums.ProjectRole;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("""
            SELECT p as project, pm.projectRole as role
            FROM Project p
            JOIN ProjectMember pm ON pm.project.id = p.id
            WHERE pm.user.id = :userId
              AND p.deletedAt IS NULL
            ORDER BY p.updatedAt DESC
            """)
    List<ProjectWithRole> findAllAccessibleByUser(@Param("userId") Long userId);

    @Query("""
            select p from Project p
            where p.id= :projectId
            and p.deletedAt is null
            and exists(
                 select 1 from ProjectMember pm
                    where pm.id.userId= :userId
                    and pm.id.projectId= :projectId
            )
            """)
    Optional<Project> findAccessibleProjectById(@Param("projectId") Long projectId,
                                                @Param("userId") Long userId);
    @Query("""
            SELECT p as project, pm.projectRole as role
            FROM Project p
            JOIN ProjectMember pm ON pm.project.id = p.id
            WHERE p.id = :projectId
              AND pm.user.id = :userId
              AND p.deletedAt IS NULL
            """)
    Optional<ProjectWithRole> findAccessibleProjectByIdWithRole(@Param("projectId") Long projectId,
                                                                @Param("userId") Long userId);


    interface ProjectWithRole {
        Project getProject();
        ProjectRole getRole();
    }
}
