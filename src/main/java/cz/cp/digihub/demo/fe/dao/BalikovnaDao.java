package cz.cp.digihub.demo.fe.dao;

import java.sql.Date;
import java.util.List;

import cz.cp.digihub.demo.fe.dto.ReportDetail;
import cz.cp.digihub.demo.fe.dto.ZasilkaDetail;
import cz.cp.digihub.demo.fe.dto.ZasilkaHistory;

public interface BalikovnaDao {

	public List<Integer> loadClientIdsList();

	public List<ZasilkaHistory> loadZasilkaHistory(Integer idClient);

	public List<ZasilkaDetail> loadZasilkaDetail(String idZasilka);

	public List<String> loadPostList();

	public void processDayReport(Date selectedDate);

	public List<ReportDetail> loadPostReport(final String postPsc, Date selectedDate, Integer clientId);

	public void truncateReportTable();

}
