#!/bin/bash
# Esperar a que SQL Server inicie
sleep 15s

# Ejecutar el script SQL de carrera
/opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "Admin12345" -C -i /docker-entrypoint-initdb.d/carrera.sql