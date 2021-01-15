package com.space.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//используеться для сосоставления с таблицей 'ship'
@Entity
public class Ship
{
    //используеться для указания ключа таблицы (первая анотация) и для указания,
    //что он генерируется автоматически (вторая анотация)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String planet;

    @Enumerated(EnumType.STRING)
    private ShipType shipType;

    private Date prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    //почему обязательный такой конструктор?
    public Ship() {

    }

    public Ship(String name, String planet, ShipType shipType, long prodDate, Boolean isUsed, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = new Date(prodDate);
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlanet() {
        return planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public Double getSpeed() {
        return speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
