import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router-dom';
import axios from 'axios';
import '../CSS/Prefill.css';

class Prefill extends Component {

  constructor(props) {
    super(props);
    this.state = {
      customerNamesAndIds: [],
      customerVatcodesAndIds: [],

      customerGotFromPrefillingVatcode: '',
      customerGotFromPrefillingName: '',
      customerGotFromPrefillingAddress: '',
      customerGotFromPrefillingPhonenumber: '',
      customerGotFromPrefillingIban: '',
      customerGotFromPrefillingNotes: '',

      customer: {}
    };
  }

  onChangeNameField = (e) => {
    var temporary = e.target.value
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);
    this.setState({customerNamesAndIds: []});

    document.getElementById('customerNameFragmentId').style.borderColor = "black";

    if (temporary.trim() != '') {
      axios.get('http://localhost:8080/api/storage/customers/prefill/'+  temporary+ "/" )
      .then(res => {
        this.setState({ customerNamesAndIds: res.data });

        if (res.data.length == 0 || res.data.length > 20) {
          document.getElementById('customerNameFragmentId').style.borderColor = "red";
        }

      });
    }
  }

  onChangeVatcodeField = (e) => {
    var temporary = e.target.value
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);
    this.setState({customerVatcodesAndIds: []});

    document.getElementById('customerVatcodeFragmentId').style.borderColor = "black";

    if (temporary.trim() != '') {
      axios.get('http://localhost:8080/api/storage/customers/prefill/vatcode/'+  temporary+ "/" )
      .then(res => {
        this.setState({ customerVatcodesAndIds: res.data });

        if (res.data.length == 0 || res.data.length > 20) {
          document.getElementById('customerVatcodeFragmentId').style.borderColor = "red";
        }

      });
    }
  }

  fillTextfieldsOnClickName = (e) => {
    var temporary = e.target.innerHTML
    var customerid = e.target.id
    console.log('customer id: ' + customerid);

    this.setState({ name: temporary })
    this.setState({ customerNamesAndIds: [] });

    axios.get('http://localhost:8080/api/storage/'+ customerid)
    .then(res => {
      this.setState({ name: res.data.name, address: res.data.address, phoneNumber: res.data.phoneNumber, iban: res.data.iban });
      if (res.data.vatCode != null) {
        this.setState({vatCode: res.data.vatCode});
      } else {
        this.setState({vatCode: ''})
      }

      if (res.data.notes != null) {
        this.setState({notes: res.data.notes});
      } else {
        this.setState({notes: ''});
      }

      document.getElementById('customerVatcodeFragmentId').style.borderColor = "black";
      document.getElementById('customerNameFragmentId').style.borderColor = "black";

      this.setState({customerGotFromPrefillingVatcode: res.data.vatCode, customerGotFromPrefillingName: res.data.name, customerGotFromPrefillingAddress: res.data.address, customerGotFromPrefillingPhonenumber: res.data.phoneNumber, customerGotFromPrefillingIban: res.data.iban, customerGotFromPrefillingNotes: res.data.notes});
      console.log(res.data)
    });
  }

  fillTextfieldsOnClickVatcode = (e) => {
    var temporary = e.target.innerHTML
    var customerid = e.target.id
    console.log('customer id: ' + customerid);

    this.setState({ vatCode: temporary })
    this.setState({ customerVatcodesAndIds: [] });

    axios.get('http://localhost:8080/api/storage/'+ customerid)
    .then(res => {
      this.setState({ name: res.data.name, address: res.data.address, phoneNumber: res.data.phoneNumber, iban: res.data.iban });
      if (res.data.vatCode != null) {
        this.setState({vatCode: res.data.vatCode});
      } else {
        this.setState({vatCode: ''})
      }

      if (res.data.notes != null) {
        this.setState({notes: res.data.notes});
      } else {
        this.setState({notes: ''});
      }

      document.getElementById('customerVatcodeFragmentId').style.borderColor = "black";
      document.getElementById('customerNameFragmentId').style.borderColor = "black";

      this.setState({customerGotFromPrefillingVatcode: res.data.vatCode, customerGotFromPrefillingName: res.data.name, customerGotFromPrefillingAddress: res.data.address, customerGotFromPrefillingPhonenumber: res.data.phoneNumber, customerGotFromPrefillingIban: res.data.iban, customerGotFromPrefillingNotes: res.data.notes});
      console.log(res.data)
    });
  }

  onChangeNonAutoField = (e) => {
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);
  }

  onSubmit = (e) => {
    e.preventDefault();
    
  }

  render() {
    const { customerNamesAndIds, customerVatcodesAndIds, vatCode, name, address, phoneNumber, iban, notes } = this.state;
    return (
      <div className="container">
      <form onSubmit={this.onSubmit} autoComplete="off">
      <div>
      <label htmlFor="vatCode">VatCode fragment/or full VatCode for new Customer:</label>
                <input id="customerVatcodeFragmentId" onChange={this.onChangeVatcodeField} type="text" className="form-control" name="vatCode" value={vatCode} placeholder="vatCode" />
      </div>
      <table>
        <tbody>
          {customerVatcodesAndIds.slice(0, 20).map(c =>
            <tr>
              <td id={c.id} onClick={this.fillTextfieldsOnClickVatcode}>{c.vatCode}</td>
            </tr>
            )}
        </tbody>
      </table>

      <div className="form-group">
                <label htmlFor="name">name fragment/or full name for new Customer:</label>
                <input id="customerNameFragmentId" type="text" className="form-control" name="name" value={name} onChange={this.onChangeNameField} placeholder="customer name" />
              </div>
              
              <table>
      <tbody>
      {customerNamesAndIds.slice(0, 20).map(c =>
                      <tr>
                      <td id={c.id} onClick={this.fillTextfieldsOnClickName}>{c.name}</td>
                      </tr>
                )}
      </tbody>
      </table>

      <div className="form-group">
                <label htmlFor="address">WhatCompanyWrote:</label>
                <input type="text" className="form-control" name="address" value={address} onChange={this.onChangeNonAutoField} placeholder="address" />
              </div>
              <div className="form-group">
                <label htmlFor="phoneNumber">phoneNumber:</label>
                <input type="text" className="form-control" name="phoneNumber" value={phoneNumber} onChange={this.onChangeNonAutoField} placeholder="Phone Number" />
              </div>
              <div className="form-group">
                <label htmlFor="iban">iban:</label>
                <input type="text" className="form-control" name="iban" value={iban} onChange={this.onChangeNonAutoField} placeholder="iban" />
              </div>
              <div className="form-group">
                <label htmlFor="notes">notes:</label>
                <input type="text" className="form-control" name="notes" value={notes} onChange={this.onChangeNonAutoField} placeholder="notes" />
              </div>
              <button name="onlyButtonInForm" id="add" type="submit">Add New Customer</button>
      </form>
      
      </div>
    );
  }
}

export default Prefill;
