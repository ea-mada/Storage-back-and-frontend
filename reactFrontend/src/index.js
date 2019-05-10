import React from 'react';
import ReactDOM from 'react-dom';
import { HashRouter, Route, Switch } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
import App from './App';
import './App.css';
import Create from './components/Create';
import ShowCustomer from './components/ShowCustomer';
import Prefill from './components/Prefill';
import Navigation from './components/Navigation';
import './axiosDefaults';
import ItemsList from './components/ItemsList';
import ItemForm from './components/ItemForm';
import { NotFound } from './components/NotFound';

ReactDOM.render(
  <HashRouter>
      <Switch>
        <Route exact path = "/" component={Navigation} />
        <Route exact path='/customers' component={App} />
        <Route path='/customers/addCustomer' component={Create} />
        <Route path='/customers/showCustomer/:customerid' component={ShowCustomer} />
        <Route path='/customers/prefillCustomer' component={Prefill} />
        <Route exact path='/items' component={ItemsList} />
        <Route path='/items/form' component={ItemForm} />
        <Route component={NotFound} />
      </Switch>
  </HashRouter>,
  document.getElementById('root')
);