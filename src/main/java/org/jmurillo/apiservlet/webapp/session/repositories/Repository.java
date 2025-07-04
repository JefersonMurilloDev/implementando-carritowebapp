package org.jmurillo.apiservlet.webapp.session.repositories;

import java.sql.SQLException;
import java.util.List;

public interface Repository <T>{
    List<T> listar() throws SQLException;
    T PorId(Long id) throws SQLException;
    void guardar(T t) throws SQLException;
    //void actualizar(T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
}
