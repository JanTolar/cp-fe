package cz.cp.digihub.demo.fe.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cz.cp.digihub.demo.fe.dao.ESipoDao;
import cz.cp.digihub.demo.fe.dto.ESipoGenericReview;
import cz.cp.digihub.demo.fe.dto.ESipoSummary;
import cz.cp.digihub.demo.fe.service.ESipoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ESipoServiceImpl implements ESipoService {

	private final ESipoDao eSipoDao;

	@Override
	public List<Integer> loadPlatciIdsList() {
		final List<Integer> response = eSipoDao.loadPlatciIdsList();
		LOG.debug(response.toString());
		return response;
	}

	@Override
	public ESipoSummary loadESipoSummary(final Integer platceId, final Integer period) {
		final ESipoSummary response = new ESipoSummary();
		response.setPlatceDetail(eSipoDao.loadESipoPlatce(platceId));
		response.setRuleSummary(eSipoDao.loadESipoRuleSummary(platceId, period));
		response.setPaymentSummary(eSipoDao.loadESipoPaymentSummary(platceId, period));

		LOG.debug(response.toString());

		return response;
	}

	@Override
	public List<ESipoGenericReview> loadESipoRuleDetail(final Integer platceId, final Integer period, final String symbol) {
		final List<ESipoGenericReview> response = eSipoDao.loadESipoRuleDetail(platceId, period, symbol);
		LOG.debug(response.toString());
		return response;
	}

	@Override
	public List<ESipoGenericReview> loadESipoPaymentDetail(final Integer platceId, final Integer period, final String symbol) {
		final List<ESipoGenericReview> response = eSipoDao.loadESipoPaymentDetail(platceId, period, symbol);
		LOG.debug(response.toString());
		return response;
	}

}
