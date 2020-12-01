package com.flashex.shipmentmicroservice.lib.services;

import com.flashex.shipmentmicroservice.lib.model.Packet;
import com.flashex.shipmentmicroservice.lib.repository.PacketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderTrackingService {

    @Autowired
    private PacketRepository packetRepository;

    public List<Packet> getAllPackets(){
        List<Packet> packets =new ArrayList<>();
        packetRepository.findAll()
                .forEach(packets::add);
        return packets;
    }

}
