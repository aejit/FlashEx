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
public class BingDistanceMatrix {

    String authenticationResultCode;
    String brandLogoUri;
    String copyright;
    List<ResourcesSet> resourceSets;
    int statusCode;
    String statusDescription;
    String traceId;
}

