package cz.cp.digihub.demo.fe.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ESipoGenericReview {

	private Integer platceId;	// spojcislo
	private Integer period;		// obdobi
	private Integer feeCode;	// kod poplatku
	private String symbol;		// symbol
	private BigDecimal rule;	// predpis
	private BigDecimal payment;	// castka

	private String deliveryDate;	// datum podani
	private String processDate;		// datzprac
	private Integer deliveryPost;	// podposta
	private Integer psc;				// psc

}
