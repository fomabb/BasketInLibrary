package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/admin")
@RequiredArgsConstructor
@Valid
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Hello Admin!", HttpStatus.OK);
    }


    @PutMapping("/{faqId}")
    public FaqAnswerDTO answerForFaq(
            @PathVariable("faqId") Long faqId,
            @RequestBody FaqAnswerDTO answer
    ) {
        adminService.answerForFaq(faqId, answer);

        return answer;
    }

    @DeleteMapping("/categoryId/{categoryId}/faqId/{faqId}")
    public ResponseEntity<String> deleteFaq(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("faqId") Long faqId
    ) {
        adminService.deleteFaq(categoryId, faqId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Faq with ID " + faqId + " successfully deleted from category with ID " + categoryId);
    }

    @PostMapping("/description")
    public DescriptionCategory createDescriptionCategory(@RequestBody DescriptionCategory descriptionCategory) {

        adminService.createDescriptionCategory(descriptionCategory);

        return descriptionCategory;
    }
}
