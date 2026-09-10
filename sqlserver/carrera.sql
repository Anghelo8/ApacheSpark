CREATE DATABASE universidad;
GO

USE universidad;
GO

CREATE TABLE dbo.carrera (
    id_carrera INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    duracion_anios INT NOT NULL
);

INSERT INTO dbo.carrera (nombre, duracion_anios) VALUES
('Ingeniería de Sistemas', 5),
('Administración de Empresas', 4),
('Diseño Gráfico', 3);
GO