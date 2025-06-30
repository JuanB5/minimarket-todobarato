package todobarato.minimarket.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    
    @Column(name = "dni", length = 8)
    private String dni;
    
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
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }
    
    public boolean isClienteActivo() {
        return this.estado != null && this.estado == 1;
    }
    
    public String getEstadoTexto() {
        return this.estado != null && this.estado == 1 ? "Cliente Activo" : "Cliente Inactivo";
    }
    
    public String getCodigoCliente() {
        return "CLI-" + String.format("%06d", this.idCliente);
    }
    
    public void actualizarFechaModificacion() {
        this.fechaModificacion = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Cliente TODO BARATO [ID=" + idCliente + ", nombre=" + getNombreCompleto() + ", DNI=" + dni + "]";
    }
}
