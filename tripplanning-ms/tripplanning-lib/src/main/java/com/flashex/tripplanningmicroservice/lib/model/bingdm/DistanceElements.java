package com.flashex.tripplanningmicroservice.lib.model.bingdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistanceElements {
    int destinationIndex;
    int originIndex;
    float totalWalkDuration;
    float travelDistance;
    float travelDuration;
}
