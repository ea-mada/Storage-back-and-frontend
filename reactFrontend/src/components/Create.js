import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import { Link } from 'react-router-dom';

class Create extends Component {

  constructor() {
    super();
    this.state = {
      vatCode: '',
      name: '',
      address: '',
      phoneNumber: '',
      iban: '',
      notes: ''
    };
  }
  onChange = (e) => {
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);
  }

  onSubmit = (e) => {
    e.preventDefault();

    const { vatCode, name, address, phoneNumber, iban, notes } = this.state;

    axios.post('http://localhost:8080/api/storage', { vatCode, name, address, phoneNumber, iban, notes })
      .then((result) => {
        this.props.history.push("/")
      });
  }

  render() {
    const { vatCode, name, address, phoneNumber, iban, notes } = this.state;
    return (
      <div className="container">
        <div className="panel panel-default">
          <div className="panel-heading">
            <h3 className="panel-title">
              ADD Customer
            </h3>
          </div>
          <div className="panel-body">
            <h4><Link to="/"><span className="glyphicon glyphicon-th-list" aria-hidden="true"></span> Customer List</Link></h4>
            <form onSubmit={this.onSubmit}>
              <div className="form-group">
                <label htmlFor="vatCode">vatCode:</label>
                <input type="text" className="form-control" name="vatCode" value={vatCode} onChange={this.onChange} placeholder="vatCode" />
              </div>
              <div className="form-group">
                <label htmlFor="writingDate">name:</label>
                <input type="text" className="form-control" name="name" value={name} onChange={this.onChange} placeholder="name" />
              </div>
              <div className="form-group">
                <label htmlFor="address">WhatCompanyWrote:</label>
                <input type="text" className="form-control" name="address" value={address} onChange={this.onChange} placeholder="address" />
              </div>
              <div className="form-group">
                <label htmlFor="phoneNumber">phoneNumber:</label>
                <input type="text" className="form-control" name="phoneNumber" value={phoneNumber} onChange={this.onChange} placeholder="Phone Number" />
              </div>
              <div className="form-group">
                <label htmlFor="iban">iban:</label>
                <input type="text" className="form-control" name="iban" value={iban} onChange={this.onChange} placeholder="iban" />
              </div>
              <div className="form-group">
                <label htmlFor="phoneNumber">notes:</label>
                <input type="text" className="form-control" name="notes" value={notes} onChange={this.onChange} placeholder="notes" />
              </div>
              <button type="submit" className="btn btn-default">Submit</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default Create;