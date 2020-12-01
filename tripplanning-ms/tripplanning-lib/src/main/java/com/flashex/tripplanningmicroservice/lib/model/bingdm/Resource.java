package com.flashex.tripplanningmicroservice.lib.model.bingdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    String __type;
    List<LatLong> destinations;
    String errorMessage;
    List<LatLong> origins;
    List<DistanceElements> results;
}
