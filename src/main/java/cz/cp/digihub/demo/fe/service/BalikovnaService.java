package cz.cp.digihub.demo.fe.service;

import java.util.List;

import cz.cp.digihub.demo.fe.dto.ReportDetail;
import cz.cp.digihub.demo.fe.dto.ZasilkaDetail;
import cz.cp.digihub.demo.fe.dto.ZasilkaHistory;

public interface BalikovnaService {

	public List<Integer> loadClientIdsList();

	public List<ZasilkaHistory> loadZasilkaHistory(Integer idClient);

	public List<ZasilkaDetail> loadZasilkaDetail(String idZasilka);

	public List<String> loadPostList();

	public void processDayReport(String dateInString);

	public List<ReportDetail> loadPostReport(final String postPsc, final String dateInString, Integer clientId);

	public void truncateReportTable();

}
