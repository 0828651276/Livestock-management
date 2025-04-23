package com.livestockmanagementapi.service.feedFlan;

import com.livestockmanagementapi.model.FeedPlan;
import com.livestockmanagementapi.service.IGenericService;

import java.util.List;

public interface IFeedPlanService extends IGenericService<FeedPlan> {
    List<FeedPlan> searchByPenName(String penName);

}
