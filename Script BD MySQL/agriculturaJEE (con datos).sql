-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 29, 2025 at 02:00 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `agricultura`
--
CREATE DATABASE IF NOT EXISTS agriculturaJEE;
USE agriculturaJEE;

-- --------------------------------------------------------

--
-- Table structure for table `facturas`
--

CREATE TABLE `facturas` (
  `ID_Factura` int(11) NOT NULL,
  `ID_Trabajo` int(11) NOT NULL,
  `Estado` enum('Pendiente de generar','Pendiente de pagar','Pagada') DEFAULT 'Pendiente de generar',
  `Fecha_Emision` date DEFAULT NULL,
  `Fecha_Pago` date DEFAULT NULL,
  `Monto` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `facturas`
--

INSERT INTO `facturas` (`ID_Factura`, `ID_Trabajo`, `Estado`, `Fecha_Emision`, `Fecha_Pago`, `Monto`) VALUES
(1, 1, 'Pagada', '2025-01-29', '2025-01-29', 2640.00),
(2, 2, 'Pagada', '2025-01-29', '2025-01-29', 5580.00);

-- --------------------------------------------------------

--
-- Table structure for table `maquinas`
--

CREATE TABLE `maquinas` (
  `ID_Maquina` int(11) NOT NULL,
  `Estado` enum('Disponible','Asignada','Fuera de servicio') NOT NULL,
  `Tipo_Maquina` int(11) NOT NULL,
  `Modelo` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `maquinas`
--

INSERT INTO `maquinas` (`ID_Maquina`, `Estado`, `Tipo_Maquina`, `Modelo`) VALUES
(1, 'Disponible', 1, 'm1'),
(2, 'Disponible', 1, 'm1'),
(3, 'Disponible', 2, 'm1'),
(4, 'Disponible', 2, 'm2'),
(5, 'Disponible', 3, 'm1'),
(6, 'Disponible', 3, 'm4'),
(7, 'Disponible', 4, 'm1'),
(8, 'Disponible', 5, 'm2'),
(9, 'Disponible', 5, 'm2'),
(10, 'Disponible', 5, 'm3'),
(11, 'Disponible', 6, 'm1'),
(12, 'Disponible', 6, 'm5'),
(13, 'Disponible', 7, 'm1'),
(14, 'Disponible', 7, 'm19');

-- --------------------------------------------------------

--
-- Table structure for table `maquinista_tipo_trabajo`
--

CREATE TABLE `maquinista_tipo_trabajo` (
  `ID_Maquinista` int(11) NOT NULL,
  `ID_Tipo_Trabajo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `maquinista_tipo_trabajo`
--

INSERT INTO `maquinista_tipo_trabajo` (`ID_Maquinista`, `ID_Tipo_Trabajo`) VALUES
(5, 1),
(5, 5),
(6, 1),
(6, 2),
(6, 3),
(7, 4),
(7, 5),
(7, 6),
(8, 1),
(8, 2),
(8, 6),
(8, 7);

-- --------------------------------------------------------

--
-- Table structure for table `parcelas`
--

CREATE TABLE `parcelas` (
  `Num_Parcela` int(11) NOT NULL,
  `ID_Catastro` varchar(50) NOT NULL,
  `Extension` decimal(10,2) NOT NULL,
  `Propietario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parcelas`
--

INSERT INTO `parcelas` (`Num_Parcela`, `ID_Catastro`, `Extension`, `Propietario`) VALUES
(1, '0001', 11.00, 2),
(2, '0002', 32.00, 2),
(3, '0003', 8.00, 3),
(4, '0004', 8.00, 3),
(5, '0005', 41.00, 4),
(6, '0006', 19.00, 4),
(7, '0007', 24.00, 4),
(8, '0008', 11.00, 8),
(9, '0009', 31.00, 8),
(10, '0010', 51.00, 8);

-- --------------------------------------------------------

--
-- Table structure for table `puntos`
--

CREATE TABLE `puntos` (
  `ID_Punto` int(11) NOT NULL,
  `ID_Parcelas` int(11) NOT NULL,
  `Longitud` decimal(10,6) NOT NULL,
  `Latitud` decimal(10,6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `ID_Rol` int(11) NOT NULL,
  `Nombre` enum('Agricultor','Administrador','Maquinista') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`ID_Rol`, `Nombre`) VALUES
(1, 'Administrador'),
(2, 'Agricultor'),
(3, 'Maquinista');

-- --------------------------------------------------------

--
-- Table structure for table `tipo_trabajo`
--

CREATE TABLE `tipo_trabajo` (
  `ID_Tipo_Trabajo` int(11) NOT NULL,
  `Nombre` enum('Arar','Cosechar','Sembrar','Fumigar','Fertilizar','Podar','Empacar') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tipo_trabajo`
--

INSERT INTO `tipo_trabajo` (`ID_Tipo_Trabajo`, `Nombre`) VALUES
(1, 'Arar'),
(2, 'Cosechar'),
(3, 'Sembrar'),
(4, 'Fumigar'),
(5, 'Fertilizar'),
(6, 'Podar'),
(7, 'Empacar');

-- --------------------------------------------------------

--
-- Table structure for table `trabajos`
--

CREATE TABLE `trabajos` (
  `ID_Trabajo` int(11) NOT NULL,
  `Num_parcela` int(11) NOT NULL,
  `ID_Maquina` int(11) NOT NULL,
  `ID_Maquinista` int(11) NOT NULL,
  `Fec_inicio` date DEFAULT NULL,
  `Fec_fin` date DEFAULT NULL,
  `Horas` int(11) DEFAULT NULL,
  `Tipo` int(11) NOT NULL,
  `Estado` enum('Pendiente','En curso','Finalizado','') NOT NULL DEFAULT 'Pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trabajos`
--

INSERT INTO `trabajos` (`ID_Trabajo`, `Num_parcela`, `ID_Maquina`, `ID_Maquinista`, `Fec_inicio`, `Fec_fin`, `Horas`, `Tipo`, `Estado`) VALUES
(1, 1, 7, 7, '2025-01-29', '2025-01-30', 8, 4, 'Finalizado'),
(2, 9, 4, 8, '2025-01-29', '2025-01-29', 6, 2, 'Finalizado');

-- --------------------------------------------------------

--
-- Table structure for table `trabajos_solicitados`
--

CREATE TABLE `trabajos_solicitados` (
  `ID_Solicitud` int(11) NOT NULL,
  `Num_Parcela` int(11) NOT NULL,
  `Propietario` int(11) NOT NULL,
  `ID_Tipo_Trabajo` int(11) NOT NULL,
  `Estado` enum('En revision','Aprobado','Rechazado') DEFAULT 'En revision',
  `Fecha_Solicitud` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `trabajos_solicitados`
--

INSERT INTO `trabajos_solicitados` (`ID_Solicitud`, `Num_Parcela`, `Propietario`, `ID_Tipo_Trabajo`, `Estado`, `Fecha_Solicitud`) VALUES
(1, 1, 2, 4, 'Aprobado', '2025-01-29'),
(2, 9, 8, 2, 'Aprobado', '2025-01-29');

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE `usuarios` (
  `ID_Usuario` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Contrasenia` varchar(255) NOT NULL,
  `Telefono` varchar(15) DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  `Habilitado` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`ID_Usuario`, `Nombre`, `Contrasenia`, `Telefono`, `Email`, `Habilitado`) VALUES
(1, 'admin', 'pass', '123456789', 'admin@email.com', 1),
(2, 'agricultor1', 'pass', '123123123', 'agri1@email.com', 1),
(3, 'agricultor2', 'pass', '123213131', 'agri2@email.com', 1),
(4, 'agricultor3', 'pass', '1341256122', 'agri3@email.com', 1),
(5, 'maquinista1', 'pass', '141251325', 'maq1@email.com', 1),
(6, 'maquinista2', 'pass', '156132512', 'maq2@email.com', 1),
(7, 'maquinista3', 'pass', '581726512', 'maq3@email.com', 1),
(8, 'usuarioTotal', 'pass', '617298412', 'ut@email.com', 1);

-- --------------------------------------------------------

--
-- Table structure for table `usuarios_roles`
--

CREATE TABLE `usuarios_roles` (
  `ID_Usuario` int(11) NOT NULL,
  `ID_Rol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuarios_roles`
--

INSERT INTO `usuarios_roles` (`ID_Usuario`, `ID_Rol`) VALUES
(1, 1),
(2, 2),
(3, 2),
(4, 2),
(5, 3),
(6, 3),
(7, 3),
(8, 1),
(8, 2),
(8, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `facturas`
--
ALTER TABLE `facturas`
  ADD PRIMARY KEY (`ID_Factura`),
  ADD KEY `ID_Trabajo` (`ID_Trabajo`);

--
-- Indexes for table `maquinas`
--
ALTER TABLE `maquinas`
  ADD PRIMARY KEY (`ID_Maquina`),
  ADD KEY `Tipo_Maquina` (`Tipo_Maquina`);

--
-- Indexes for table `maquinista_tipo_trabajo`
--
ALTER TABLE `maquinista_tipo_trabajo`
  ADD PRIMARY KEY (`ID_Maquinista`,`ID_Tipo_Trabajo`),
  ADD KEY `ID_Tipo_Trabajo` (`ID_Tipo_Trabajo`);

--
-- Indexes for table `parcelas`
--
ALTER TABLE `parcelas`
  ADD PRIMARY KEY (`Num_Parcela`),
  ADD UNIQUE KEY `ID_Catastro` (`ID_Catastro`),
  ADD KEY `Propietario` (`Propietario`);

--
-- Indexes for table `puntos`
--
ALTER TABLE `puntos`
  ADD PRIMARY KEY (`ID_Punto`),
  ADD UNIQUE KEY `ID_Punto` (`ID_Punto`),
  ADD KEY `ID_Parcelas` (`ID_Parcelas`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`ID_Rol`);

--
-- Indexes for table `tipo_trabajo`
--
ALTER TABLE `tipo_trabajo`
  ADD PRIMARY KEY (`ID_Tipo_Trabajo`);

--
-- Indexes for table `trabajos`
--
ALTER TABLE `trabajos`
  ADD PRIMARY KEY (`ID_Trabajo`),
  ADD KEY `fk_Num_parcela` (`Num_parcela`),
  ADD KEY `ID_Maquina` (`ID_Maquina`),
  ADD KEY `Tipo` (`Tipo`),
  ADD KEY `trabajos_ibfk_3` (`ID_Maquinista`);

--
-- Indexes for table `trabajos_solicitados`
--
ALTER TABLE `trabajos_solicitados`
  ADD PRIMARY KEY (`ID_Solicitud`),
  ADD KEY `Num_Parcela` (`Num_Parcela`),
  ADD KEY `Propietario` (`Propietario`),
  ADD KEY `ID_Tipo_Trabajo` (`ID_Tipo_Trabajo`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`ID_Usuario`),
  ADD UNIQUE KEY `Email` (`Email`),
  ADD UNIQUE KEY `Nombre` (`Nombre`);

--
-- Indexes for table `usuarios_roles`
--
ALTER TABLE `usuarios_roles`
  ADD PRIMARY KEY (`ID_Usuario`,`ID_Rol`),
  ADD UNIQUE KEY `ID_Usuario` (`ID_Usuario`,`ID_Rol`),
  ADD KEY `ID_Rol` (`ID_Rol`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `facturas`
--
ALTER TABLE `facturas`
  MODIFY `ID_Factura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `maquinas`
--
ALTER TABLE `maquinas`
  MODIFY `ID_Maquina` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `parcelas`
--
ALTER TABLE `parcelas`
  MODIFY `Num_Parcela` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `puntos`
--
ALTER TABLE `puntos`
  MODIFY `ID_Punto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `ID_Rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `trabajos`
--
ALTER TABLE `trabajos`
  MODIFY `ID_Trabajo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `trabajos_solicitados`
--
ALTER TABLE `trabajos_solicitados`
  MODIFY `ID_Solicitud` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `ID_Usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `facturas`
--
ALTER TABLE `facturas`
  ADD CONSTRAINT `facturas_ibfk_1` FOREIGN KEY (`ID_Trabajo`) REFERENCES `trabajos` (`ID_Trabajo`);

--
-- Constraints for table `maquinas`
--
ALTER TABLE `maquinas`
  ADD CONSTRAINT `maquinas_ibfk_1` FOREIGN KEY (`Tipo_Maquina`) REFERENCES `tipo_trabajo` (`ID_Tipo_Trabajo`);

--
-- Constraints for table `maquinista_tipo_trabajo`
--
ALTER TABLE `maquinista_tipo_trabajo`
  ADD CONSTRAINT `maquinista_tipo_trabajo_ibfk_1` FOREIGN KEY (`ID_Maquinista`) REFERENCES `usuarios` (`ID_Usuario`),
  ADD CONSTRAINT `maquinista_tipo_trabajo_ibfk_2` FOREIGN KEY (`ID_Tipo_Trabajo`) REFERENCES `tipo_trabajo` (`ID_Tipo_Trabajo`);

--
-- Constraints for table `parcelas`
--
ALTER TABLE `parcelas`
  ADD CONSTRAINT `parcelas_ibfk_1` FOREIGN KEY (`Propietario`) REFERENCES `usuarios` (`ID_Usuario`);

--
-- Constraints for table `puntos`
--
ALTER TABLE `puntos`
  ADD CONSTRAINT `puntos_ibfk_1` FOREIGN KEY (`ID_Parcelas`) REFERENCES `parcelas` (`Num_Parcela`);

--
-- Constraints for table `trabajos`
--
ALTER TABLE `trabajos`
  ADD CONSTRAINT `trabajos_ibfk_1` FOREIGN KEY (`Num_parcela`) REFERENCES `parcelas` (`Num_Parcela`),
  ADD CONSTRAINT `trabajos_ibfk_2` FOREIGN KEY (`ID_Maquina`) REFERENCES `maquinas` (`ID_Maquina`),
  ADD CONSTRAINT `trabajos_ibfk_3` FOREIGN KEY (`ID_Maquinista`) REFERENCES `usuarios` (`ID_Usuario`),
  ADD CONSTRAINT `trabajos_ibfk_4` FOREIGN KEY (`Tipo`) REFERENCES `tipo_trabajo` (`ID_Tipo_Trabajo`);

--
-- Constraints for table `trabajos_solicitados`
--
ALTER TABLE `trabajos_solicitados`
  ADD CONSTRAINT `trabajos_solicitados_ibfk_1` FOREIGN KEY (`Num_Parcela`) REFERENCES `parcelas` (`Num_Parcela`),
  ADD CONSTRAINT `trabajos_solicitados_ibfk_2` FOREIGN KEY (`Propietario`) REFERENCES `usuarios` (`ID_Usuario`),
  ADD CONSTRAINT `trabajos_solicitados_ibfk_3` FOREIGN KEY (`ID_Tipo_Trabajo`) REFERENCES `tipo_trabajo` (`ID_Tipo_Trabajo`);

--
-- Constraints for table `usuarios_roles`
--
ALTER TABLE `usuarios_roles`
  ADD CONSTRAINT `usuarios_roles_ibfk_1` FOREIGN KEY (`ID_Usuario`) REFERENCES `usuarios` (`ID_Usuario`),
  ADD CONSTRAINT `usuarios_roles_ibfk_2` FOREIGN KEY (`ID_Rol`) REFERENCES `roles` (`ID_Rol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
