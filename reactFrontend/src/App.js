import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router-dom';
import axios from 'axios';

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      customers: []
    };
  }

  componentDidMount() {
    axios.get('http://localhost:8080/api/storage/')
      .then(res => {
        this.setState({ customers: res.data });
        console.log(this.state.customers);
      });
  }

  render() {
    return (
      <div className="container">
        <div className="panel panel-default">
          <div className="panel-heading">
            <h3 className="panel-title">
              CUSTOMER LIST
            </h3>
          </div>
          <div className="panel-body">
            <h4><Link to="/create"><span className="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Add Customer</Link></h4>
            <h4><Link to="/prefill"><span className="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Pre-fill Customer</Link></h4>
            <table className="table table-stripe">
              <thead>
                <tr>
                  <th>VAT code</th>
                  <th>Name</th>
                </tr>
              </thead>
              <tbody>
                {this.state.customers.map(c =>
                  <tr key={c.customerid}>
                    <td>{c.name}</td>
                    <td>{c.address}</td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    );
  }
}

export default App;
