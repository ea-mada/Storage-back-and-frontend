import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

class App extends Component {

  state = {
    customers: []
  };

  componentDidMount() {
    axios.get('http://localhost:8080/api/storage/customers/getCustomers')
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
            <h4><Link to="/customers/addCustomer"><span className="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Add Customer</Link></h4>
            <h4><Link to="/customers/prefillCustomer"><span className="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Pre-fill Customer</Link></h4>
            <table className="table table-stripe" id="white-in-black-tr">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Address</th>
                </tr>
              </thead>
              <tbody>
                {this.state.customers.map(c =>
                  <tr key={c.customerid}>
                    <td><Link to={`/customers/showCustomer/${c.customerid}`}>{c.name}</Link></td>
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
