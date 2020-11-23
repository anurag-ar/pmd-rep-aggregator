package com.autobuds.PMDReportAggregator.utility;

import lombok.Data;

@Data
public class SFOrgResponse {

		String access_token ;
		String signature ;
		String scope ;
		String id_token ;
		String instance_url ;
		String id;
		String token_type;
		String refresh_token;
}
