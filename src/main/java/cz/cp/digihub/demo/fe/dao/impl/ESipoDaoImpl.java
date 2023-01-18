package cz.cp.digihub.demo.fe.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cz.cp.digihub.demo.fe.dao.ESipoDao;
import cz.cp.digihub.demo.fe.dto.ESipoGenericReview;
import cz.cp.digihub.demo.fe.dto.ESipoPlatce;
import cz.cp.digihub.demo.fe.utils.QueryUtils;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ESipoDaoImpl implements ESipoDao {

	@Value("${app.db.names.defaultSchema}")
	private String defaultSchema;

	private final NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public List<Integer> loadPlatciIdsList() {
//		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(QueryUtils.LOAD_CLIENTS_LIST, new MapSqlParameterSource());
//		final List<Integer> clientIds = new ArrayList<>();
//		for (final Map<String, Object> resultRow : resultList) {
//			clientIds.add((Integer) resultRow.get("id_client"));
//		}
//		return clientIds;

		// NOT USED REAL DATA, ONLY SIMPLE EXAMPLE
		final List<Integer> platciIds = Arrays.asList(
				-576248034,
				-576248003,
				-576247979,
				-576247941,
				-576247790,
				-576247625,
				-576247502,
				-576247035,
				-576246359,
				-576246091,
				-576246060,
				-576246043,
				-575480411,
				-576247430,
				-581710083);

		return platciIds;
	}

	@Override
	public ESipoPlatce loadESipoPlatce(final Integer platceId) {

		final StringBuilder sql = new StringBuilder(String.format(QueryUtils.LOAD_PLATCE_DETAIL, defaultSchema));
		final MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("spojcislo", platceId);

		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(sql.toString(), paramSource);

		final ESipoPlatce eSipoPlatce = new ESipoPlatce();
		for (final Map<String, Object> resultRow : resultList) {
			eSipoPlatce.setPlatceId((Integer) resultRow.get("spojcislo"));
			eSipoPlatce.setFirstName((String) resultRow.get("jmeno"));
			eSipoPlatce.setSurname((String) resultRow.get("prijmeni"));
			eSipoPlatce.setBirthDate((String) resultRow.get("dat_naroz"));
			eSipoPlatce.setBirthPlace((String) resultRow.get("misto_naroz"));
			eSipoPlatce.setEmail((String) resultRow.get("email"));
			eSipoPlatce.setPhone((String) resultRow.get("telefon"));
			eSipoPlatce.setReportFrequency((BigDecimal) resultRow.get("cetnost_vypis"));
		}
		return eSipoPlatce;
	}

	@Override
	public List<ESipoGenericReview> loadESipoRuleSummary(final Integer platceId, final Integer period) {
		final List<ESipoGenericReview> ruleSummary = new ArrayList<>();

		final StringBuilder sql = new StringBuilder(String.format(QueryUtils.LOAD_ESIPO_RULE_SUMMARY, defaultSchema));
		final MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("spojcislo", platceId)
				.addValue("obdobi", period);

		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(sql.toString(), paramSource);
		for (final Map<String, Object> resultRow : resultList) {
			final ESipoGenericReview rule = new ESipoGenericReview();
			rule.setPlatceId((Integer) resultRow.get("spojcislo"));
			rule.setPeriod((Integer) resultRow.get("obdobi"));
			rule.setFeeCode((Integer) resultRow.get("kodpopl"));
			rule.setSymbol((String) resultRow.get("symbol"));
			rule.setRule((BigDecimal) resultRow.get("predpis"));
			ruleSummary.add(rule);
		}

		return ruleSummary;
	}

	@Override
	public List<ESipoGenericReview> loadESipoPaymentSummary(final Integer platceId, final Integer period) {
		final List<ESipoGenericReview> paymentSummary = new ArrayList<>();

		final StringBuilder sql = new StringBuilder(String.format(QueryUtils.LOAD_ESIPO_PAYMENT_SUMMARY, defaultSchema));
		final MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("spojcislo", platceId)
				.addValue("obdobi", period);

		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(sql.toString(), paramSource);
		for (final Map<String, Object> resultRow : resultList) {
			final ESipoGenericReview payment = new ESipoGenericReview();
			payment.setPlatceId((Integer) resultRow.get("spojcislo"));
			payment.setPeriod((Integer) resultRow.get("obdobi"));
			payment.setSymbol((String) resultRow.get("symbol"));
			payment.setPayment((BigDecimal) resultRow.get("castka"));
			payment.setDeliveryDate(((Date) resultRow.get("datpod")).toString());
			payment.setProcessDate(((Date) resultRow.get("datzprac")).toString());
			payment.setDeliveryPost((Integer) resultRow.get("podposta"));
			payment.setPsc((Integer) resultRow.get("psc"));
			paymentSummary.add(payment);
		}

		return paymentSummary;
	}

	@Override
	public List<ESipoGenericReview> loadESipoRuleDetail(final Integer platceId, final Integer period, final String symbol) {
		final List<ESipoGenericReview> ruleDetail = new ArrayList<>();

		final StringBuilder sql = new StringBuilder(String.format(QueryUtils.LOAD_ESIPO_RULE_DETAIL, defaultSchema));
		final MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("spojcislo", platceId)
				.addValue("obdobi", period)
				.addValue("symbol", symbol);

		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(sql.toString(), paramSource);
		for (final Map<String, Object> resultRow : resultList) {
			final ESipoGenericReview rule = new ESipoGenericReview();
			rule.setPlatceId((Integer) resultRow.get("spojcislo"));
			rule.setPeriod((Integer) resultRow.get("obdobi"));
			rule.setFeeCode((Integer) resultRow.get("kodpopl"));
			rule.setSymbol((String) resultRow.get("symbol"));
			rule.setRule((BigDecimal) resultRow.get("predpis"));
			ruleDetail.add(rule);
		}

		return ruleDetail;
	}

	@Override
	public List<ESipoGenericReview> loadESipoPaymentDetail(final Integer platceId, final Integer period, final String symbol) {
		final List<ESipoGenericReview> paymentDetail = new ArrayList<>();

		final StringBuilder sql = new StringBuilder(String.format(QueryUtils.LOAD_ESIPO_PAYMENT_DETAIL, defaultSchema));
		final MapSqlParameterSource paramSource = new MapSqlParameterSource()
				.addValue("spojcislo", platceId)
				.addValue("obdobi", period)
				.addValue("symbol", symbol);

		final List<Map<String, Object>> resultList = this.namedJdbcTemplate.queryForList(sql.toString(), paramSource);
		for (final Map<String, Object> resultRow : resultList) {
			final ESipoGenericReview payment = new ESipoGenericReview();
			payment.setPlatceId((Integer) resultRow.get("spojcislo"));
			payment.setPeriod((Integer) resultRow.get("obdobi"));
			payment.setSymbol((String) resultRow.get("symbol"));
			payment.setPayment((BigDecimal) resultRow.get("castka"));
			payment.setFeeCode((Integer) resultRow.get("kodpopl"));
			paymentDetail.add(payment);
		}

		return paymentDetail;
	}

}
