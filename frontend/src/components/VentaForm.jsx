import { useEffect, useState } from "react";
import { Alert, Button, Card, Form, Spinner } from "react-bootstrap";
import { ApiError, registrarVenta } from "../services/api.js";
import Mensaje from "./Mensaje.jsx";

const peso = (n) => `$${Number(n).toLocaleString("es-CL")}`;

/** Registra una venta. `alTerminar` recarga bebidas y ventas desde el backend. */
export default function VentaForm({ bebidas, bebidaInicial, alTerminar }) {
  const [bebidaId, setBebidaId] = useState("");
  const [unidades, setUnidades] = useState("1");
  const [errorLocal, setErrorLocal] = useState("");
  const [resultado, setResultado] = useState(null); // { ok, venta } | { ok:false, rechazo }
  const [errorApi, setErrorApi] = useState(null);
  const [enviando, setEnviando] = useState(false);

  useEffect(() => {
    if (bebidaInicial) setBebidaId(String(bebidaInicial));
  }, [bebidaInicial]);

  const seleccionada = bebidas.find((b) => String(b.id) === bebidaId);

  const enviar = async (ev) => {
    ev.preventDefault();
    setResultado(null);
    setErrorApi(null);

    const n = Number(unidades);
    if (!bebidaId) return setErrorLocal("Selecciona una bebida.");
    if (!Number.isInteger(n) || n <= 0) return setErrorLocal("La cantidad debe ser un entero mayor que 0.");
    setErrorLocal("");

    setEnviando(true);
    try {
      const venta = await registrarVenta(Number(bebidaId), n);
      setResultado({ ok: true, venta });
      await alTerminar(); // el stock se vuelve a pedir al backend
    } catch (err) {
      if (err instanceof ApiError && err.status === 409) {
        setResultado({ ok: false, rechazo: err.cuerpo });
        await alTerminar(); // el rechazo tambien queda en el historial
      } else {
        setErrorApi(err); // 400, 404, 500 o sin conexion
      }
    } finally {
      setEnviando(false);
    }
  };

  return (
    <Card body style={{ maxWidth: 560 }}>
      <Form onSubmit={enviar} noValidate>
        <Form.Group className="mb-3">
          <Form.Label>Bebida</Form.Label>
          <Form.Select value={bebidaId} onChange={(e) => setBebidaId(e.target.value)}>
            <option value="">Selecciona una bebida</option>
            {bebidas.map((b) => (
              <option key={b.id} value={b.id}>
                {b.nombre} ({b.tipo === "ALCOHOLICA" ? "alcohólica" : "sin alcohol"}) · stock {b.stock}
              </option>
            ))}
          </Form.Select>
          {seleccionada?.precio != null && (
            <Form.Text muted>Precio unitario: {peso(seleccionada.precio)}</Form.Text>
          )}
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Cantidad</Form.Label>
          <Form.Control type="number" min="1" value={unidades}
            onChange={(e) => setUnidades(e.target.value)} />
        </Form.Group>

        {errorLocal && <Alert variant="danger">{errorLocal}</Alert>}
        <Mensaje error={errorApi} />

        {resultado?.ok && (
          <Alert variant="success">
            Venta autorizada: {resultado.venta.unidades} × {resultado.venta.nombre ?? seleccionada?.nombre}.
            Total cobrado: <strong>{peso(resultado.venta.total)}</strong>.
          </Alert>
        )}
        {resultado && !resultado.ok && (
          <Alert variant="warning">
            Venta rechazada{resultado.rechazo?.error ? ` (${resultado.rechazo.error})` : ""}.{" "}
            {resultado.rechazo?.mensaje}
          </Alert>
        )}

        <Button type="submit" variant="primary" disabled={enviando}>
          {enviando ? <Spinner size="sm" animation="border" /> : "Registrar venta"}
        </Button>
      </Form>
    </Card>
  );
}
