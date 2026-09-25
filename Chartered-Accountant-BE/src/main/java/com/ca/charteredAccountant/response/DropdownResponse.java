package com.ca.charteredAccountant.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Generic id/label pair for Angular select boxes. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DropdownResponse {

	private Long id;
	private String name;

}
