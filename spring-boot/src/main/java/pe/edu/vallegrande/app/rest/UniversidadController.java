package pe.edu.vallegrande.app.rest;

import pe.edu.vallegrande.app.service.SparkUniversidadService;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/universidad")
public class UniversidadController {

    private final SparkUniversidadService sparkService;

    public UniversidadController(SparkUniversidadService sparkService) {
        this.sparkService = sparkService;
    }

    @GetMapping("/matriculas")
    public List<Map<String, Object>> obtenerMatriculas() {

        Dataset<Row> resultado = sparkService.obtenerResumen();

        return resultado.collectAsList()
                .stream()
                .map(row -> Map.<String, Object>of(
                        "idMatricula", row.getAs("id_matricula"),
                        "nombre", row.getAs("nombre"),
                        "apellido", row.getAs("apellido"),
                        "carrera", row.getAs("carrera_nombre"),
                        "fechaMatricula", row.getAs("fecha_matricula").toString()
                ))
                .toList();
    }
}