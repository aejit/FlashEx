package com.flashex.shipmentmicroservice.lib.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.protocol.types.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaReprocessMessage {
    private String packetId;
    private String reprocessStage;
}
