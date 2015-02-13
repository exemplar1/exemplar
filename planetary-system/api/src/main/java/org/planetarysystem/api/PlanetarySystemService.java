package org.planetarysystem.api;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.planetarysystem.contract.Planet;

public interface PlanetarySystemService {

    public Optional<Planet> retrievePlanet(Long id);

    public ImmutableList<Planet> retrieveAll();

    public Planet insertOrUpdate(Planet planet);

    public Planet delete(Long id);

    public Planet delete(Planet planet);
}
