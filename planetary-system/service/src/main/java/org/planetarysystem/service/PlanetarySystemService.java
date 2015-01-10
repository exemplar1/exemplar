package org.planetarysystem.service;

import com.google.common.collect.ImmutableList;
import org.planetarysystem.domain.Planet;

public interface PlanetarySystemService {

    public ImmutableList<Planet> retrieveAll();
}
