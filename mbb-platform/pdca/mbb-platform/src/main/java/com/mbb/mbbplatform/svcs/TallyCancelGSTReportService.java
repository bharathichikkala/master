package com.mbb.mbbplatform.svcs;

import java.util.List;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mbb.mbbplatform.domain.TallyCancelGSTReport;
import com.mbb.mbbplatform.model.ServiceResponse;

@RequestMapping(value = "/mbb/TallyCancelGSTReport")
public interface TallyCancelGSTReportService {
	@PostMapping("/")
	@ResponseBody
	ServiceResponse<List<TallyCancelGSTReport>> addTallyCancelGSTReport();	
	
}
