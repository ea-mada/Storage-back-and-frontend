import React from 'react';
import { Link } from 'react-router-dom';
import '../CSS/Navigation.css';

const Navigation = () => {
    
    return (
        <div>
            <h4>
                <Link to="/customers">
                    <span className="glyphicon glyphicon-th-list" aria-hidden="true"></span> 
                    Customer List
                </Link>
            </h4>
            <h4>
                <Link to="/items">
                    <span className="glyphicon glyphicon-th-list" aria-hidden="true"></span> 
                    Items List
                </Link>
            </h4>
        </div>
    );
    
}

export default Navigation;
