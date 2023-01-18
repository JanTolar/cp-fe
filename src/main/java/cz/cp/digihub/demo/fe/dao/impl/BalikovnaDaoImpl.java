package cz.cp.digihub.demo.fe.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import cz.cp.digihub.demo.fe.dao.BalikovnaDao;
import cz.cp.digihub.demo.fe.dto.ReportDetail;
import cz.cp.digihub.demo.fe.dto.ZasilkaDetail;
import cz.cp.digihub.demo.fe.dto.ZasilkaHistory;
import cz.cp.digihub.demo.fe.utils.QueryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BalikovnaDaoImpl implements BalikovnaDao {

	@Value("${app.db.names.defaultSchema}")
	private String defaultSchema;

	@Value("${app.db.names.cdsReplicaSchema}")
	private String cdsReplicaSchema;

	@Value("${app.db.names.zasilkaUdalostTable}")
	private String zasilkaUdalostTable;

	private final NamedParameterJdbcTemplate namedJdbcTemplate;
	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Integer> loadClientIdsList() {
		final String LOAD_CLIENTS_LIST = String.format(QueryUtils.LOAD_CLIENTS_LIST, cdsReplicaSchema, zasilkaUdalostTable);
		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(LOAD_CLIENTS_LIST, new MapSqlParameterSource());

		final List<Integer> clientIds = new ArrayList<>();
		for (final Map<String, Object> resultRow : resultList) {
			clientIds.add((Integer) resultRow.get("id_client"));
		}
		return clientIds;
	}

	@Override
	public List<ZasilkaHistory> loadZasilkaHistory(final Integer idClient) {

		final String LOAD_ZASILKA_HISTORY_BASE = String.format(QueryUtils.LOAD_ZASILKA_HISTORY_BASE, cdsReplicaSchema, zasilkaUdalostTable);
		final StringBuilder sql = new StringBuilder(LOAD_ZASILKA_HISTORY_BASE);
		final MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("idClient", idClient);

		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(sql.toString(), paramSource);

		return mapResultToZasilkaHistory(resultList);
	}

	private List<ZasilkaHistory> mapResultToZasilkaHistory(final List<Map<String, Object>> resultList) {
		final List<ZasilkaHistory> zasilkaHistoryList = new ArrayList<>();
		final Set<String> duplicityControl = new HashSet<>();

		for (final Map<String, Object> resultRow : resultList) {
			final String idZasilka = (String) resultRow.get("id_zasilka");
			if (!duplicityControl.contains(idZasilka)) {
				final ZasilkaHistory zasilkaHistory = new ZasilkaHistory();
				zasilkaHistory.setIdZasilka(idZasilka);
				zasilkaHistory.setIdClient((Integer) resultRow.get("id_client"));
				zasilkaHistory.setDatStav(((Timestamp) resultRow.get("date0")).toString());
				zasilkaHistoryList.add(zasilkaHistory);
				duplicityControl.add(idZasilka);
			}
		}
		return zasilkaHistoryList;
	}

	@Override
	public List<ZasilkaDetail> loadZasilkaDetail(final String idZasilka) {

		final String LOAD_ZASILKA_DETAIL_BASE = String.format(QueryUtils.LOAD_ZASILKA_DETAIL_BASE, cdsReplicaSchema, zasilkaUdalostTable, defaultSchema, defaultSchema);
		final StringBuilder sql = new StringBuilder(LOAD_ZASILKA_DETAIL_BASE);
		final MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("idZasilka", idZasilka);

		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(sql.toString(), paramSource);

		return mapResultToZasilkaDetail(resultList);
	}

	private List<ZasilkaDetail> mapResultToZasilkaDetail(final List<Map<String, Object>> resultList) {
		final List<ZasilkaDetail> zasilkaDetailList = new ArrayList<>();

		for (final Map<String, Object> resultRow : resultList) {
			if ((String) resultRow.get("description") != null) {
				final ZasilkaDetail zasilkaDetail = new ZasilkaDetail();
				zasilkaDetail.setIdZasilka((String) resultRow.get("id_zasilka"));
				zasilkaDetail.setIdClient((Integer) resultRow.get("id_client"));
				zasilkaDetail.setDatStav(((Timestamp) resultRow.get("date0")).toString());
				zasilkaDetail.setStatus((String) resultRow.get("description"));
				zasilkaDetail.setCharge((String) resultRow.get("charge"));
				zasilkaDetailList.add(zasilkaDetail);
			}
		}
		return zasilkaDetailList;
	}

	@Override
	public List<String> loadPostList() {
//		String loadPostList = "select r.zpc0 from %s.report r group by zpc0;";
//		loadPostList = String.format(loadPostList, defaultSchema);
//
//		final List<String> postList = new ArrayList<>();
//		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(loadPostList.toString(), new MapSqlParameterSource());
//		for (final Map<String, Object> resultRow : resultList) {
//			postList.add((String) resultRow.get("zpc0"));
//		}

		final List<String> postList = Arrays.asList("29001 ","16023 ","28804 ","50015 ","16090 ","39007 ","53002 ","16021 ","50016 ","50002 ","26613 ","16032 ","26745 ","74907 ","16026 ","50010 ","26732 ","26703 ","74610 ","39707 ","16031 ","50370 ","50021 ","50014 ","16029 ","27207 ","74901 ","16040 ","13000 ","50018 ","54107 ","27401 ","26107 ","14000 ","26744 ","74903 ","32800 ","75707 ","26607 ","16039 ","70071 ","26731 ","26602 ","28802 ","79007 ","16022 ","16000 ","17704 ","38607 ","16038 ","10003 ","26601 ","74235 ","28002 ","74604 ","53010 ","74700 ","68207 ","74601 ");
		
		return postList;
	}

	@Override
	public void processDayReport(final Date selectedDate) {
		String processDayReportSql = "select %s.report_day_transactions(:selectedDate);";

		processDayReportSql = String.format(processDayReportSql, defaultSchema);

		final SqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("selectedDate", selectedDate);

		this.namedJdbcTemplate.queryForList(processDayReportSql, paramSource);
	}

	@Override
	public List<ReportDetail> loadPostReport(final String postPsc, final Date selectedDate, final Integer clientId) {

		final StringBuilder LOAD_DAY_POST_REPORT_SQL = new StringBuilder(String.format(QueryUtils.LOAD_DAY_POST_REPORT_BASE, defaultSchema, defaultSchema));

		final MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("postPsc", postPsc);

		if (selectedDate != null) {
			LOAD_DAY_POST_REPORT_SQL.append(QueryUtils.LOAD_DAY_POST_REPORT_COND_DATE);
			paramSource.addValue("selectedDate", selectedDate);
		}
		if (clientId != null) {
			LOAD_DAY_POST_REPORT_SQL.append(QueryUtils.LOAD_DAY_POST_REPORT_COND_CLIENT_ID);
			paramSource.addValue("clientId", clientId);
		}

		LOG.debug("LOAD_DAY_POST_REPORT_SQL: " + LOAD_DAY_POST_REPORT_SQL);

		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(LOAD_DAY_POST_REPORT_SQL.toString(), paramSource);

		return mapResultToReportDetail(resultList);
	}

	private List<ReportDetail> mapResultToReportDetail(final List<Map<String, Object>> resultList) {
		final List<ReportDetail> reportDetailList = new ArrayList<>();

		for (final Map<String, Object> resultRow : resultList) {
			final ReportDetail reportDetail = new ReportDetail();
			reportDetail.setPostPsc((String) resultRow.get("zpc0"));
			reportDetail.setIdZasilka((String) resultRow.get("id_zasilka"));
			reportDetail.setIdClient((Integer) resultRow.get("id_client"));
			reportDetail.setTargetPsc((String) resultRow.get("zpc1"));
			reportDetail.setAmount((BigDecimal) resultRow.get("amount_wo_vat"));
			reportDetail.setChargeType((String) resultRow.get("charge_type"));

			final Timestamp createdDate = (Timestamp) resultRow.get("sys_report_create");
			if (createdDate != null) {
				reportDetail.setReportCreated(createdDate.toString());
			}
			final Timestamp updatedDate = (Timestamp) resultRow.get("sys_report_update");
			if (updatedDate != null) {
				reportDetail.setLastUpdate(updatedDate.toString());
			} else {
				reportDetail.setLastUpdate(reportDetail.getReportCreated());
			}
			reportDetailList.add(reportDetail);
		}
		return reportDetailList;
	}

	@Override
	public void truncateReportTable() {
		String truncateSql = "truncate %s.report;";

		truncateSql = String.format(truncateSql, defaultSchema);

		jdbcTemplate.execute(truncateSql);
	}

}
