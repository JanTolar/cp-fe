package cz.cp.digihub.demo.fe.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cp.digihub.demo.fe.dto.ESipoGenericReview;
import cz.cp.digihub.demo.fe.dto.ESipoSummary;
import cz.cp.digihub.demo.fe.dto.ReportDetail;
import cz.cp.digihub.demo.fe.dto.ZasilkaDetail;
import cz.cp.digihub.demo.fe.service.BalikovnaService;
import cz.cp.digihub.demo.fe.service.ESipoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebController {

	private final BalikovnaService balikovnaService;
	private final ESipoService eSipoService;

	@GetMapping(value = { "/", "/home" })
	public String index(final Model model) {
		model.addAttribute("page", "home");
		return "home";
	}

	// -----------------------------------------------------------------------

	@GetMapping(value = "/balikovna-reporty")
	public String balikovnaReporty(final Model model) {
		model.addAttribute("page", "balikovna-reporty");
		model.addAttribute("clientIds", balikovnaService.loadClientIdsList());
		model.addAttribute("postPscList", balikovnaService.loadPostList());
		return "balikovna-reporty";
	}

	@GetMapping(value = "/balikovna-reporty/process-day-report")
	public ModelAndView processZasilkaDayReport(
			final Model model,
			final String dateInString) {

		LOG.debug("Processing balikovna-reporty for: process-day-report/" + dateInString);

		balikovnaService.processDayReport(dateInString);

		model.addAttribute("processedDay", dateInString);

		return new ModelAndView("fragments/dummy");
	}

	@GetMapping(value = "/balikovna-reporty/show-report-day-detail")
	public ModelAndView loadDayReportDetail(
			final Model model,
			final String postPsc,
			String dateInString,
			Integer clientId) {

		LOG.debug("Getting balikovna-reporty/show-report-day-detail, postPsc=" + postPsc + ", dateInString=" + dateInString + ", clientId=" + clientId);

		dateInString = dateInString.equals("all") ? null : dateInString;
		clientId = clientId == -1 ? null : clientId;
		
		final List<ReportDetail> reportDetails = balikovnaService.loadPostReport(postPsc, dateInString, clientId);
		BigDecimal amountSum = new BigDecimal(0);
		for (final ReportDetail rd : reportDetails) {
			if (rd.getAmount() != null) {
				amountSum = amountSum.add(rd.getAmount());
			}
		}

		model.addAttribute("reportDetailsList", reportDetails);
		model.addAttribute("amountSum", amountSum);
		model.addAttribute("postPsc", postPsc);
		model.addAttribute("processedDay", dateInString);
		model.addAttribute("clientId", clientId);

		return new ModelAndView("fragments/zasilka-report-detail");
	}

	@GetMapping(value = "/balikovna-reporty/truncate")
	public ModelAndView processZasilkaDayReport() {
		LOG.debug("Truncating report table");
		balikovnaService.truncateReportTable();
		return new ModelAndView("fragments/dummy");
	}

	// -----------------------------------------------------------------------
	
	@GetMapping(value = "/balikovna-zakaznik")
	public String balikovnaZakaznik(final Model model) {
		model.addAttribute("page", "balikovna-zakaznik");
		model.addAttribute("clientIds", balikovnaService.loadClientIdsList());
		return "balikovna-zakaznik";
	}

	@GetMapping(value = "/balikovna-zakaznik/zasilka-history")
	public ModelAndView loadZasilkaHistory(
			final Model model,
			final Integer clientId) {

		LOG.debug("Getting zasilka-history from: zasilka-history/" + clientId);

		model.addAttribute("zasilkaHistoryList", balikovnaService.loadZasilkaHistory(clientId));

		return new ModelAndView("fragments/zasilka-history");
	}

	@GetMapping(value = "/balikovna-zakaznik/zasilka-detail")
	public ModelAndView loadZasilkaDetail(
			final Model model,
			final String zasilkaId) {

		LOG.debug("Getting zasilka-detail from: zasilka-detail/" + zasilkaId);

		final List<ZasilkaDetail> zasilkaDetailList = balikovnaService.loadZasilkaDetail(zasilkaId);
		if (CollectionUtils.isNotEmpty(zasilkaDetailList)) {
			model.addAttribute("clientId", zasilkaDetailList.get(0).getIdClient());
		}
		model.addAttribute("zasilkaId", zasilkaId);
		model.addAttribute("zasilkaDetailList", zasilkaDetailList);

		return new ModelAndView("fragments/zasilka-detail-modal");
	}

	// -----------------------------------------------------------------------

	@GetMapping(value = "/esipo")
	public String eSipo(final Model model) {
		model.addAttribute("page", "esipo");
		model.addAttribute("platciIds", eSipoService.loadPlatciIdsList());
		return "esipo";
	}

	@GetMapping(value = "/esipo/prehled")
	public ModelAndView loadESipoSummary(
			final Model model,
			final Integer platceId,
			final Integer period) {

		LOG.debug("Getting prehled for esipo: platce: " + platceId + ", period" + period);
		final ESipoSummary summary = eSipoService.loadESipoSummary(platceId, period);

		model.addAttribute("platceId", platceId);
		model.addAttribute("period", period);
		model.addAttribute("platceDetail", summary.getPlatceDetail());
		model.addAttribute("ruleSummaryList", summary.getRuleSummary());
		model.addAttribute("paymentSummaryList", summary.getPaymentSummary());

		return new ModelAndView("fragments/esipo-summary");
	}

	@GetMapping(value = "/esipo/detail")
	public ModelAndView loadESipoDetail(
			final Model model,
			final Integer platceId,
			final Integer period,
			final String symbol,
			final String type) {

		LOG.debug("Getting detail for esipo: platce: " + platceId + ", period" + period + ", symbol" + symbol + ", type" + type);
		List<ESipoGenericReview> detail = new ArrayList<>();
		if ("RULE".equals(type)) {
			detail = eSipoService.loadESipoRuleDetail(platceId, period, symbol);
		} else {
			detail = eSipoService.loadESipoPaymentDetail(platceId, period, symbol);
		}

		model.addAttribute("platceId", platceId);
		model.addAttribute("period", period);
		model.addAttribute("symbol", symbol);
		model.addAttribute("type", type);
		model.addAttribute("detailList", detail);

		return new ModelAndView("fragments/esipo-detail-modal");
	}

}
