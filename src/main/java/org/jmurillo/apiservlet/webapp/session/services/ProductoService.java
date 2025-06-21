package org.jmurillo.apiservlet.webapp.session.services;

import org.jmurillo.apiservlet.webapp.session.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listar();
    Optional<Producto> buscarProductoPorId(Long id);
}
