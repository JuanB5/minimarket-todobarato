package todobarato.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import todobarato.minimarket.model.Cliente;

import java.util.List;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
    
    // Buscar clientes activos
    @Query("SELECT c FROM Cliente c WHERE c.estado = 1 ORDER BY c.apellido, c.nombre")
    List<Cliente> findClientesActivos();
    
    // Buscar por nombre, apellido o DNI
    @Query("SELECT c FROM Cliente c WHERE (UPPER(c.nombre) LIKE UPPER(CONCAT('%', :busqueda, '%')) OR UPPER(c.apellido) LIKE UPPER(CONCAT('%', :busqueda, '%')) OR c.dni LIKE CONCAT('%', :busqueda, '%')) AND c.estado = 1")
    List<Cliente> findByNombreOrApellidoOrDniContaining(@Param("busqueda") String busqueda);
    
    // Buscar por DNI exacto
    @Query("SELECT c FROM Cliente c WHERE c.dni = :dni AND c.estado = 1")
    Cliente findByDni(@Param("dni") String dni);
    
    // Contar clientes activos
    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.estado = 1")
    Long countClientesActivos();
    
    // Verificar si existe un cliente con el mismo DNI
    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.dni = :dni AND c.estado = 1")
    Long countByDni(@Param("dni") String dni);
    
    // Clientes registrados recientemente
    @Query("SELECT c FROM Cliente c WHERE c.estado = 1 ORDER BY c.fechaCreacion DESC")
    List<Cliente> findClientesRecientes();
}
