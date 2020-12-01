package com.flashex.shipmentmicroservice.lib.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bin {

    @Id
    private String binId;
//    private int binSize;
    private Date createdOn;
    private List<String> binningStrategy;
    private String sortingStrategy;
    private List<Packet> binnedPackets;

}
