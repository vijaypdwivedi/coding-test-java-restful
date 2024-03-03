package com.coding.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin/")
public class AdminController {

    @PostMapping("/add/book")
    @PreAuthorize("hasRole('ADMIN')")
    public String addBookDetails() {

        return "";
    }

}
