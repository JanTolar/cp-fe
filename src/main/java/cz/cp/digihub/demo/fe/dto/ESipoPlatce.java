package cz.cp.digihub.demo.fe.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ESipoPlatce {

	private Integer platceId; // spojcislo
	private String firstName;
	private String surname;
	private String birthDate;
	private String birthPlace;
	private String email;
	private String phone;
	private BigDecimal reportFrequency;

}
