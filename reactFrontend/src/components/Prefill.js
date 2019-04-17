import React, { Component } from 'react';
import axios from 'axios';
import '../CSS/Prefill.css';

class Prefill extends Component {

  constructor(props) {
    super(props);
    this.state = {
      customerNamesAndIds: [],
      customerVatcodesAndIds: [],

      customer: {}
    };
  }

  onChangeNameField = (e) => {
    var temporary = e.target.value
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);

    

    if (temporary.trim() !== '') {
      axios.get('/customers/prefillName/'+  temporary+ "/" )
      .then(res => {
        this.setState({ customerNamesAndIds: res.data });

        if (res.data.length === 0 || res.data.length > 20) {
          document.getElementById('customerNameFragmentId').style.borderColor = "red";
        }

        if (res.data.length === 0) {
          document.getElementById('tooMuchCustomersWithName').innerHTML = "There are 0 Customers containing: '" + temporary + "' in their names.";
        } else if (res.data.length > 20) {
          document.getElementById('tooMuchCustomersWithName').innerHTML = "There are more than 20 Customers containing: '" + temporary + "' in their names, BUT SHOWN ONLY 20 IN SUGGESTIONS.";
        } else {
          document.getElementById('customerNameFragmentId').style.borderColor = "black";
          document.getElementById('tooMuchCustomersWithName').innerHTML = "";
        }

      });
    } else {
      document.getElementById('customerNameFragmentId').style.borderColor = "black";
      this.setState({customerNamesAndIds: []});
    }
  }

  onChangeVatcodeField = (e) => {
    var temporary = e.target.value
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);

    if (temporary.trim() !== '') {
      axios.get('/customers/prefillVatcode/'+  temporary+ "/" )
      .then(res => {
        this.setState({ customerVatcodesAndIds: res.data });

        if (res.data.length === 0 || res.data.length > 20) {
          document.getElementById('customerVatcodeFragmentId').style.borderColor = "red";
        }

        if (res.data.length === 0) {
          document.getElementById('tooMuchCustomersWithVatcode').innerHTML = "There are 0 Customers containing: '" + temporary + "' in their names.";
        } else if (res.data.length > 20) {
          document.getElementById('tooMuchCustomersWithVatcode').innerHTML = "There are more than 20 Customers containing: '" + temporary + "' in their names, BUT SHOWN ONLY 20 IN SUGGESTIONS.";
        } else {
          document.getElementById('customerVatcodeFragmentId').style.borderColor = "black";
    document.getElementById('tooMuchCustomersWithVatcode').innerHTML = "";
        }

      });
    } else {
      this.setState({customerVatcodesAndIds: []});
      document.getElementById('customerVatcodeFragmentId').style.borderColor = "black";
    }
  }

  fillTextfieldsOnClickName = (e) => {
    var temporary = e.target.innerHTML
    var customerid = e.target.id
    console.log('customer id: ' + customerid);

    this.setState({ name: temporary })
    this.setState({ customerNamesAndIds: [] });

    axios.get('/customers/getCustomer/'+ customerid)
    .then(res => {
      this.setState({ customerid: res.data.customerid, name: res.data.name, address: res.data.address, phoneNumber: res.data.phoneNumber, iban: res.data.iban });
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

      document.getElementById('button').innerHTML = "Edit Customer";
    });
  }

  fillTextfieldsOnClickVatcode = (e) => {
    var temporary = e.target.innerHTML
    var customerid = e.target.id
    console.log('customer id: ' + customerid);

    this.setState({ vatCode: temporary })
    this.setState({ customerVatcodesAndIds: [] });

    axios.get('/customers/getCustomer/'+ customerid)
    .then(res => {
      this.setState({ name: res.data.name, address: res.data.address, phoneNumber: res.data.phoneNumber, iban: res.data.iban });
      if (res.data.vatCode !== null) {
        this.setState({vatCode: res.data.vatCode});
      } else {
        this.setState({vatCode: ''})
      }

      if (res.data.notes !== null) {
        this.setState({notes: res.data.notes});
      } else {
        this.setState({notes: ''});
      }

      document.getElementById('customerVatcodeFragmentId').style.borderColor = "black";
      document.getElementById('customerNameFragmentId').style.borderColor = "black";

      this.setState({customerGotFromPrefillingVatcode: res.data.vatCode, customerGotFromPrefillingName: res.data.name, customerGotFromPrefillingAddress: res.data.address, customerGotFromPrefillingPhonenumber: res.data.phoneNumber, customerGotFromPrefillingIban: res.data.iban, customerGotFromPrefillingNotes: res.data.notes});
      console.log(res.data)

      document.getElementById('button').innerHTML = "Edit Customer";
    });
  }

  onChangeNonAutoField = (e) => {
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);
  }

  onSubmit = (e) => {
    const { customerid, vatCode, name, address, phoneNumber, iban, notes } = this.state;
    if (document.getElementById("button").innerHTML === "Edit Customer") {
    console.log(vatCode)
    axios.put('/customers/setCustomer/'+ customerid, { name, vatCode, address, phoneNumber, iban, notes });
    } else {
      axios.post("/customers/addCustomer/", { name, vatCode, address, phoneNumber, iban, notes });
    }
    
  }

  onClickContainer = () => {
    this.setState({customerNamesAndIds: [], customerVatcodesAndIds: []});
    document.getElementById('customerVatcodeFragmentId').style.borderColor = "black";
      document.getElementById('customerNameFragmentId').style.borderColor = "black";
  }

  render() {
    const { customerNamesAndIds, customerVatcodesAndIds, vatCode, name, address, phoneNumber, iban, notes } = this.state;
    return (
      <div className="container" onClick={this.onClickContainer} id="container">
      <form onSubmit={this.onSubmit} autoComplete="off">
      <div>
      <label htmlFor="vatCode">VatCode:</label>
                <input id="customerVatcodeFragmentId" onChange={this.onChangeVatcodeField} type="text" className="form-control" name="vatCode" value={vatCode} placeholder="vatCode" />
                <table>
        <tbody>
          {customerVatcodesAndIds.slice(0, 20).map(c =>
          customerVatcodesAndIds.length > 20 ?
            <tr>
              <td id={c.customerid} onClick={this.fillTextfieldsOnClickVatcode}><span className="warning">There are more records. </span>{c.vatCode}</td>
            </tr>
            :
            <tr>
              <td id={c.customerid} onClick={this.fillTextfieldsOnClickVatcode}>{c.vatCode}</td>
            </tr>
            )}
        </tbody>
      </table>
      </div>
      
      <pre id="tooMuchCustomersWithVatcode" className="warning prefillWarningNotTable"></pre>
      <div className="form-group">
                <label htmlFor="name">Name:</label>
                <input id="customerNameFragmentId" type="text" className="form-control" name="name" value={name} onChange={this.onChangeNameField} placeholder="customer name" />
                <table>
      <tbody>
      {customerNamesAndIds.slice(0, 20).map(c =>
      customerNamesAndIds.length > 20 ?
                      <tr>
                      <td id={c.customerid} onClick={this.fillTextfieldsOnClickName}><span className="warning">There are more records. </span>{c.name}</td>
                      </tr>
                      :
                      <tr>
                      <td id={c.customerid} onClick={this.fillTextfieldsOnClickName}>{c.name}</td>
                      </tr> 
                )}
      </tbody>
      </table>
              </div>
              
      <pre id="tooMuchCustomersWithName" className="warning prefillWarningNotTable"></pre>
      <div className="form-group">
                <label htmlFor="address">Address:</label>
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
              <button name="onlyButtonInForm" id="button" type="submit">Add Customer</button>
      </form>
      
      </div>
    );
  }
}

export default Prefill;
