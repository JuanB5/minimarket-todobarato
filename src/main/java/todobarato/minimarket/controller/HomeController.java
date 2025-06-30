package todobarato.minimarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import todobarato.minimarket.repository.ICategoriaRepository;
import todobarato.minimarket.repository.IClienteRepository;
import todobarato.minimarket.repository.IProductoRepository;
import todobarato.minimarket.repository.IProveedorRepository;

@Controller
public class HomeController {
    
    @Autowired
    private IProductoRepository productoRepository;
    
    @Autowired
    private ICategoriaRepository categoriaRepository;
    
    @Autowired
    private IProveedorRepository proveedorRepository;
    
    @Autowired
    private IClienteRepository clienteRepository;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            // Estadísticas generales
            Long totalProductos = productoRepository.countProductosActivos();
            Long totalCategorias = categoriaRepository.countCategoriasActivas();
            Long totalProveedores = proveedorRepository.countProveedoresActivos();
            Long totalClientes = clienteRepository.countClientesActivos();
            Long productosStockBajo = productoRepository.countProductosStockBajo();
            
            // Datos para el dashboard
            model.addAttribute("totalProductos", totalProductos != null ? totalProductos : 0);
            model.addAttribute("totalCategorias", totalCategorias != null ? totalCategorias : 0);
            model.addAttribute("totalProveedores", totalProveedores != null ? totalProveedores : 0);
            model.addAttribute("totalClientes", totalClientes != null ? totalClientes : 0);
            model.addAttribute("productosStockBajo", productosStockBajo != null ? productosStockBajo : 0);
            
            // Productos con stock bajo para alertas
            model.addAttribute("alertasStock", productoRepository.findProductosStockBajo());
            
            // Productos más vendidos (simulado)
            model.addAttribute("productosMasVendidos", productoRepository.findProductosMasVendidos().stream().limit(5).toList());
            
            // Clientes recientes
            model.addAttribute("clientesRecientes", clienteRepository.findClientesRecientes().stream().limit(5).toList());
            
            model.addAttribute("titulo", "Dashboard - TODO BARATO");
            model.addAttribute("mensaje", "Bienvenido al Sistema de Inventario TODO BARATO");
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el dashboard: " + e.getMessage());
        }
        
        return "dashboard";
    }
}
