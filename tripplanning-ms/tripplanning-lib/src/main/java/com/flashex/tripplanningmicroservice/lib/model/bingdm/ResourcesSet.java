package com.flashex.tripplanningmicroservice.lib.model.bingdm;

import com.flashex.tripplanningmicroservice.lib.model.bingdm.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesSet {
    int estimatedTotal;
    List<Resource> resources;
}
