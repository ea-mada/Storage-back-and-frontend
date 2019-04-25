import React from 'react';
import ReactDOM from 'react-dom';
import { HashRouter, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
import App from './App';
import './App.css';
import Create from './components/Create';
import Prefill from './components/Prefill';
import Navigation from './components/Navigation';
import './axiosDefaults';

ReactDOM.render(
  <HashRouter>
      <div>
        <Route path = "/" component={Navigation} />
        <Route exact path='/customers' component={App} />
        <Route path='/customers/addCustomer' component={Create} />
        <Route path='/customers/prefillCustomer' component={Prefill} />
      </div>
  </HashRouter>,
  document.getElementById('root')
);