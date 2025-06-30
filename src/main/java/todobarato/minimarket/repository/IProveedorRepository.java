package todobarato.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import todobarato.minimarket.model.Proveedor;

import java.util.List;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Integer> {
    
    // Buscar proveedores activos
    @Query("SELECT p FROM Proveedor p WHERE p.estado = 1 ORDER BY p.nombre")
    List<Proveedor> findProveedoresActivos();
    
    // Buscar por nombre o RUC
    @Query("SELECT p FROM Proveedor p WHERE (UPPER(p.nombre) LIKE UPPER(CONCAT('%', :busqueda, '%')) OR p.ruc LIKE CONCAT('%', :busqueda, '%')) AND p.estado = 1")
    List<Proveedor> findByNombreOrRucContaining(@Param("busqueda") String busqueda);
    
    // Buscar por RUC exacto
    @Query("SELECT p FROM Proveedor p WHERE p.ruc = :ruc AND p.estado = 1")
    Proveedor findByRuc(@Param("ruc") String ruc);
    
    // Contar proveedores activos
    @Query("SELECT COUNT(p) FROM Proveedor p WHERE p.estado = 1")
    Long countProveedoresActivos();
    
    // Verificar si existe un proveedor con el mismo RUC
    @Query("SELECT COUNT(p) FROM Proveedor p WHERE p.ruc = :ruc AND p.estado = 1")
    Long countByRuc(@Param("ruc") String ruc);
}
