import { useEffect, useState } from "react";
import { Button, Form, Modal, Spinner } from "react-bootstrap";
import Mensaje from "./Mensaje.jsx";

const VACIO = {
  nombre: "", tipo: "ALCOHOLICA", volumenML: "", stock: "",
  gradosAlcohol: "", certificada: false, azucarPorLitro: "", ventaRestringida: false,
};

/** Alta y edicion. `bebida` null = crear. `onGuardar(datos)` debe lanzar ApiError si falla. */
export default function BebidaForm({ show, bebida, onGuardar, onCerrar }) {
  const [f, setF] = useState(VACIO);
  const [errores, setErrores] = useState({}); // validacion local
  const [errorApi, setErrorApi] = useState(null); // respuesta del backend
  const [enviando, setEnviando] = useState(false);

  useEffect(() => {
    if (show) {
      setF(bebida ? { ...VACIO, ...bebida } : VACIO);
      setErrores({});
      setErrorApi(null);
    }
  }, [show, bebida]);

  const alc = f.tipo === "ALCOHOLICA";
  const camposApi = errorApi?.status === 400 ? errorApi.cuerpo?.campos ?? {} : {};
  const set = (campo, valor) => setF((prev) => ({ ...prev, [campo]: valor }));
  const msg = (campo) => errores[campo] ?? camposApi[campo];

  const validar = () => {
    const e = {};
    if (!f.nombre.trim()) e.nombre = "El nombre es obligatorio.";
    if (f.volumenML === "" || Number(f.volumenML) <= 0) e.volumenML = "El volumen es obligatorio.";
    if (f.stock === "" || Number(f.stock) < 0) e.stock = "El stock no puede ser negativo.";
    if (alc && f.gradosAlcohol === "") e.gradosAlcohol = "Indica los grados de alcohol.";
    if (!alc && (f.azucarPorLitro === "" || Number(f.azucarPorLitro) < 0))
      e.azucarPorLitro = "Indica el azúcar por litro (0 o más).";
    return e;
  };

  const enviar = async (ev) => {
    ev.preventDefault();
    const e = validar();
    setErrores(e);
    setErrorApi(null);
    if (Object.keys(e).length > 0) return; // no se envia un formulario invalido

    const datos = {
      nombre: f.nombre.trim(),
      tipo: f.tipo,
      volumenML: Number(f.volumenML),
      stock: Number(f.stock),
      ventaRestringida: f.ventaRestringida,
      gradosAlcohol: alc ? Number(f.gradosAlcohol) : null,
      certificada: alc ? f.certificada : null,
      azucarPorLitro: alc ? null : Number(f.azucarPorLitro),
    };

    setEnviando(true);
    try {
      await onGuardar(datos);
    } catch (err) {
      setErrorApi(err); // el backend puede devolver errores por campo
    } finally {
      setEnviando(false);
    }
  };

  return (
    <Modal show={show} onHide={onCerrar} centered>
      <Form onSubmit={enviar} noValidate>
        <Modal.Header closeButton>
          <Modal.Title>{bebida ? "Editar bebida" : "Nueva bebida"}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Mensaje error={errorApi} />

          <Form.Group className="mb-3">
            <Form.Label>Nombre</Form.Label>
            <Form.Control value={f.nombre} isInvalid={!!msg("nombre")}
              onChange={(e) => set("nombre", e.target.value)} />
            <Form.Control.Feedback type="invalid">{msg("nombre")}</Form.Control.Feedback>
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Tipo</Form.Label>
            <Form.Select value={f.tipo} onChange={(e) => set("tipo", e.target.value)}>
              <option value="ALCOHOLICA">Alcohólica</option>
              <option value="SIN_ALCOHOL">Sin alcohol</option>
            </Form.Select>
          </Form.Group>

          <div className="row">
            <Form.Group className="mb-3 col-6">
              <Form.Label>Volumen (ml)</Form.Label>
              <Form.Control type="number" value={f.volumenML} isInvalid={!!msg("volumenML")}
                onChange={(e) => set("volumenML", e.target.value)} />
              <Form.Control.Feedback type="invalid">{msg("volumenML")}</Form.Control.Feedback>
            </Form.Group>
            <Form.Group className="mb-3 col-6">
              <Form.Label>Stock</Form.Label>
              <Form.Control type="number" value={f.stock} isInvalid={!!msg("stock")}
                onChange={(e) => set("stock", e.target.value)} />
              <Form.Control.Feedback type="invalid">{msg("stock")}</Form.Control.Feedback>
            </Form.Group>
          </div>

          {alc ? (
            <>
              <Form.Group className="mb-3">
                <Form.Label>Grados de alcohol</Form.Label>
                <Form.Control type="number" step="0.1" value={f.gradosAlcohol}
                  isInvalid={!!msg("gradosAlcohol")}
                  onChange={(e) => set("gradosAlcohol", e.target.value)} />
                <Form.Control.Feedback type="invalid">{msg("gradosAlcohol")}</Form.Control.Feedback>
              </Form.Group>
              <Form.Check className="mb-2" label="Certificada por el proveedor"
                checked={!!f.certificada} onChange={(e) => set("certificada", e.target.checked)} />
            </>
          ) : (
            <Form.Group className="mb-3">
              <Form.Label>Azúcar por litro (g/L)</Form.Label>
              <Form.Control type="number" value={f.azucarPorLitro}
                isInvalid={!!msg("azucarPorLitro")}
                onChange={(e) => set("azucarPorLitro", e.target.value)} />
              <Form.Control.Feedback type="invalid">{msg("azucarPorLitro")}</Form.Control.Feedback>
            </Form.Group>
          )}

          <Form.Check label="Venta restringida" checked={!!f.ventaRestringida}
            onChange={(e) => set("ventaRestringida", e.target.checked)} />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="outline-secondary" onClick={onCerrar}>Cancelar</Button>
          <Button type="submit" variant="primary" disabled={enviando}>
            {enviando ? <Spinner size="sm" animation="border" /> : "Guardar bebida"}
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
}
