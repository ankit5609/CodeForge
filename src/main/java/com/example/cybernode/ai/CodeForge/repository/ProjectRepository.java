package com.example.cybernode.ai.CodeForge.repository;

import com.example.cybernode.ai.CodeForge.entity.Project;
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
            select p from Project p where p.deletedAt is NULL
             and exists(
                    select 1 from ProjectMember pm
                    where pm.id.userId= :userId
                    and pm.id.projectId=p.id
                    )
            order by p.updatedAt desc"""
    )
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId);

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

}
