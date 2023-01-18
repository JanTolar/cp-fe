package cz.cp.digihub.demo.fe.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QueryUtils {

	// Balikovna-reporty
	public static final String LOAD_DAY_POST_REPORT_BASE;
	public static final String LOAD_DAY_POST_REPORT_COND_DATE;
	public static final String LOAD_DAY_POST_REPORT_COND_CLIENT_ID;

	// Balikovna-zakaznik
	public static final String LOAD_CLIENTS_LIST;
	public static final String LOAD_ZASILKA_HISTORY_BASE;
	public static final String LOAD_ZASILKA_DETAIL_BASE;

	// eSipo
	public static final String LOAD_PLATCI_LIST;
	public static final String LOAD_PLATCE_DETAIL;
	public static final String LOAD_ESIPO_RULE_SUMMARY;
	public static final String LOAD_ESIPO_PAYMENT_SUMMARY;
	public static final String LOAD_ESIPO_RULE_DETAIL;
	public static final String LOAD_ESIPO_PAYMENT_DETAIL;

	static {
		LOAD_DAY_POST_REPORT_BASE = loadDayPostReportBase();
		LOAD_DAY_POST_REPORT_COND_DATE = loadDayPostReportConditionDate();
		LOAD_DAY_POST_REPORT_COND_CLIENT_ID = loadDayPostReportConditionClientId();
		
		LOAD_CLIENTS_LIST = loadClientsList();
		LOAD_ZASILKA_HISTORY_BASE = loadZasilkaHistoryBase();
		LOAD_ZASILKA_DETAIL_BASE = loadZasilkaDetailBase();

		LOAD_PLATCI_LIST = loadPlatciList();
		LOAD_PLATCE_DETAIL = loadESipoPlatceDetail();
		LOAD_ESIPO_RULE_SUMMARY = loadESipoRuleSummary();
		LOAD_ESIPO_PAYMENT_SUMMARY = loadESipoPaymentSummary();
		LOAD_ESIPO_RULE_DETAIL = loadESipoRuleDetail();
		LOAD_ESIPO_PAYMENT_DETAIL = loadESipoPaymentDetail();
	}

	private static String loadDayPostReportBase() {
		return """
			select 
				r.zpc0,						-- PSC posty
				r.id_zasilka,				-- Zpracovane zasilky
				r.id_client,
				r.zpc1,						-- PSC cile
				r.tarif16 as amount_wo_vat,	-- Castka bez DPH
				cht.charge_type,
				r.sys_report_create,
				r.sys_report_update				-- Typ vyuctovani
			from digi_hub.report r
				left join digi_hub.codelist_charge_type cht on cht.code = r.charge
				where r.id_client is not null
					and r.zpc0 = :postPsc				-- PSC posty
			""";
	}

	private static String loadDayPostReportConditionDate() {
		return """
				and r.date0::date = :selectedDate
		    """;
	}

	private static String loadDayPostReportConditionClientId() {
		return """
				and r.id_client = :clientId
		    """;
	}

	private static String loadClientsList() {
		return """
				SELECT id_client as id_client
				FROM %s.%s
				where id_client is not null
				GROUP  BY id_client
				order by id_client asc;
			""";
	}

	private static String loadZasilkaHistoryBase() {
		return """
				select 
					zu1.id_zasilka,
					zu1.id_client,
					zu1.date0
				from %s.%s zu1
				where id_client = :idClient and status in ('21','22','23','24','25','28')
				order by date0 asc;
			""";
	}

	private static String loadZasilkaDetailBase() {
		return """
				select 
					zu1.id_zasilka,
					zu1.id_client,	-- cislo zakaznika - filtr pro views
					zu1.date0,
					cs.description,
					cht.charge_type as charge		-- typ vyucotvani - filtr pro views
				from %s.%s zu1
					left join %s.codelist_charge_type cht on cht.code = zu1.charge
					left join %s.codelist_status cs on cs.status = zu1.status and cs.reason = nullif(trim(zu1.reason), '')
				where zu1.id_zasilka = :idZasilka
					and id_client is not null
				order by date0 asc;
			""";
	}

	private static String loadPlatciList() {
		return """
				select * from %s.nsipo_platci;
			""";
	}

	private static String loadESipoPlatceDetail() {
		return """
			select 
				platci.spojcislo,
				platci.jmeno,
				platci.prijmeni,
				platci.dat_naroz,
				platci.misto_naroz,
				platci.email,
				platci.telefon,
				platci.cetnost_vypis
			from %s.nsipo_platci platci
			where platci.spojcislo = :spojcislo;
		""";
	}

	private static String loadESipoRuleSummary() {
		return """
			select 
				sm.spojcislo,
				sm.obdobi,
				sm.kodpopl,
				sm.symbol,
				sm.predpis
			from %s.nsipo_p_sum_mview sm
			where sm.spojcislo = :spojcislo and sm.obdobi = :obdobi;
		""";
	}

	private static String loadESipoPaymentSummary() {
		return """
			select 
				zaplm.spojcislo,
				zaplm.obdobi,
				zaplm.symbol,
				zaplm.castka,
				zaplm.datpod,
				zaplm.datzprac,
				zaplm.podposta,
				zaplm.psc
			from %s.nsipo_zaplp_sum_mview zaplm
			where zaplm.spojcislo = :spojcislo and zaplm.obdobi = :obdobi;
		""";
	}

	private static String loadESipoRuleDetail() {
		return """
			select 
				pm.spojcislo,
				pm.obdobi,
				pm.kodpopl,
				pm.symbol,
				pm.predpis
			from %s.nsipo_p_mview pm
			where pm.spojcislo = :spojcislo and pm.obdobi = :obdobi and symbol = :symbol;
		""";
	}

	private static String loadESipoPaymentDetail() {
		return """
			select 	
				zaplm.spojcislo,
				zaplm.obdobi,
				zaplm.symbol,
				zaplm.castka,
				zaplm.kodpopl
			from %s.nsipo_zaplp_mview zaplm
			where zaplm.spojcislo = :spojcislo and zaplm.obdobi = :obdobi and symbol = :symbol;
		""";
	}

}
