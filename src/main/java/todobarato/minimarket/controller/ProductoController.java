package todobarato.minimarket.controller;

import todobarato.minimarket.model.Producto;
import todobarato.minimarket.repository.ICategoriaRepository;
import todobarato.minimarket.repository.IProductoRepository;
import todobarato.minimarket.repository.IProveedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todobarato/productos")
public class ProductoController {
    
    @Autowired
    private IProductoRepository productoRepository;
    
    @Autowired
    private ICategoriaRepository categoriaRepository;
    
    @Autowired
    private IProveedorRepository proveedorRepository;
    
    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String busqueda) {
        try {
            if (busqueda != null && !busqueda.trim().isEmpty()) {
                model.addAttribute("productos", productoRepository.findByNombreOrCodigoContaining(busqueda.trim()));
                model.addAttribute("busqueda", busqueda);
            } else {
                model.addAttribute("productos", productoRepository.findProductosActivos());
            }
            
            model.addAttribute("titulo", "Gestión de Productos - TODO BARATO");
            model.addAttribute("productosStockBajo", productoRepository.findProductosStockBajo());
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar productos: " + e.getMessage());
        }
        
        return "productos/listar";
    }
    
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaRepository.findCategoriasActivas());
        model.addAttribute("proveedores", proveedorRepository.findProveedoresActivos());
        model.addAttribute("titulo", "Nuevo Producto - TODO BARATO");
        model.addAttribute("accion", "Registrar");
        return "productos/formulario";
    }
    
    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable String codigo, Model model, RedirectAttributes redirectAttributes) {
        try {
            Producto producto = productoRepository.findById(codigo).orElse(null);
            if (producto == null || producto.getEstado() == 0) {
                redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
                return "redirect:/todobarato/productos";
            }
            
            model.addAttribute("producto", producto);
            model.addAttribute("categorias", categoriaRepository.findCategoriasActivas());
            model.addAttribute("proveedores", proveedorRepository.findProveedoresActivos());
            model.addAttribute("titulo", "Editar Producto - TODO BARATO");
            model.addAttribute("accion", "Actualizar");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar producto: " + e.getMessage());
            return "redirect:/todobarato/productos";
        }
        
        return "productos/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto, RedirectAttributes redirectAttributes) {
        try {
            // Validaciones básicas
            if (producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El código del producto es obligatorio");
                return "redirect:/todobarato/productos/nuevo";
            }
            
            if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El nombre del producto es obligatorio");
                return "redirect:/todobarato/productos/nuevo";
            }
            
            if (producto.getPrecio() == null || producto.getPrecio().doubleValue() <= 0) {
                redirectAttributes.addFlashAttribute("error", "El precio debe ser mayor a 0");
                return "redirect:/todobarato/productos/nuevo";
            }
            
            // Verificar si es nuevo o actualización
            boolean esNuevo = !productoRepository.existsById(producto.getCodigo());
            
            if (esNuevo) {
                producto.setEstado(1);
            } else {
                producto.actualizarFechaModificacion();
            }
            
            productoRepository.save(producto);
            
            String mensaje = esNuevo ? "Producto registrado exitosamente en TODO BARATO" : "Producto actualizado exitosamente";
            redirectAttributes.addFlashAttribute("success", mensaje);
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar producto: " + e.getMessage());
        }
        
        return "redirect:/todobarato/productos";
    }
    
    @GetMapping("/eliminar/{codigo}")
    public String eliminar(@PathVariable String codigo, RedirectAttributes redirectAttributes) {
        try {
            Producto producto = productoRepository.findById(codigo).orElse(null);
            if (producto != null) {
                // Eliminación lógica
                producto.setEstado(0);
                producto.actualizarFechaModificacion();
                productoRepository.save(producto);
                redirectAttributes.addFlashAttribute("success", "Producto eliminado exitosamente de TODO BARATO");
            } else {
                redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar producto: " + e.getMessage());
        }
        
        return "redirect:/todobarato/productos";
    }
    
    @GetMapping("/stock-bajo")
    public String stockBajo(Model model) {
        try {
            model.addAttribute("productos", productoRepository.findProductosStockBajo());
            model.addAttribute("titulo", "Productos con Stock Bajo - TODO BARATO");
            model.addAttribute("alerta", true);
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar productos con stock bajo: " + e.getMessage());
        }
        
        model.addAttribute("productosStockBajo", productoRepository.findProductosStockBajo());
        
        return "productos/listar";
    }
}
