-- Base de datos para TODO BARATO Minimarket
-- Sistema de Inventario y Gestión

-- Crear base de datos
DROP DATABASE IF EXISTS todobarato;
CREATE DATABASE todobarato CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE todobarato;

-- Tabla de Categorías
CREATE TABLE tb_categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100),
    estado INT NOT NULL DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Proveedores
CREATE TABLE tb_proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ruc VARCHAR(11),
    telefono VARCHAR(15),
    direccion VARCHAR(200),
    email VARCHAR(100),
    estado INT NOT NULL DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de Productos
CREATE TABLE tb_producto (
    codigo VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    stock_minimo INT NOT NULL DEFAULT 0,
    id_categoria INT,
    id_proveedor INT,
    estado INT NOT NULL DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_categoria) REFERENCES tb_categoria(id_categoria),
    FOREIGN KEY (id_proveedor) REFERENCES tb_proveedor(id_proveedor)
);

-- Tabla de Clientes
CREATE TABLE tb_cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(8),
    telefono VARCHAR(15),
    direccion VARCHAR(200),
    email VARCHAR(100),
    estado INT NOT NULL DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insertar datos iniciales para TODO BARATO

-- Categorías
INSERT INTO tb_categoria (nombre, descripcion) VALUES
('Abarrotes', 'Productos de consumo básico y alimentos no perecederos'),
('Bebidas', 'Gaseosas, jugos, agua y bebidas en general'),
('Lácteos', 'Leche, yogurt, quesos y derivados lácteos'),
('Limpieza', 'Productos de limpieza para el hogar'),
('Cuidado Personal', 'Productos de higiene y cuidado personal'),
('Snacks', 'Galletas, dulces y productos para picar'),
('Carnes y Embutidos', 'Productos cárnicos y embutidos'),
('Frutas y Verduras', 'Productos frescos del campo');

-- Proveedores
INSERT INTO tb_proveedor (nombre, ruc, telefono, direccion, email) VALUES
('Distribuidora San Miguel SAC', '20123456789', '014567890', 'Av. Industrial 123, Lima', 'ventas@sanmiguel.com'),
('Alimentos del Norte EIRL', '20234567890', '014567891', 'Jr. Comercio 456, Trujillo', 'pedidos@alimentosnorte.com'),
('Productos Andinos SA', '20345678901', '014567892', 'Av. Los Andes 789, Huancayo', 'contacto@andinos.com'),
('Bebidas Refrescantes SAC', '20456789012', '014567893', 'Calle Industrial 321, Arequipa', 'ventas@refrescantes.com'),
('Limpieza Total EIRL', '20567890123', '014567894', 'Av. Progreso 654, Chiclayo', 'info@limpiezatotal.com'),
('Lácteos La Vaca Feliz SA', '20678901234', '014567895', 'Jr. Ganadero 987, Cajamarca', 'pedidos@vacafeliz.com');

-- Productos TODO BARATO
INSERT INTO tb_producto (codigo, nombre, descripcion, precio, stock, stock_minimo, id_categoria, id_proveedor) VALUES
-- Abarrotes
('TB001', 'Arroz Superior x 5kg', 'Arroz de grano largo, calidad superior', 18.50, 50, 10, 1, 1),
('TB002', 'Aceite Vegetal x 1L', 'Aceite vegetal para cocinar', 8.90, 30, 5, 1, 1),
('TB003', 'Azúcar Blanca x 1kg', 'Azúcar refinada blanca', 3.20, 40, 8, 1, 2),
('TB004', 'Sal de Mesa x 1kg', 'Sal yodada para consumo', 1.50, 60, 12, 1, 2),
('TB005', 'Fideos Espagueti x 500g', 'Pasta de trigo espagueti', 2.80, 45, 10, 1, 1),

-- Bebidas
('TB006', 'Gaseosa Cola x 2L', 'Bebida gaseosa sabor cola', 4.50, 25, 5, 2, 4),
('TB007', 'Agua Mineral x 625ml', 'Agua mineral natural sin gas', 1, 22, 5, 1, 3)
