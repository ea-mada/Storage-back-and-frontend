import React, { Component } from 'react';
import axios from 'axios';
import Alert from './Alert';
import { Button, Modal, ModalBody, ModalFooter } from 'reactstrap';

class Create extends Component {
  state = {
    vatCode: '',
    name: '',
    address: '',
    phoneNumber: '',
    iban: '',
    notes: '',
    error401: '',
    modalIsOpen: false
  };
  
  onChange = (e) => {
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);
  }

  onAlertClose = () =>{
    this.setState({
      error401: ''
    })
  }

  toggleModal = () => {
    this.setState(prevState => ({
      modalIsOpen: !prevState.modalIsOpen
    }));
  }

  onCloseModal = () => {
    this.toggleModal();
    this.props.history.push("/customers");
  }

  onSubmit = (e) => {
    e.preventDefault();

    const { vatCode, name, address, phoneNumber, iban, notes } = this.state;

    axios.post('/customers/addCustomer', { vatCode, name, address, phoneNumber, iban, notes })
      .then((result) => {
        this.toggleModal();
      }).catch(error =>{
        console.log(error);
      });
  }

  render() {
    const { vatCode, name, address, phoneNumber, iban, notes } = this.state;
    
    // intercepts responses before they get to promise
    axios.interceptors.response.use( response => response,
      error => {
          console.log("Error intercepted")
          switch (error.response.status) {
              case 401:
                  this.setState({
                      error401: 'Provided VAT code is already in database'
                  })
                  break;
              default:
                  break;
          }
          return Promise.reject(error)
      }
  )
    return (
      <div className="container">
        <Modal isOpen={this.state.modalIsOpen} toggle={this.toggleModal} className={this.props.className}>
          <ModalBody>
            <p>Customer was added succesfully!</p>
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={this.onCloseModal}>OK</Button>
          </ModalFooter>
        </Modal>
        <div className="panel panel-default">
          <div className="panel-heading">
            <h3 className="panel-title">
              ADD Customer
            </h3>
          </div>
          <div className="panel-body">
            <form onSubmit={this.onSubmit}>
              <div className="form-group">
                <label htmlFor="vatCode">VAT code:</label>
                <input type="text" className="form-control" name="vatCode" value={vatCode} onChange={this.onChange} placeholder="LT123456789" required pattern="LT\d{9}" />
              </div>
              <div className="form-group">
                <label htmlFor="writingDate">Name*:</label>
                <input type="text" className="form-control" name="name" value={name} onChange={this.onChange} placeholder="Name" required />
              </div>
              <div className="form-group">
                <label htmlFor="address">Address*:</label>
                <input type="text" className="form-control" name="address" value={address} onChange={this.onChange} placeholder="address" required />
              </div>
              <div className="form-group">
                <label htmlFor="phoneNumber">Phone number*:</label>
                <input type="text" className="form-control" name="phoneNumber" value={phoneNumber} onChange={this.onChange} placeholder="Phone Number" pattern="\+370-\d{3}-\d{5}" required />
              </div>
              <div className="form-group">
                <label htmlFor="iban">IBAN*:</label>
                <input type="text" className="form-control" name="iban" value={iban} onChange={this.onChange} placeholder="LT012345678901234567" required pattern="LT\d{18}" />
              </div>
              <div className="form-group">
                <label htmlFor="phoneNumber">notes:</label>
                <input type="text" className="form-control" name="notes" value={notes} onChange={this.onChange} placeholder="text" />
              </div>
              {/* checks if error exist and then renders Alert */}
              {this.state.error401 && <Alert alertText={this.state.error401} onClose={this.onAlertClose} />}

              <button type="submit" className="btn btn-default">Submit</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default Create;