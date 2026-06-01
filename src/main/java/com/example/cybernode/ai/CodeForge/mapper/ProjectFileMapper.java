package com.example.cybernode.ai.CodeForge.mapper;

import com.example.cybernode.ai.CodeForge.dto.project.FileNode;
import com.example.cybernode.ai.CodeForge.entity.ProjectFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectFileMapper {

    List<FileNode> toListOfFileNode(List<ProjectFile> projectFileList);
}
