import { Alert } from "react-bootstrap";

/** Muestra un ApiError: conexion, 400 (campo por campo), 404, 409 o 500. */
export default function Mensaje({ error, onCerrar }) {
  if (!error) return null;
  const campos = error.status === 400 ? error.cuerpo?.campos : null;
  const variante = error.status === 409 ? "warning" : "danger";

  return (
    <Alert variant={variante} dismissible={!!onCerrar} onClose={onCerrar}>
      {error.message}
      {campos && (
        <ul className="mb-0 mt-2">
          {Object.entries(campos).map(([campo, motivo]) => (
            <li key={campo}>
              <strong>{campo}</strong>: {motivo}
            </li>
          ))}
        </ul>
      )}
    </Alert>
  );
}
