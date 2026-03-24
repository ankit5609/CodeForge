package com.example.cybernode.ai.CodeForge.controller;

import com.example.cybernode.ai.CodeForge.dto.project.FileContentResponse;
import com.example.cybernode.ai.CodeForge.dto.project.FileNode;
import com.example.cybernode.ai.CodeForge.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/files")
public class FileController {
    private final FileService fileService;

    @GetMapping
    public ResponseEntity<List<FileNode>> getFileTree(@PathVariable Long projectId){
        Long userId=1L;
        return ResponseEntity.ok(fileService.getFileTree(projectId,userId));
    }

    @GetMapping("/{path:**}")
    public ResponseEntity<FileContentResponse> getFile(@PathVariable Long projectId,
                                                       @PathVariable String path){
        Long userId=1L;
        return ResponseEntity.ok(fileService.getFileContent(projectId,path,userId));
    }
}
