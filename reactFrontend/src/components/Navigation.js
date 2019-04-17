import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import '../CSS/Navigation.css';

export default class Navigation extends Component {
    render() {
        return (
            <h4><Link to="/customers"><span className="glyphicon glyphicon-th-list" aria-hidden="true"></span> Customer List</Link></h4>
        );
    }
}
