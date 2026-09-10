CREATE TABLE matricula (
    id_matricula NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_estudiante NUMBER NOT NULL,
    id_carrera NUMBER NOT NULL,
    fecha_matricula DATE DEFAULT SYSDATE NOT NULL
);

INSERT INTO matricula (id_estudiante, id_carrera, fecha_matricula) VALUES (1, 1, TO_DATE('2026-03-01', 'YYYY-MM-DD'));
INSERT INTO matricula (id_estudiante, id_carrera, fecha_matricula) VALUES (2, 2, TO_DATE('2026-03-02', 'YYYY-MM-DD'));
INSERT INTO matricula (id_estudiante, id_carrera, fecha_matricula) VALUES (3, 1, TO_DATE('2026-03-03', 'YYYY-MM-DD'));

COMMIT;