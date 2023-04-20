import React, { Component } from 'react'
import { ErrorMessage, Esperando } from '../comunes'

export default class Muro extends Component {
    constructor(props) {
        super(props)
        this.state = {
            listado: null,
            loading: true,
            error: null,
            ver: []
        }
    }
    
  render() {
    if(this.state.loading)
        return <Esperando />
    return (
        <>
            {this.state.error && <ErrorMessage msg={this.state.error} />}

            <h1>Muro</h1>
            <input type='button' defaultValue={1} onClick={()=> this.load(1)} />
            <input type='button' defaultValue={2} onClick={()=> this.load(2)} />
            <input type='button' defaultValue={3} onClick={()=> this.load(3)} />
            <input type='button' defaultValue={4} onClick={()=> this.load(4)} />
            <input type='button' defaultValue={5} onClick={()=> this.load(5)} />

            <br></br>
            <br></br>


{/*             {JSON.stringify(this.state.listado)}
 */}
            <div className="container text-center">
                <div className="row">
   
                    {this.state.listado && this.state.listado.map((item, index) => (
                            <>
                            <div className="col">
                            <div className="card mt-4" style={{ width: "18rem" }}>
                            {this.state.ver[index] &&
                                    <img key={item.id} src={item.download_url} alt={item.id} />
                                    }
                            <div className="card-body">
                                <h5 className="card-title">{item.author}</h5>
                                <p className="card-text">Dimension: {item.width}*{item.height}</p>
                                <button type='button' href="#" className="btn btn-primary" onClick={()=> {
                                        this.setEstado(index)
                                    }}>
                                {this.state.ver[index] ? 'Ocultar Imagen' : 'Mostrar Imagen'}
                                </button>
                            </div>
                            </div>
                            </div>
                            </>               
                            ))}
                </div>
            </div>
        </>
    )
  }

    setEstado(index) {
        this.state.ver[index] ? this.state.ver[index]=false : this.state.ver[index]=true
        this.setState({ ver: this.state.ver })
    }

  setError(msg) {
    this.setState({error: msg})
  }
  load(num) {
    this.setState({loading: true})
    fetch('https://picsum.photos/v2/list?page='+num+'&limit=10')
        .then(resp => {
            if(resp.ok) {
                resp.json().then(
                    data => this.setState({listado: data, ver : []})
                )
            } else {
                this.setError(resp.status)
            }
        })
        .catch(err => this.setError(JSON.stringify(err)))
        .finally(() => this.setState({loading: false}))
  }
  componentDidMount() {
    this.load(1)
  }

}
