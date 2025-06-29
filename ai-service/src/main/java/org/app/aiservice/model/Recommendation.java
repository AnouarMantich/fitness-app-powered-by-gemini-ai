package org.app.aiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "recommendations")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Recommendation {

    private String id;
    private String activityId;
    private String userId;
    private String activityType;
    private Integer caloriesBurned;
    private Integer duration;
    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safetySuggestions;

    @CreatedDate
    private LocalDateTime created;


}
