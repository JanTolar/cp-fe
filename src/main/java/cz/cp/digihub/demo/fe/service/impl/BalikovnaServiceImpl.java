package cz.cp.digihub.demo.fe.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import cz.cp.digihub.demo.fe.dao.BalikovnaDao;
import cz.cp.digihub.demo.fe.dto.ReportDetail;
import cz.cp.digihub.demo.fe.dto.ZasilkaDetail;
import cz.cp.digihub.demo.fe.dto.ZasilkaHistory;
import cz.cp.digihub.demo.fe.service.BalikovnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalikovnaServiceImpl implements BalikovnaService {

	private final BalikovnaDao balikovnaDao;

	@Override
	public List<Integer> loadClientIdsList() {
		final List<Integer> response = balikovnaDao.loadClientIdsList();
		LOG.debug(response.toString());
		return response;
	}

	@Override
	public List<ZasilkaHistory> loadZasilkaHistory(final Integer idClient) {
		final List<ZasilkaHistory> response = balikovnaDao.loadZasilkaHistory(idClient);
		LOG.debug(response.toString());
		return response;
	}

	@Override
	public List<ZasilkaDetail> loadZasilkaDetail(final String idZasilka) {
		final List<ZasilkaDetail> response = balikovnaDao.loadZasilkaDetail(idZasilka);
		LOG.debug(response.toString());
		return response;
	}

	@Override
	public List<String> loadPostList() {
		final List<String> response = balikovnaDao.loadPostList();
		LOG.debug(response.toString());
		return response;
	}

	@Override
	public void processDayReport(final String dateInString) {
		final Date date = Date.valueOf(dateInString);
		balikovnaDao.processDayReport(date);
	}

	@Override
	public List<ReportDetail> loadPostReport(final String postPsc, final String dateInString, final Integer clientId) {
		Date date = null;
		if (dateInString != null) {
			date = Date.valueOf(dateInString);
		}

		final List<ReportDetail> response = balikovnaDao.loadPostReport(postPsc, date, clientId);
		LOG.debug(response.toString());
		return response;
	}

	@Override
	public void truncateReportTable() {
		balikovnaDao.truncateReportTable();
	}

}
