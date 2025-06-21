package org.jmurillo.apiservlet.webapp.session.services;

import org.jmurillo.apiservlet.webapp.session.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImp implements ProductoService {
    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(1L, "notebook", "computacion", 2000000),
                new Producto(2L, "Lenovo Ideapack", "computacion", 1890000),
                new Producto(3L, "Cama Comfort", "home", 1200000),
                new Producto(4L, "Harmario Comfort", "home", 500000),
                new Producto(5L, "play Station", "gaming", 1200000),
                new Producto(6L, "Nintendo", "gaming", 900000)
        );
    }

    @Override
    public Optional<Producto> buscarProductoPorId(Long id) {
        return listar().stream().filter(p -> p.getId().equals(id)).findAny();
    }

}
