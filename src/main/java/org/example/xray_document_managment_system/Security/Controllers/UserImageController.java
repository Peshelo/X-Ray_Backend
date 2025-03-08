package org.example.xray_document_managment_system.Security.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.xray_document_managment_system.Security.Service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class UserImageController {

    private final UserService userService;


}
