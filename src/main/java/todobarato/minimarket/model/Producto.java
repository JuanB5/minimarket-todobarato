package todobarato.minimarket.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    
    @Id
    @Column(name = "codigo", length = 10)
    private String codigo;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "stock", nullable = false)
    @Builder.Default
    private Integer stock = 0;
    
    @Column(name = "stock_minimo", nullable = false)
    @Builder.Default
    private Integer stockMinimo = 0;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;
    
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
    public boolean isStockBajo() {
        return this.stock != null && this.stockMinimo != null && this.stock <= this.stockMinimo;
    }
    
    public boolean isProductoActivo() {
        return this.estado != null && this.estado == 1;
    }
    
    public String getEstadoTexto() {
        return this.estado != null && this.estado == 1 ? "Activo" : "Inactivo";
    }
    
    public String getCodigoTodoBarato() {
        return "TB-" + this.codigo;
    }
    
    public void actualizarFechaModificacion() {
        this.fechaModificacion = LocalDateTime.now();
    }
    
    public String getAlertaStock() {
        if (isStockBajo()) {
            return "⚠️ STOCK BAJO";
        }
        return "✅ Stock OK";
    }
    
    public String getPrecioFormateado() {
        return "S/ " + this.precio.toString();
    }
    
    @Override
    public String toString() {
        return "Producto TODO BARATO [código=" + codigo + ", nombre=" + nombre + ", precio=S/ " + precio + "]";
    }
}
