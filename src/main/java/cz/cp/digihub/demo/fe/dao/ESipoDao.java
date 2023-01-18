package cz.cp.digihub.demo.fe.dao;

import java.util.List;

import cz.cp.digihub.demo.fe.dto.ESipoGenericReview;
import cz.cp.digihub.demo.fe.dto.ESipoPlatce;

public interface ESipoDao {

	public List<Integer> loadPlatciIdsList();

	public ESipoPlatce loadESipoPlatce(Integer platceId);

	public List<ESipoGenericReview> loadESipoRuleSummary(Integer platceId, Integer period);

	public List<ESipoGenericReview> loadESipoPaymentSummary(Integer platceId, Integer period);

	public List<ESipoGenericReview> loadESipoRuleDetail(Integer platceId, Integer period, String symbol);

	public List<ESipoGenericReview> loadESipoPaymentDetail(Integer platceId, Integer period, String symbol);

}
