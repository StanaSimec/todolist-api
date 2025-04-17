package com.simec.todolistapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    @GetMapping("/detail")
    public ResponseEntity<String> detail() {
        return ResponseEntity.ok("test");
    }
}
