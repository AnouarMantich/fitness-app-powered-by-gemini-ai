package org.app.aiservice.controller;


import lombok.RequiredArgsConstructor;
import org.app.aiservice.model.Recommendation;
import org.app.aiservice.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendations(String userId) {
        return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
    }
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getActivityRecommendation(String activityId) {
        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
    }

}
