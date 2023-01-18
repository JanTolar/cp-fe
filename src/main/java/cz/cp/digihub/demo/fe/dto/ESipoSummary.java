package cz.cp.digihub.demo.fe.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ESipoSummary {

	private ESipoPlatce platceDetail;
	private List<ESipoGenericReview> ruleSummary;
	private List<ESipoGenericReview> paymentSummary;

}