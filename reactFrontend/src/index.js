import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './CSS/bootstrap.min.css';
import App from './App';
import './App.css';
import Create from './components/Create';
import Prefill from './components/Prefill';
import Navigation from './components/Navigation';

ReactDOM.render(
  <Router>
      <div>
        <Route path = "/" component={Navigation} />
        <Route exact path='/customers' component={App} />
        <Route path='/customers/addCustomer' component={Create} />
        <Route path='/customers/prefillCustomer' component={Prefill} />
      </div>
  </Router>,
  document.getElementById('root')
);