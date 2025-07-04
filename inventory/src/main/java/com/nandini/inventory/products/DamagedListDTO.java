package com.nandini.inventory.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DamagedListDTO {
    private List<DamagedDTO> list;
}
