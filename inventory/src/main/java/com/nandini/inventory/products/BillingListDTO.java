package com.nandini.inventory.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class BillingListDTO {
private List<BillingDTO> list;
}
