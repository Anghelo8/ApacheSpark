CREATE TABLE IF NOT EXISTS public.estudiante (
    id_estudiante SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100)
);

INSERT INTO public.estudiante (nombre, apellido, email) VALUES
('Juan', 'Pérez', 'juan.perez@example.com'),
('Maria', 'Gómez', 'maria.gomez@example.com'),
('Carlos', 'López', 'carlos.lopez@example.com');