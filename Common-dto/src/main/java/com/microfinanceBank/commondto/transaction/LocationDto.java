package com.microfinanceBank.commondto.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto implements Serializable {

    private String vendor;
    private String location;

}
