package org.jmurillo.apiservlet.webapp.session.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carro {
    private List<ItemCarro> items;

    public Carro() {
        this.items = new ArrayList<>();
    }

    public void addItemCarro(ItemCarro itemCarro) {
        if (items.contains(itemCarro)) {
            Optional<ItemCarro> optionalItemCarro = items.stream()
                    .filter(i -> i.equals(itemCarro))
                    .findAny();
            optionalItemCarro.ifPresent(i -> i.setCantidad(i.getCantidad() + 1));
        } else {
            this.items.add(itemCarro);
        }
    }

    public List<ItemCarro> getItems() {
        return items;
    }

    public int obtenerImporteTotal() {
        return this.items.stream().mapToInt(ItemCarro::obtenerImporte).sum();
    }

    // Nuevos métodos para actualizar y eliminar items
    public void actualizarCantidad(Long productoId, int nuevaCantidad) {
        Optional<ItemCarro> itemOptional = items.stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst();
        
        if (itemOptional.isPresent()) {
            ItemCarro item = itemOptional.get();
            if (nuevaCantidad > 0) {
                item.setCantidad(nuevaCantidad);
            } else {
                // Si la cantidad es 0 o negativa, eliminar el item
                items.remove(item);
            }
        }
    }

    public void eliminarItem(Long productoId) {
        items.removeIf(item -> item.getProducto().getId().equals(productoId));
    }

    public void eliminarItems(Long[] productosIds) {
        if (productosIds != null) {
            for (Long id : productosIds) {
                eliminarItem(id);
            }
        }
    }
}