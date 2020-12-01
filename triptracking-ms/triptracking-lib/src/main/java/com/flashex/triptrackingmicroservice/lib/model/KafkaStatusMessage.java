package com.flashex.triptrackingmicroservice.lib.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaStatusMessage {

    private String packetId;
    private String statusValue;
    private Date timeStamp;
}
