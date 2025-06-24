package org.app.activityservice.controller;

import lombok.RequiredArgsConstructor;
import org.app.activityservice.dto.ActivityRequest;
import org.app.activityservice.dto.ActivityResponse;
import org.app.activityservice.service.ActivityService.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;


    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request,@RequestHeader("X-User-ID")  String userId){
        if(userId!=null){
            request.setUserId(userId);
        }
        return ResponseEntity.ok(activityService.trackActivity(request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getActivities(@RequestHeader("X-User-Id") String userId){
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable String id){
        return ResponseEntity.ok(activityService.getActivityById(id));
    }


}
