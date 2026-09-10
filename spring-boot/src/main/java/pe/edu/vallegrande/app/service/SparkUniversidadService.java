package pe.edu.vallegrande.app.service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SparkUniversidadService {

    private final SparkSession spark;

    // PostgreSQL (estudiante)
    private final String postgresUrl;
    private final String postgresUsername;
    private final String postgresPassword;
    private final String postgresDriver;

    // SQL Server (carrera)
    private final String sqlServerUrl;
    private final String sqlServerUsername;
    private final String sqlServerPassword;
    private final String sqlServerDriver;

    // Oracle (matricula)
    private final String oracleUrl;
    private final String oracleUsername;
    private final String oraclePassword;
    private final String oracleDriver;

    public SparkUniversidadService(
            @Value("${app.postgres.url}") String postgresUrl,
            @Value("${app.postgres.username}") String postgresUsername,
            @Value("${app.postgres.password}") String postgresPassword,
            @Value("${app.postgres.driver}") String postgresDriver,

            @Value("${app.sqlserver.url}") String sqlServerUrl,
            @Value("${app.sqlserver.username}") String sqlServerUsername,
            @Value("${app.sqlserver.password}") String sqlServerPassword,
            @Value("${app.sqlserver.driver}") String sqlServerDriver,

            @Value("${app.oracle.url}") String oracleUrl,
            @Value("${app.oracle.username}") String oracleUsername,
            @Value("${app.oracle.password}") String oraclePassword,
            @Value("${app.oracle.driver}") String oracleDriver
    ) {
        this.postgresUrl = postgresUrl;
        this.postgresUsername = postgresUsername;
        this.postgresPassword = postgresPassword;
        this.postgresDriver = postgresDriver;

        this.sqlServerUrl = sqlServerUrl;
        this.sqlServerUsername = sqlServerUsername;
        this.sqlServerPassword = sqlServerPassword;
        this.sqlServerDriver = sqlServerDriver;

        this.oracleUrl = oracleUrl;
        this.oracleUsername = oracleUsername;
        this.oraclePassword = oraclePassword;
        this.oracleDriver = oracleDriver;

        this.spark = SparkSession.builder()
                .appName("UniversidadSparkDemo")
                .master("local[*]")
                .getOrCreate();
    }

    public Dataset<Row> obtenerResumen() {

        Dataset<Row> estudiante = leerEstudiante(); // Postgres
        Dataset<Row> carrera = leerCarrera();       // SQL Server
        Dataset<Row> matricula = leerMatricula();   // Oracle

        Dataset<Row> resultado = matricula
                .join(
                        estudiante,
                        matricula.col("id_estudiante").equalTo(estudiante.col("id_estudiante"))
                )
                .join(
                        carrera,
                        matricula.col("id_carrera").equalTo(carrera.col("id_carrera"))
                )
                .select(
                        matricula.col("id_matricula"),
                        estudiante.col("nombre"),
                        estudiante.col("apellido"),
                        carrera.col("nombre").as("carrera_nombre"),
                        matricula.col("fecha_matricula")
                );

        return resultado;
    }

    // Conexión a PostgreSQL: tabla estudiante
    private Dataset<Row> leerEstudiante() {
        return spark.read()
                .format("jdbc")
                .option("url", postgresUrl)
                .option("dbtable", "public.estudiante")
                .option("user", postgresUsername)
                .option("password", postgresPassword)
                .option("driver", postgresDriver)
                .load();
    }

    // Conexión a SQL Server: tabla carrera
    private Dataset<Row> leerCarrera() {
        return spark.read()
                .format("jdbc")
                .option("url", sqlServerUrl)
                .option("dbtable", "dbo.carrera")
                .option("user", sqlServerUsername)
                .option("password", sqlServerPassword)
                .option("driver", sqlServerDriver)
                .load();
    }

    // Conexión a Oracle: tabla matricula
    private Dataset<Row> leerMatricula() {
        return spark.read()
                .format("jdbc")
                .option("url", oracleUrl)
                .option("dbtable", "matricula")
                .option("user", oracleUsername)
                .option("password", oraclePassword)
                .option("driver", oracleDriver)
                .load();
    }
}