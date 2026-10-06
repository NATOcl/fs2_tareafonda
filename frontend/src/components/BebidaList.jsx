import { useState } from "react";
import { Badge, Button, Form, InputGroup, Spinner, Table } from "react-bootstrap";

const peso = (n) => (n == null ? "-" : `$${Number(n).toLocaleString("es-CL")}`);

/** Datos propios de cada tipo de bebida. */
function detalle(b) {
  if (b.tipo === "ALCOHOLICA") {
    return `${b.gradosAlcohol ?? "-"}° · ${b.certificada ? "Certificada" : "No certificada"}`;
  }
  return `Azúcar: ${b.azucarPorLitro ?? "-"} g/L`;
}

export default function BebidaList({
  bebidas, cargando, onBuscar, onNueva, onEditar, onEliminar, onRestringir, onVender,
}) {
  const [texto, setTexto] = useState("");

  const buscar = (e) => {
    e.preventDefault();
    onBuscar(texto); // el filtro lo aplica el servidor
  };
  const limpiar = () => {
    setTexto("");
    onBuscar("");
  };

  return (
    <>
      <div className="d-flex flex-wrap gap-2 justify-content-between mb-3">
        <Form onSubmit={buscar} style={{ minWidth: 280 }}>
          <InputGroup>
            <Form.Control
              placeholder="Buscar por nombre"
              value={texto}
              onChange={(e) => setTexto(e.target.value)}
            />
            <Button type="submit" variant="primary">Buscar</Button>
            <Button variant="outline-primary" onClick={limpiar}>Limpiar</Button>
          </InputGroup>
        </Form>
        <Button variant="primary" onClick={onNueva}>Nueva bebida</Button>
      </div>

      {cargando ? (
        <div className="text-center py-5">
          <Spinner animation="border" role="status" />
          <p className="text-muted mt-2">Cargando bebidas...</p>
        </div>
      ) : bebidas.length === 0 ? (
        <div className="text-center text-muted py-5">
          No hay bebidas para mostrar. Crea una con «Nueva bebida» o cambia la búsqueda.
        </div>
      ) : (
        <div className="table-responsive">
          <Table hover className="tabla-fonda bg-white">
            <thead>
              <tr>
                <th>Nombre</th><th>Tipo</th><th>Volumen</th><th>Stock</th>
                <th>Detalle</th><th>Precio</th><th>Estado</th><th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {bebidas.map((b) => (
                <tr key={b.id}>
                  <td>{b.nombre}</td>
                  <td>{b.tipo === "ALCOHOLICA" ? "Alcohólica" : "Sin alcohol"}</td>
                  <td>{b.volumenML} ml</td>
                  <td>{b.stock}</td>
                  <td>{detalle(b)}</td>
                  <td>{peso(b.precio)}</td>
                  <td>
                    {b.ventaRestringida
                      ? <Badge bg="danger">Restringida</Badge>
                      : <Badge bg="success">Disponible</Badge>}
                  </td>
                  <td className="text-nowrap">
                    <Button size="sm" variant="primary" className="me-1"
                      disabled={b.ventaRestringida} onClick={() => onVender(b)}>Vender</Button>
                    <Button size="sm" variant="outline-primary" className="me-1"
                      onClick={() => onEditar(b)}>Editar</Button>
                    <Button size="sm" variant="outline-secondary" className="me-1"
                      disabled={b.ventaRestringida} onClick={() => onRestringir(b)}>Restringir</Button>
                    <Button size="sm" variant="outline-danger"
                      onClick={() => onEliminar(b)}>Eliminar</Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      )}
    </>
  );
}
