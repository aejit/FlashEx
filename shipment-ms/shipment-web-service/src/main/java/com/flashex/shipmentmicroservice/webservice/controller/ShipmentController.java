package com.flashex.shipmentmicroservice.webservice.controller;

import com.flashex.shipmentmicroservice.lib.model.Shipment;
import com.flashex.shipmentmicroservice.lib.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/shipments")
    public List<Shipment> getAllShipments(){
        System.out.println("In controller");
        return shipmentService.getAllShipments();
    }

    @PostMapping("/shipments")
    public List<Shipment> saveShipments(@RequestBody ArrayList<Shipment> shipments){
        System.out.println("In controller");
        return shipmentService.saveShipments(shipments);
    }
}
