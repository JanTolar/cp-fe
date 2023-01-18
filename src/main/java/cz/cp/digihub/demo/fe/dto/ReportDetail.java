package cz.cp.digihub.demo.fe.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDetail {

	private String postPsc; // zpc0 - PSC posty
	private Integer idClient;
	private String idZasilka;
	private String targetPsc; // zpc1 - PSC cile
	private BigDecimal amount; // Castka bez DPH
	private String chargeType; // Typ vyuctovani
	private String reportCreated;
	private String lastUpdate;

}
