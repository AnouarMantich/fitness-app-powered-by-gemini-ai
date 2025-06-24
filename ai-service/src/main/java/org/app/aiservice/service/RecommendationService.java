package org.app.aiservice.service;


import lombok.RequiredArgsConstructor;
import org.app.aiservice.exception.ActivityNotFoundException;
import org.app.aiservice.model.Recommendation;
import org.app.aiservice.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;


    public List<Recommendation> getUserRecommendations(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId).orElseThrow(
                ()-> new ActivityNotFoundException("Activity " + activityId + " not found")
        );
    }
}
