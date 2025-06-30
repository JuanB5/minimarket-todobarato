package todobarato.minimarket.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "ruc", length = 11)
    private String ruc;
    
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    @Column(name = "direccion", length = 200)
    private String direccion;
    
    @Column(name = "email", length = 100)
    private String email;
    
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
    public boolean isActivo() {
        return this.estado != null && this.estado == 1;
    }
    
    public String getEstadoTexto() {
        return this.estado != null && this.estado == 1 ? "Activo" : "Inactivo";
    }
    
    public void actualizarFechaModificacion() {
        this.fechaModificacion = LocalDateTime.now();
    }
    
    public String getCodigoProveedor() {
        return "PROV-" + String.format("%04d", this.idProveedor);
    }
    
    @Override
    public String toString() {
        return "Proveedor TODO BARATO [ID=" + idProveedor + ", nombre=" + nombre + ", RUC=" + ruc + "]";
    }
}
