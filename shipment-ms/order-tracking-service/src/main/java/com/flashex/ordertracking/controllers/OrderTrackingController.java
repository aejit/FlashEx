//package com.flashex.ordertracking.controllers;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.flashex.ordertracking.messagingservice.ProducerService;
//import com.flashex.shipmentmicroservice.lib.model.Packet;
//import com.flashex.shipmentmicroservice.lib.model.Shipment;
//import com.flashex.shipmentmicroservice.lib.services.PacketService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class OrderTrackingController {
//    private final ProducerService producer;
//
//    @Autowired
//    public OrderTrackingController(ProducerService producer) {
//        this.producer = producer;
//    }
//
//    @Autowired
//    public PacketService packetService;
//
//    @GetMapping("/packets{/packetId}")
//    public List<Packet> getAllPackets() {
//        System.out.println("in get All Packets");
//        return packetService.getAllPackets();
//    }
//
//    @PostMapping("/packets")
//    public List<Packet> savePackets(@RequestBody List<Packet> packets)
//    {
//        return packetService.savePackets(packets);
//    }
//
//
//}
