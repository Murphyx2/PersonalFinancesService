package com.app.personalfinancesservice.converters;

import com.app.personalfinancesservice.domain.categoryplanner.input.DeleteCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.GetCategoryPlannerRequest;
import com.app.personalfinancesservice.domain.categoryplanner.input.UpdateCategoryPlannerRequest;

public class GetCategoryPlannerRequestConverter {

	public static GetCategoryPlannerRequest convert(DeleteCategoryPlannerRequest request){
		return new GetCategoryPlannerRequest() //
				.withId(request.getId()) //
				.withUserId(request.getUserId()) //
				;
	}

	public static GetCategoryPlannerRequest convert(UpdateCategoryPlannerRequest request){
		return new GetCategoryPlannerRequest() //
				.withId(request.getId()) //
				.withUserId(request.getUserId()) //
				;
	}


	private GetCategoryPlannerRequestConverter(){
		// Empty on purpose
	}
}
