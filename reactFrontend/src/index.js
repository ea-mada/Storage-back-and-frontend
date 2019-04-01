import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './CSS/bootstrap.min.css';
import App from './App';
import './App.css';
import Create from './components/Create';
import Prefill from './components/Prefill';

ReactDOM.render(
  <Router>
      <div>
        <Route exact path='/' component={App} />
        <Route path='/create' component={Create} />
        <Route path='/prefill' component={Prefill} />
      </div>
  </Router>,
  document.getElementById('root')
);