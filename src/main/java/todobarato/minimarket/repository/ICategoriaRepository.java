package todobarato.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import todobarato.minimarket.model.Categoria;

import java.util.List;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    // Buscar categorías activas
    @Query("SELECT c FROM Categoria c WHERE c.estado = 1 ORDER BY c.nombre")
    List<Categoria> findCategoriasActivas();
    
    // Buscar por nombre
    @Query("SELECT c FROM Categoria c WHERE UPPER(c.nombre) LIKE UPPER(CONCAT('%', :nombre, '%')) AND c.estado = 1")
    List<Categoria> findByNombreContaining(@Param("nombre") String nombre);
    
    // Contar categorías activas
    @Query("SELECT COUNT(c) FROM Categoria c WHERE c.estado = 1")
    Long countCategoriasActivas();
    
    // Verificar si existe una categoría con el mismo nombre
    @Query("SELECT COUNT(c) FROM Categoria c WHERE UPPER(c.nombre) = UPPER(:nombre) AND c.estado = 1")
    Long countByNombre(@Param("nombre") String nombre);
}
