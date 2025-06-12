package org.app.activityservice.mapper;

import org.app.activityservice.dto.ActivityRequest;
import org.app.activityservice.dto.ActivityResponse;
import org.app.activityservice.model.Activity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ActivityMapper {

    public Activity mapToActivity(ActivityRequest request) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(request, activity);
        activity.setCreatedAt(LocalDateTime.now());
        return activity;
    }

    public ActivityResponse mapToActivityResponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        BeanUtils.copyProperties(activity, response);
        return response;
    }

}
