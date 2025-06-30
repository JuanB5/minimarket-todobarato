package todobarato.minimarket.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_categoria")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;
    
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    
    @Column(name = "descripcion", length = 100)
    private String descripcion;
    
    @Column(name = "estado", nullable = false)
    @Builder.Default
    private Integer estado = 1;
    
    @Column(name = "fecha_creacion")
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_modificacion")
    @Builder.Default
    private LocalDateTime fechaModificacion = LocalDateTime.now();
    
    // Métodos de negocio específicos para TODO BARATO
    public boolean isActiva() {
        return this.estado != null && this.estado == 1;
    }
    
    public String getEstadoTexto() {
        return this.estado != null && this.estado == 1 ? "Activa" : "Inactiva";
    }
    
    public void actualizarFechaModificacion() {
        this.fechaModificacion = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Categoria TODO BARATO [ID=" + idCategoria + ", nombre=" + nombre + "]";
    }
}
