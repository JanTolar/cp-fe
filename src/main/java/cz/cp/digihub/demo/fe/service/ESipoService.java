package cz.cp.digihub.demo.fe.service;

import java.util.List;

import cz.cp.digihub.demo.fe.dto.ESipoGenericReview;
import cz.cp.digihub.demo.fe.dto.ESipoSummary;

public interface ESipoService {

	public List<Integer> loadPlatciIdsList();

	public ESipoSummary loadESipoSummary(Integer platceId, Integer period);

	public List<ESipoGenericReview> loadESipoRuleDetail(Integer platceId, Integer period, String symbol);

	public List<ESipoGenericReview> loadESipoPaymentDetail(final Integer platceId, final Integer period, String symbol);

}
