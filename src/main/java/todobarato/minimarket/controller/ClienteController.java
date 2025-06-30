package todobarato.minimarket.controller;

import todobarato.minimarket.model.Cliente;
import todobarato.minimarket.repository.IClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/todobarato/clientes")
public class ClienteController {
    
    @Autowired
    private IClienteRepository clienteRepository;
    
    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String busqueda) {
        try {
            if (busqueda != null && !busqueda.trim().isEmpty()) {
                model.addAttribute("clientes", clienteRepository.findByNombreOrApellidoOrDniContaining(busqueda.trim()));
                model.addAttribute("busqueda", busqueda);
            } else {
                model.addAttribute("clientes", clienteRepository.findClientesActivos());
            }
            
            model.addAttribute("titulo", "Gestión de Clientes - TODO BARATO");
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar clientes: " + e.getMessage());
        }
        
        return "clientes/listar";
    }
    
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente - TODO BARATO");
        model.addAttribute("accion", "Registrar");
        return "clientes/formulario";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteRepository.findById(id).orElse(null);
            if (cliente == null || cliente.getEstado() == 0) {
                redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
                return "redirect:/todobarato/clientes";
            }
            
            model.addAttribute("cliente", cliente);
            model.addAttribute("titulo", "Editar Cliente - TODO BARATO");
            model.addAttribute("accion", "Actualizar");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cargar cliente: " + e.getMessage());
            return "redirect:/todobarato/clientes";
        }
        
        return "clientes/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            // Validaciones básicas
            if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El nombre del cliente es obligatorio");
                return "redirect:/clientes/nuevo";
            }
            
            if (cliente.getApellido() == null || cliente.getApellido().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El apellido del cliente es obligatorio");
                return "redirect:/clientes/nuevo";
            }
            
            if (cliente.getDni() != null && !cliente.getDni().trim().isEmpty()) {
                if (cliente.getDni().length() != 8) {
                    redirectAttributes.addFlashAttribute("error", "El DNI debe tener 8 dígitos");
                    return "redirect:/todobarato/clientes/nuevo";
                }
                
                // Verificar DNI duplicado
                Cliente clienteExistente = clienteRepository.findByDni(cliente.getDni());
                if (clienteExistente != null && !clienteExistente.getIdCliente().equals(cliente.getIdCliente())) {
                    redirectAttributes.addFlashAttribute("error", "Ya existe un cliente con ese DNI");
                    return "redirect:/todobarato/clientes/nuevo";
                }
            }
            
            // Verificar si es nuevo o actualización
            boolean esNuevo = cliente.getIdCliente() == null;
            
            if (esNuevo) {
                cliente.setEstado(1);
            } else {
                cliente.actualizarFechaModificacion();
            }
            
            clienteRepository.save(cliente);
            
            String mensaje = esNuevo ? "Cliente registrado exitosamente en TODO BARATO" : "Cliente actualizado exitosamente";
            redirectAttributes.addFlashAttribute("success", mensaje);
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar cliente: " + e.getMessage());
        }
        
        return "redirect:/todobarato/clientes";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteRepository.findById(id).orElse(null);
            if (cliente != null) {
                cliente.setEstado(0);
                cliente.actualizarFechaModificacion();
                clienteRepository.save(cliente);
                redirectAttributes.addFlashAttribute("success", "Cliente eliminado exitosamente de TODO BARATO");
            } else {
                redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar cliente: " + e.getMessage());
        }
        
        return "redirect:/todobarato/clientes";
    }
}
