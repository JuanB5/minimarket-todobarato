package todobarato.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import todobarato.minimarket.model.Producto;

import java.util.List;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, String> {
    
    // Buscar productos activos
    @Query("SELECT p FROM Producto p WHERE p.estado = 1")
    List<Producto> findProductosActivos();
    
    // Buscar productos con stock bajo
    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo AND p.estado = 1")
    List<Producto> findProductosStockBajo();
    
    // Buscar por nombre o código
    @Query("SELECT p FROM Producto p WHERE (UPPER(p.nombre) LIKE UPPER(CONCAT('%', :busqueda, '%')) OR UPPER(p.codigo) LIKE UPPER(CONCAT('%', :busqueda, '%'))) AND p.estado = 1")
    List<Producto> findByNombreOrCodigoContaining(@Param("busqueda") String busqueda);
    
    // Buscar por categoría
    @Query("SELECT p FROM Producto p WHERE p.categoria.idCategoria = :idCategoria AND p.estado = 1")
    List<Producto> findByCategoriaId(@Param("idCategoria") Integer idCategoria);
    
    // Buscar por proveedor
    @Query("SELECT p FROM Producto p WHERE p.proveedor.idProveedor = :idProveedor AND p.estado = 1")
    List<Producto> findByProveedorId(@Param("idProveedor") Integer idProveedor);
    
    // Contar productos activos
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.estado = 1")
    Long countProductosActivos();
    
    // Contar productos con stock bajo
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.stock <= p.stockMinimo AND p.estado = 1")
    Long countProductosStockBajo();
    
    // Productos más vendidos (simulado por stock bajo)
    @Query("SELECT p FROM Producto p WHERE p.estado = 1 ORDER BY p.stock ASC")
    List<Producto> findProductosMasVendidos();
}
