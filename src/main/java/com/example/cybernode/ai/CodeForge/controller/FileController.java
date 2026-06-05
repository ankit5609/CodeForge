package com.example.cybernode.ai.CodeForge.controller;

import com.example.cybernode.ai.CodeForge.dto.project.FileContentResponse;
import com.example.cybernode.ai.CodeForge.dto.project.FileNode;
import com.example.cybernode.ai.CodeForge.dto.project.FileTreeResponse;
import com.example.cybernode.ai.CodeForge.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/files")
public class FileController {
    private final ProjectFileService fileService;

    @GetMapping
    public ResponseEntity<FileTreeResponse> getFileTree(@PathVariable Long projectId){
        return ResponseEntity.ok(fileService.getFileTree(projectId));
    }

    @GetMapping("/content")
    public ResponseEntity<FileContentResponse> getFile(@PathVariable Long projectId,
                                                       @RequestParam String path){
        return ResponseEntity.ok(fileService.getFileContent(projectId,path));
    }
}
