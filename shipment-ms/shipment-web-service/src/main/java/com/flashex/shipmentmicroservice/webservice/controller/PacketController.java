package com.flashex.shipmentmicroservice.webservice.controller;


import com.flashex.shipmentmicroservice.lib.model.Packet;
import com.flashex.shipmentmicroservice.lib.services.PacketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PacketController {
    private final Logger logger = LoggerFactory.getLogger(PacketController.class);

    @Autowired
    private PacketService packetService;

    @GetMapping("/packets")
    public List<Packet> getAllPackets() {
        System.out.println("in get All Packets");
        return packetService.getAllPackets();
    }

    @PostMapping("/packets")
    public List<Packet> savePackets(@RequestBody List<Packet> packets)
    {
        return packetService.savePackets(packets);
    }


    @GetMapping("/packets/{packetId}")
    public Packet getPacketById(@PathVariable String packetId){
        return packetService.findByPacketId(packetId);
    }
}
