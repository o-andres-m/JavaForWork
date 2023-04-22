import React, { Component } from "react";
import { ValidationMessage, ErrorMessage, Esperando, PaginacionCmd as Paginacion,} from "../biblioteca/comunes";

export class Peliculas extends Component {
  constructor(props) {
    super(props);
    this.state = {
      modo: "list",
      listado: null,
      elemento: null,
      error: null,
      loading: true,
      pagina: 0,
      paginas: 0,
      };
    this.idOriginal = null;
    this.url =
     (process.env.REACT_APP_API_URL || "http://localhost:8080/javafilms") +"/api/films/v1";
  }

  setError(msg) {
    this.setState({ error: msg, loading: false });
  }

  list(num) {
    let pagina = this.state.pagina;
    if (num || num === 0) pagina = num;
    this.setState({ loading: true });
    fetch(`${this.url}?page=${pagina}&size=50`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "list",
                  listado: data.content,
                  loading: false,
                  pagina: data.number,
                  paginas: data.totalPages,
                });
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }

  add() {
    this.setState({
      modo: "add",
      elemento: {
        filmId: 0,
        description: "",
        length: 0,
        rating: "",
        releaseYear: "",
        rentalDuration: 0,
        rentalRate: 0,
        replacementCost: 0,
        title: "",
        languageId: 0,
        languageVOId: 0,
        actors: [],
        categories: []
      },
    });
  }
  edit(key) {
    this.setState({ loading: true });
    fetch(`${this.url}/${key}`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "edit",
                  elemento: {
                    filmId: data.filmId,
                    description: data.description,
                    length: data.length,
                    rating: data.rating,
                    releaseYear: data.releaseYear,
                    rentalDuration: data.rentalDuration,
                    rentalRate: data.rentalRate,
                    replacementCost: data.replacementCost,
                    title: data.title,
                    languageId: data.language,
                    languageVOId: data.languageVO ,
                    actors: data.actors,
                    categories: data.categories
                  },
                  loading: false,
                }
                
                );
                this.idOriginal = key;
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }
  view(key) {
    this.setState({ loading: true });
    fetch(`${this.url}/${key}`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  modo: "view",
                  elemento: data,
                  loading: false,
                });
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }
  delete(key,peliName) {
    if (!window.confirm("Eliminar pelicula "+peliName +"?")) return;
    this.setState({ loading: true });
    fetch(`${this.url}/${key}`, { method: "DELETE" })
      .then((response) => {
        if (response.ok) this.list();
        else
          response.json().then((error) =>
            this.setError(`${error.status}:
    ${error.error}`)
          );
        this.setState({ loading: false });
      })
      .catch((error) => this.setError(error));
  }
  componentDidMount() {
    this.list(0);
  }

  cancel() {
    this.list();
  }
  send(elemento) {
    this.setState({ loading: true });
    // eslint-disable-next-line default-case
    let actorsArr = elemento.actors.split(',').map(x=>+x)
    let categoryArr = elemento.categories.split(',').map(x=>+x)

    let elementoToSend = {
      filmId: elemento.filmId,
      description: elemento.description,
      length: +elemento.length,
      rating: elemento.rating,
      releaseYear: +elemento.releaseYear,
      rentalDuration: +elemento.rentalDuration,
      rentalRate: +elemento.rentalRate,
      replacementCost: +elemento.replacementCost,
      title: elemento.title,
      languageId: +elemento.languageId,
      languageVOId: +elemento.languageVOId ,
      actors: actorsArr,
      categories: categoryArr
    }

    switch (this.state.modo) {
      case "add":
        fetch(`${this.url}`, {
          method: "POST",
          body: JSON.stringify(elementoToSend),
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (response.ok) this.cancel();
            else
              response.json().then((error) =>
                this.setError(`${error.status}:
    ${error.detail}`)
              );
            this.setState({ loading: false });
          })
          .catch((error) => this.setError(error));
        break;
      case "edit":
        fetch(`${this.url}/${this.idOriginal}`, {
          method: "PUT",
          body: JSON.stringify(elementoToSend),
          headers: {
            "Content-Type": "application/json",
          },
          
        })
          .then((response) => {
            if (response.ok) this.cancel();
            else
              response.json().then((error) =>
                this.setError(`${error.status}:
    ${error.detail}`)
              );
            this.setState({ loading: false });
          })
          .catch((error) => this.setError(error));
        break;
        default:
            break;
    }
  }

  render() {
    if (this.state.loading) return <Esperando />;
    let result = [
      <ErrorMessage
        key="error"
        msg={this.state.error}
        onClear={() => this.setState({ error: null })}
      />,
    ];
    switch (this.state.modo) {
      case "add":
      case "edit":
        result.push(
          <PeliculasForm
            key="main"
            isAdd={this.state.modo === "add"}
            elemento={this.state.elemento}
            onCancel={(e) => this.cancel()}
            onSend={(e) => this.send(e)}
          />
        );
        break;
      case "view":
        result.push(
          <PeliculasView
            key="main"
            elemento={this.state.elemento}
            onCancel={(e) => this.cancel()}
          />
        );
        break;
      default:
        if (this.state.listado)
          result.push(
            <PeliculasList
              key="main"
              listado={this.state.listado}
              pagina={this.state.pagina}
              paginas={this.state.paginas}
              onAdd={(e) => this.add()}
              onView={(key) => this.view(key)}
              onEdit={(key) => this.edit(key)}
              onDelete={(key, peliName) => this.delete(key, peliName)}
              onChangePage={(num) => this.list(num)}
            />
          );
        break;
    }
    return result;
  }
}

function PeliculasList(props) {
  return (
    <>
      <table className="table table-hover table-striped">
        <thead className="table-info">
          <tr>
            <th>Lista de Peliculas</th>
            <th className="text-end">
              <input
                type="button"
                className="btn btn-primary"
                value="Añadir"
                onClick={(e) => props.onAdd()}
              />
            </th>
          </tr>
        </thead>
        <tbody className="table-group-divider">
          {props.listado.map((item) => (
            <tr key={item.filmId}>
              <td>{item.title}</td>
              <td className="text-end">
                <div className="btn-group text-end" role="group">
                  <input
                    type="button"
                    className="btn btn-primary"
                    value="Ver"
                    onClick={(e) => props.onView(item.filmId)}
                  />
                  <input
                    type="button"
                    className="btn btn-primary"
                    value="Editar"
                    onClick={(e) => props.onEdit(item.filmId)}
                  />
                  <input
                    type="button"
                    className="btn btn-danger"
                    value="Borrar"
                    onClick={(e) => props.onDelete(item.filmId, item.title)}
                  />
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Paginacion
        actual={props.pagina}
        total={props.paginas}
        onChange={(num) => props.onChangePage(num)}
      />
    </>
  );
}

function PeliculasView({ elemento, onCancel }) {
  let ratingName;
  switch (elemento.rating){
    case 'G':
      ratingName = 'GENERAL_AUDIENCES';
      break;
    case 'PG':
        ratingName = 'PARENTAL_GUIDANCE_SUGGESTED';
        break;
    case 'PG-13':
        ratingName = 'PARENTS_STRONGLY_CAUTIONED';
        break;
    case 'R':
        ratingName = 'RESTRICTED';
        break;
    case 'NC-17':
          ratingName = 'ADULTS_ONLY';
          break;
    default:
      ratingName ='NO_RATING';
      break;
  }

  return (
    <div>
        <br/>
      <p>
        <b>Codigo:</b> {elemento.filmId}
        <br />
        <b>Titulo:</b> {elemento.title}
        <br />
        <b>Descripción:</b> {elemento.description}
        <br />
        <b>Año:</b> {elemento.releaseYear}
        <br />
        <b>Rating:</b> {ratingName }
        <br />
        <b>Idioma:</b> {elemento.language ? elemento.language : 'No Posee'}
        <br />
        <b>IdiomaVO:</b> {elemento.languageVO ? elemento.languageVO : 'No Posee'}
        <br />
        <b>length:</b> {elemento.length ? elemento.length : 'No Posee'}
        <br />
        <b>rentalDuration:</b> {elemento.rentalDuration ? elemento.rentalDuration : 'No Posee'}
        <br />
        <b>rentalRate:</b> {elemento.rentalRate ? elemento.rentalRate : 'No Posee'}
        <br />
        <b>replacementCost:</b> {elemento.rentalRate ? elemento.rentalRate : 'No Posee'}
        <br />
        <b>Actores:</b> {elemento.actors.join(", ")}
        <br />
        <b>Categorias:</b> {elemento.categories.join(", ") ? elemento.categories.join(", ") : 'No posee'}

      </p>
      <p>
        <button
          className="btn btn-primary"
          type="button"
          onClick={(e) => onCancel()}
        >
          Volver
        </button>
      </p>
    </div>
  );
}

class PeliculasForm extends Component {
  constructor(props) {
    super(props);
    this.state = { 
      elemento: props.elemento,
      msgErr: [], 
      invalid: false,
      listadoCategorias : [],
      listadoActores : []

     };
    this.handleChange = this.handleChange.bind(this);
    this.onSend = () => {
      if (this.props.onSend) this.props.onSend(this.state.elemento);
    };
    this.onCancel = () => {
      if (this.props.onCancel) this.props.onCancel();
    };
    
  }
  handleChange(event) {
    const cmp = event.target.name;
    const valor = event.target.value;
    this.setState((prev) => {
      prev.elemento[cmp] = valor;
      return { elemento: prev.elemento };
    });
    this.validar();
  }
  validarCntr(cntr) {
    if (cntr.name) {
      // eslint-disable-next-line default-case
      switch (cntr.name) {
        case "apellidos":
          cntr.setCustomValidity(
            cntr.value !== cntr.value.toUpperCase()
              ? "Debe estar en mayúsculas"
              : ""
          );
          break;
      }
    }
  }
  validar() {
    if (this.form) {
      const errors = {};
      let invalid = false;
      for (var cntr of this.form.elements) {
        if (cntr.name) {
          this.validarCntr(cntr);
          errors[cntr.name] = cntr.validationMessage;
          invalid = invalid || !cntr.validity.valid;
        }
      }
      this.setState({ msgErr: errors, invalid: invalid });
    }
  }

  getCategories() {
    this.setState({ loading: true });
    fetch(`http://localhost:8080/javafilms/api/category/v1`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  listadoCategorias: data,
                  loading: false,
                });
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }

  getActores() {
    this.setState({ loading: true });
    fetch(`http://localhost:8080/javafilms/api/actors/v1`)
      .then((response) => {
        response.json().then(
          response.ok
            ? (data) => {
                this.setState({
                  listadoActores: data,
                  loading: false,
                });
              }
            : (error) => this.setError(`${error.status}: ${error.error}`)
        );
      })
      .catch((error) => this.setError(error));
  }

  componentDidMount() {
    this.validar();
    this.getCategories()
  }
  render() {
    return (
      <form
        ref={(tag) => {
          this.form = tag;
        }}
      >
        <div className="form-group">
          <label htmlFor="filmId">Código</label>
          <input
            type="number"
            className={"form-control" + (this.props.isAdd ? "" : "-plaintext")}
            id="filmId"
            name="filmId"
            value={this.state.elemento.filmId}
            onChange={this.handleChange}
            required
            readOnly="true"
          />
          <ValidationMessage msg={this.state.msgErr.id} />
        </div>

        <div className="form-group">
          <label htmlFor="title">Titulo</label>
          <input
            type="text"
            className="form-control"
            id="title"
            name="title"
            value={this.state.elemento.title}
            onChange={this.handleChange}
            required
            minLength="2"
            maxLength="45"
          />

        </div>
        <div className="form-group">
          <label htmlFor="description">Descripción: </label>
          <input
            type="text"
            className="form-control"
            id="description"
            name="description"
            value={this.state.elemento.description}
            onChange={this.handleChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="releaseYear">Año: </label>
          <input
            type="number"
            className="form-control"
            id="releaseYear"
            name="releaseYear"
            value={this.state.elemento.releaseYear ? this.state.elemento.releaseYear : 2023}
            onChange={this.handleChange}
            min="1000"
            max="2023"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="rating">Rating: </label><br/>
          <select 
            name="rating"
            id="rating"
            onChange={this.handleChange}
            value = {this.state.elemento.rating}
            >
            <option value="NORATING" selected>Seleccione Rating...</option>
            <option value="G">GENERAL_AUDIENCES</option>
            <option value="PG">PARENTAL_GUIDANCE_SUGGESTED</option>
            <option value="PG-13">PARENTS_STRONGLY_CAUTIONED</option>
            <option value="R">RESTRICTED</option>
            <option value="NC-17">ADULTS_ONLY</option>
          </select>

        </div>

        <div className="form-group">
          <label htmlFor="languageId">Idioma: </label>
          <input
            type="text"
            className="form-control"
            id="languageId"
            name="languageId"
            value={this.state.elemento.languageId}
            onChange={this.handleChange}
            required
          />

        </div>

        <div className="form-group">
          <label htmlFor="languageVOId">IdiomaVO: </label>
          <input
            type="text"
            className="form-control"
            id="languageVOId"
            name="languageVOId"
            value={this.state.elemento.languageVOId}
            onChange={this.handleChange}
            
          />
        </div>

        <div className="form-group">
          <label htmlFor="actors">Actores: </label>
          <input
            type="text"
            className="form-control"
            id="actors"
            name="actors"
            value={this.state.elemento.actors}
            onChange={this.handleChange}
            
          />
        </div>


        <div className="form-group">
          <label htmlFor="categories">Categorias: </label>
          <input
            type="text"
            className="form-control"
            id="categories"
            name="categories"
            value={this.state.elemento.categories}
            onChange={this.handleChange}
          />

        </div>         

        <div className="form-group">
          <label htmlFor="length">length: </label>
          <input
            type="number"
            className="form-control"
            id="length"
            name="length"
            value={this.state.elemento.length}
            onChange={this.handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="releaseYear">rentalDuration: </label>
          <input
            type="number"
            className="form-control"
            id="rentalDuration"
            name="rentalDuration"
            value={this.state.elemento.rentalDuration}
            onChange={this.handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="rentalRate">rentalRate: </label>
          <input
            type="number"
            className="form-control"
            id="rentalRate"
            name="rentalRate"
            value={this.state.elemento.rentalRate}
            onChange={this.handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="replacementCost">replacementCost: </label>
          <input
            type="number"
            className="form-control"
            id="replacementCost"
            name="replacementCost"
            value={this.state.elemento.replacementCost}
            onChange={this.handleChange}
            required
          />
        </div>

        <div className="form-group">
          <button
            className="btn btn-primary"
            type="button"
            disabled={this.state.invalid}
            onClick={this.onSend}
          >
            Enviar
          </button>
          <button
            className="btn btn-primary"
            type="button"
            onClick={this.onCancel}
          >
            Volver
          </button>
        </div>
      </form>
    );
  }




}