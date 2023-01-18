package cz.cp.digihub.demo.fe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZasilkaDetail {

	@Schema(description = "ID zasilky", example = "NB0001000024M")
	private String idZasilka;

	@Schema(description = "ID of client")
	private Integer idClient;

	@Schema(description = "Date and time of state creation")
	private String datStav;

	@Schema(description = "Human readeble status-reason pair")
	private String status;

	@Schema(description = "Human readable charge (payment type)")
	private String charge;

}
