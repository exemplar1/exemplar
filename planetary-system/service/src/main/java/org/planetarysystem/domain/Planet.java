package org.planetarysystem.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.math.BigDecimal;

public class Planet implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private BigDecimal radius;
    private BigDecimal circumference;
    private BigDecimal area;
    private BigDecimal volume;
    private BigDecimal mass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    public BigDecimal getCircumference() {
        return circumference;
    }

    public void setCircumference(BigDecimal circumference) {
        this.circumference = circumference;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, radius, circumference, area, volume, mass);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Planet) {
            Planet other = (Planet) obj;

            return Objects.equal(id, other.id) &&
                    Objects.equal(name, other.name) &&
                    Objects.equal(radius, other.radius) &&
                    Objects.equal(circumference, other.circumference) &&
                    Objects.equal(area, other.area) &&
                    Objects.equal(volume, other.volume) &&
                    Objects.equal(mass, other.mass);
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("radius", radius)
                .add("circumference", circumference)
                .add("area", area)
                .add("volume", volume)
                .add("mass", mass).toString();
    }
}
