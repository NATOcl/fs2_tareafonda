import { useCallback, useEffect, useState } from "react";
import { Alert, Container, Nav, Navbar } from "react-bootstrap";
import {
  actualizarBebida, crearBebida, eliminarBebida, listarBebidas, listarVentas, restringirVenta,
} from "./services/api.js";
import BebidaForm from "./components/BebidaForm.jsx";
import BebidaList from "./components/BebidaList.jsx";
import Mensaje from "./components/Mensaje.jsx";
import VentaForm from "./components/VentaForm.jsx";
import VentaHistorial from "./components/VentaHistorial.jsx";

/**
 * Ningun componente calcula precios ni decide si una venta se autoriza:
 * esos datos vienen del backend. Aqui solo se coordina la pantalla.
 */
export default function App() {
  const [vista, setVista] = useState("bebidas"); // bebidas | venta | historial
  const [bebidas, setBebidas] = useState([]);
  const [ventas, setVentas] = useState([]);
  const [filtro, setFiltro] = useState("");
  const [cargandoBebidas, setCargandoBebidas] = useState(true);
  const [cargandoVentas, setCargandoVentas] = useState(true);
  const [error, setError] = useState(null);
  const [aviso, setAviso] = useState("");
  const [formAbierto, setFormAbierto] = useState(false);
  const [editando, setEditando] = useState(null);
  const [bebidaParaVenta, setBebidaParaVenta] = useState(null);

  const cargarBebidas = useCallback(async (nombre = filtro) => {
    setCargandoBebidas(true);
    try {
      setBebidas(await listarBebidas(nombre));
      setError(null);
    } catch (err) {
      setError(err);
    } finally {
      setCargandoBebidas(false);
    }
  }, [filtro]);

  const cargarVentas = useCallback(async () => {
    setCargandoVentas(true);
    try {
      setVentas(await listarVentas());
    } catch (err) {
      setError(err);
    } finally {
      setCargandoVentas(false);
    }
  }, []);

  useEffect(() => {
    cargarBebidas("");
    cargarVentas();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const buscar = (nombre) => {
    setFiltro(nombre);
    cargarBebidas(nombre);
  };

  // Lanza el error para que BebidaForm lo muestre campo por campo.
  const guardar = async (datos) => {
    if (editando) {
      await actualizarBebida(editando.id, datos);
      setAviso("Bebida actualizada.");
    } else {
      await crearBebida(datos);
      setAviso("Bebida creada.");
    }
    setFormAbierto(false);
    await cargarBebidas();
  };

  const accion = async (fn, textoOk) => {
    setAviso("");
    try {
      await fn();
      setAviso(textoOk);
      await cargarBebidas();
    } catch (err) {
      setError(err);
    }
  };

  const eliminar = (b) => {
    if (!window.confirm(`¿Eliminar "${b.nombre}"?`)) return;
    accion(() => eliminarBebida(b.id), "Bebida eliminada.");
  };
  const restringir = (b) =>
    accion(() => restringirVenta(b.id), `"${b.nombre}" quedó con venta restringida.`);

  const irAVender = (b) => {
    setBebidaParaVenta(b.id);
    setVista("venta");
  };

  const alTerminarVenta = async () => {
    await Promise.all([cargarBebidas(), cargarVentas()]);
  };

  const ir = (v) => {
    setVista(v);
    setError(null);
    setAviso("");
  };

  return (
    <>
      <Navbar variant="dark" expand="sm" className="fonda-nav">
        <Container>
          <Navbar.Brand>Fonda San Belarmino</Navbar.Brand>
          <Nav activeKey={vista} onSelect={ir} className="me-auto">
            <Nav.Link eventKey="bebidas">Bebidas</Nav.Link>
            <Nav.Link eventKey="venta">Nueva venta</Nav.Link>
            <Nav.Link eventKey="historial">Historial</Nav.Link>
          </Nav>
        </Container>
      </Navbar>

      <Container className="py-4">
        <Mensaje error={error} onCerrar={() => setError(null)} />
        {aviso && <Alert variant="success" dismissible onClose={() => setAviso("")}>{aviso}</Alert>}

        {vista === "bebidas" && (
          <BebidaList
            bebidas={bebidas}
            cargando={cargandoBebidas}
            onBuscar={buscar}
            onNueva={() => { setEditando(null); setFormAbierto(true); }}
            onEditar={(b) => { setEditando(b); setFormAbierto(true); }}
            onEliminar={eliminar}
            onRestringir={restringir}
            onVender={irAVender}
          />
        )}
        {vista === "venta" && (
          <VentaForm
            bebidas={bebidas}
            bebidaInicial={bebidaParaVenta}
            alTerminar={alTerminarVenta}
          />
        )}
        {vista === "historial" && <VentaHistorial ventas={ventas} cargando={cargandoVentas} />}
      </Container>

      <BebidaForm
        show={formAbierto}
        bebida={editando}
        onGuardar={guardar}
        onCerrar={() => setFormAbierto(false)}
      />
    </>
  );
}
