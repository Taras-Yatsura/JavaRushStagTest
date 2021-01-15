package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShipController {
    private final ShipService service;

    public ShipController(@Autowired ShipService service) {
        this.service = service;
    }

    @GetMapping("/rest/ships")
    public List<Ship> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String planet,
            @RequestParam(required = false) ShipType shipType,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean isUsed,
            @RequestParam(required = false) Double minSpeed,
            @RequestParam(required = false) Double maxSpeed,
            @RequestParam(required = false) Integer minCrewSize,
            @RequestParam(required = false) Integer maxCrewSize,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating,
            @RequestParam(required = false, defaultValue = "ID") ShipOrder order,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "3") Integer pageSize
    )
    {
        return service.listAllShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize,
                                maxCrewSize, minRating, maxRating, order, pageNumber, pageSize);
    }

    @GetMapping("/rest/ships/count")
    public Integer getCount(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String planet,
            @RequestParam(required = false) ShipType shipType,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean isUsed,
            @RequestParam(required = false) Double minSpeed,
            @RequestParam(required = false) Double maxSpeed,
            @RequestParam(required = false) Integer minCrewSize,
            @RequestParam(required = false) Integer maxCrewSize,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating
    )
    {
        return service.listAllShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize,
                maxCrewSize, minRating, maxRating, null, null, null).size();
    }

    @PostMapping("/rest/ships/")
    public Ship createShip(@RequestBody Ship newShip)
    {
        return service.createShip(newShip);
    }

    @GetMapping("/rest/ships/{id}")
    public Ship getShipById(@PathVariable Long id)
    {
        return service.getShip(id);
    }

    @PostMapping("/rest/ships/{id}")
    public Ship updateShip(@PathVariable Long id, @RequestBody Ship newShip)
    {
        return service.updateShip(id, newShip);
    }

    @DeleteMapping("/rest/ships/{id}")
    public void deleteShip(@PathVariable Long id)
    {
        service.deleteShip(id);
    }
}
