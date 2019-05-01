import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Button } from 'reactstrap';

class ShowCustomer extends Component {

    state = {
      customer: {},
      invoices: []
    };
  

  componentDidMount() {
    axios.get('/customers/getCustomer/'+this.props.match.params.customerid)
      .then(res => {
        this.setState({ customer: res.data, invoices: res.data.invoices });

      });
  }

  delete(id){
    console.log(id);
    axios.delete('customers/deleteCustomer/'+id)
      .then((result) => {
        this.props.history.push("/")
      });
  }

  deleteInvoice(id) {
    console.log('deleting invoice...');
    axios.delete('/invoices/deleteInvoice/'+id);
let newInvoices = this.state.invoices;
      this.setState({invoices: newInvoices.splice(id, 1)})

    console.log('invoice deleted');
  }

  render() {
    return (
      <div className="container show">
            <h3 className="panel-title">
              Customers
            </h3>
            <h4><Link to="/"><span className="glyphicon glyphicon-th-list" aria-hidden="true"></span> Customer List</Link></h4>
            <dl>
              <dt>name:</dt>
              <dd>{this.state.customer.name}</dd>
              <dt>vat code:</dt>
              <dd>{this.state.customer.vatCode}</dd>
              <dt>address:</dt>
              <dd>{this.state.customer.address}</dd>
              <dt>phone number:</dt>
              <dd>{this.state.customer.phoneNumber}</dd>
              <dt>iban:</dt>
              <dd>{this.state.customer.iban}</dd>
              <dt>notes:</dt>
              <dd>{this.state.customer.notes}</dd>
            </dl>
            <Link to={`/editCustomer/${this.state.customer.customerid}`} className="btn btn-success">Edit</Link>&nbsp;
            <Button color="danger" onClick={this.delete.bind(this, this.state.customer.customerid)} >Delete</Button>

        <div>
          <h3>Invoices</h3>
          <div>
            <table>
              <thead>
              <tr>
                <th>Date Of Purchase</th>
                <th>Recieving Company</th>
                <th>Distributor</th>
              </tr>
              </thead>
              <tbody>
                {this.state.invoices.map(i =>
                  <tr key={i.invoiceId}>
                  <td>{i.dateOfPurchase}</td>
                  <td>{i.receivingCompany}</td>
                  <td>{i.distributor}</td>
                  <td><Button color="danger" onClick={()=>this.deleteInvoice(i.invoiceId)}>Delete Invoice</Button></td>
                  
                  {/* <td><button type="button" className="btn btn-danger" onClick={()=>this.deleteInvoice(i.invoiceId)}>Delete</button></td> */}
                 
                  </tr>
                  )}
                  
              </tbody>
            </table>
            
            <Link to={`invoices/addInvoice/${this.state.customer.customerid}`}>Add New Invoice</Link>
          </div>
        </div>
      </div>
    );
  }
}

export default ShowCustomer;
