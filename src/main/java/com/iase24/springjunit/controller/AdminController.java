package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin")
@RequiredArgsConstructor
@Valid
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/{faqId}")
    public FaqAnswerDTO answerForFaq(
            @PathVariable("faqId") Long faqId,
            @RequestBody FaqAnswerDTO answer
            ) {

        adminService.answerForFaq(faqId, answer);

        return answer;
    }

    @DeleteMapping("/{faqId}")
    public void deleteFaq(@PathVariable("faqId") Long faqId) {

        adminService.deleteFaq(faqId);
    }
}
