import { Badge, Spinner, Table } from "react-bootstrap";

const peso = (n) => (n == null ? "-" : `$${Number(n).toLocaleString("es-CL")}`);

export default function VentaHistorial({ ventas, cargando }) {
  if (cargando) {
    return (
      <div className="text-center py-5">
        <Spinner animation="border" role="status" />
        <p className="text-muted mt-2">Cargando ventas...</p>
      </div>
    );
  }
  if (ventas.length === 0) {
    return <div className="text-center text-muted py-5">Aún no hay ventas registradas.</div>;
  }

  const ordenadas = [...ventas].sort((a, b) => b.id - a.id);
  return (
    <div className="table-responsive">
      <Table hover className="tabla-fonda bg-white">
        <thead>
          <tr>
            <th>N°</th><th>Fecha</th><th>Bebida</th><th>Unidades</th>
            <th>Total</th><th>Estado</th><th>Motivo</th>
          </tr>
        </thead>
        <tbody>
          {ordenadas.map((v) => (
            <tr key={v.id}>
              <td>{v.id}</td>
              <td>{v.fecha ? new Date(v.fecha).toLocaleString("es-CL") : "-"}</td>
              <td>{v.nombre ?? v.bebida?.nombre ?? `Bebida ${v.bebidaId ?? ""}`}</td>
              <td>{v.unidades}</td>
              <td>{peso(v.total)}</td>
              <td>
                <Badge bg={v.estado === "AUTORIZADA" ? "success" : "danger"}>
                  {v.estado === "AUTORIZADA" ? "Autorizada" : "Rechazada"}
                </Badge>
              </td>
              <td>{v.motivo ?? "-"}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}
