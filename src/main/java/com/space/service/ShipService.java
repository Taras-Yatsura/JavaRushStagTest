package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//Аннотация @Transactional - все методы этого класса будут перехвачены Spring Data JPA для управления транзакциями.
//И экземпляр интерфейса CRUDRepository будет внедрён в этот класс

@Service
@Transactional
public class ShipService {
    @Autowired
    CrudRepository repository;

    public Ship createShip(Ship newShip)
    {
        checkStringParam(newShip.getName());
        checkStringParam(newShip.getPlanet());
        checkType(newShip.getShipType());
        checkDate(newShip.getProdDate().getTime());
        checkSpeed(newShip.getSpeed());
        checkCrewSize(newShip.getCrewSize());
        if (newShip.getUsed() == null) newShip.setUsed(false);
        newShip.setRating(calculateRating(newShip.getUsed(), newShip.getProdDate(), newShip.getSpeed()));

        return (Ship) repository.save(newShip);
    }

    public Ship getShip(Long id)
    {
        checkId(id);
        return (Ship) repository.findById(id).get();
    }

    public Ship updateShip(Long id, Ship newShip)
    {
        Ship oldShip = getShip(id);

        String newName = newShip.getName();
        String newPlanet = newShip.getPlanet();
        ShipType newShipType = newShip.getShipType();
        Date newProdDate = newShip.getProdDate();
        Boolean newIsUsed = newShip.getUsed();
        Double newSpeed = newShip.getSpeed();
        Integer newCrewSize = newShip.getCrewSize();


        if (newName != null) {
            checkStringParam(newName);
            oldShip.setName(newName);
        }

        if (newPlanet != null)
        {
            checkStringParam(newPlanet);
            oldShip.setPlanet(newPlanet);
        }

        if (newShipType != null) {
            checkType(newShipType);
            oldShip.setShipType(newShipType);
        }

        if (newProdDate != null) {
            checkDate(newProdDate.getTime());
            oldShip.setProdDate(newProdDate);
        }

        if (newSpeed != null) {
            checkSpeed(newSpeed);
            oldShip.setSpeed(newSpeed);
        }

        if (newCrewSize != null) {
            checkCrewSize(newCrewSize);
            oldShip.setCrewSize(newCrewSize);
        }

        if (newIsUsed != null) oldShip.setUsed(newIsUsed);

/*
        if (!oldShip.getName().equals(newName)) {
            checkStringParam(newName);
            oldShip.setName(newName);
        }

        if (!oldShip.getPlanet().equals(newPlanet)) {
            checkStringParam(newPlanet);
            oldShip.setPlanet(newPlanet);
        }

        if (!oldShip.getShipType().equals(newShipType)) {
            checkType(newShipType);
            oldShip.setShipType(newShipType);
        }

        if (!oldShip.getProdDate().equals(newProdDate)) {
            checkDate(newProdDate.getTime());
            oldShip.setProdDate(newProdDate);
        }

        if (!oldShip.getSpeed().equals(newSpeed)) {
            checkSpeed(newSpeed);
            oldShip.setSpeed(newSpeed);
        }

        if (!oldShip.getCrewSize().equals(newCrewSize)) {
            checkCrewSize(newCrewSize);
            oldShip.setCrewSize(newCrewSize);
        }

        if (newIsUsed != null && !oldShip.getUsed().equals(newIsUsed)) oldShip.setUsed(newIsUsed);
*/

        oldShip.setRating(calculateRating(oldShip.getUsed(), oldShip.getProdDate(), oldShip.getSpeed()));

        return oldShip;
    }

    public void deleteShip(Long id)
    {
        checkId(id);
        repository.deleteById(id);
    }

    public List<Ship> listAllShips(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed,
                                   Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize,
                                   Double minRating, Double maxRating, ShipOrder order, Integer pageNumber,
                                   Integer pageSize)
    {
        List<Ship> allShips = (List<Ship>) repository.findAll();

        if (name != null && !name.equals(""))
        {
            allShips = allShips.stream().filter(ship -> ship.getName().contains(name)).collect(Collectors.toList());
        }

        if (planet != null && !planet.equals(""))
        {
            allShips = allShips.stream().filter(ship -> ship.getPlanet().contains(planet)).collect(Collectors.toList());
        }

        if (shipType != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getShipType().equals(shipType)).collect(Collectors.toList());
        }

        if (after != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getProdDate().getTime() >= after).collect(Collectors.toList());
        }

        if (before != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getProdDate().getTime() <= before).collect(Collectors.toList());
        }

        if (isUsed != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getUsed().equals(isUsed)).collect(Collectors.toList());
        }

        if (minSpeed != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getSpeed() >= minSpeed).collect(Collectors.toList());
        }

        if (maxSpeed != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getSpeed() <= maxSpeed).collect(Collectors.toList());
        }

        if (minCrewSize != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getCrewSize() >= minCrewSize).collect(Collectors.toList());
        }

        if (maxCrewSize != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getCrewSize() <= maxCrewSize).collect(Collectors.toList());
        }

        if (minRating != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getRating() >= minRating).collect(Collectors.toList());
        }

        if (maxRating != null)
        {
            allShips = allShips.stream().filter(ship -> ship.getRating() <= maxRating).collect(Collectors.toList());
        }

        if (order == null || pageNumber == null || pageSize == null) return allShips;

        return allShips.stream()
                .sorted(getCurrentComparator(order))
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    //private methods for main methods

    private Comparator<Ship> getCurrentComparator(ShipOrder order)
    {
        switch (order)
        {
            case SPEED:
                return Comparator.comparingDouble(Ship::getSpeed);
            case DATE:
                return Comparator.comparing(Ship::getProdDate);
            case RATING:
                return Comparator.comparingDouble(Ship::getRating);
            default:
                return Comparator.comparingLong(Ship::getId);
        }
    }

    private void checkStringParam(String param)
    {
        if (param == null || param.equals("") || param.length() > 50)
            throw new BadRequestException("Invalid 'name' or 'planet' parameter");
    }

    private void checkType(ShipType type)
    {
        //null here too
        if ((type != ShipType.MILITARY && type != ShipType.MERCHANT && type != ShipType.TRANSPORT))
            throw new BadRequestException("Invalid Ship Type parameter");
    }

    private void checkDate(Long date)
    {
        if (date == null || date < 26192246400000L || date > 33134745540000L)
            throw new BadRequestException("Invalid date parameter");
    }

    private void checkSpeed(Double speed)
    {
        if (speed != null) {
            speed = (double) Math.round(speed * 100) / 100;
            if (speed < 0.01d || speed > 0.99d) throw new BadRequestException("Invalid 'speed' parameter");
        }
        else throw new BadRequestException("Invalid 'speed' parameter");
    }

    private void checkCrewSize(Integer crewSize)
    {
        if (crewSize == null || crewSize < 1 || crewSize > 9999) throw new BadRequestException("Invalid crew size");
    }

    private Double calculateRating(Boolean isUsed, Date prodDate, Double speed)
    {
        double k = isUsed ? 0.5d : 1.0d;
        int curYear = 3019;
        String prodYearString = new SimpleDateFormat("yyyy").format(prodDate);
        int prodYear = Integer.parseInt(prodYearString);
        return (double) Math.round((80 * speed * k) / (curYear - prodYear + 1) * 100) / 100;
    }

    private void checkId(Long id)
    {
        if (id == null || id <= 0) throw new BadRequestException("Invalid id");
        if (!repository.existsById(id)) throw new NotFoundException("Invalid id");
    }
}
